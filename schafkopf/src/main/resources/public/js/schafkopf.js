/**
 * Register events
 */

$(document).ready(function() {

	$("body").on("submit", "#closeTableForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		});
	});

	$("body").on("submit", "#createTableForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		});
	});

	$("body").on("submit", "#connectForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		});
	});

	$("body").on("submit", "#leaveTableForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		})
	});

	$("body").on("submit", "#addBotForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		})
	});

	$("body").on("submit", "#startDealingForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		})
	});

	$("body").on("submit", "#takeMoreCardsForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		})
	});

	$("body").on("submit", "#createTableForm", function(event) {
		event.preventDefault();
		$(this).ajaxSubmit({
			target : '#gameArea',
		})
	});

	$("body").on("click", "#tableList a", function(event) {
		event.preventDefault();
		$('#gameArea').load($(this).attr('href'));
	});

	setInterval(updateGameState, 3000);
});

var isUpdatingGameState = false;
function updateGameState() {
	if (!isUpdatingGameState) {
		isUpdatingGameState = true;

		jQuery.ajax({
			url : "/table/state",
			success : function(json) {
				console.log("Applying gamestate");
				applyingGameState(json);
			},
		}).always(function() {
			isUpdatingGameState = false;
		});

	}

}

function applyingGameState(json) {
	var state = $.parseJSON(json);
	// apply player
	if (state.player) {

	}
	// apply other players
	var isOtherPlayerActive = false;
	if (state.otherPlayersState) {
		$(".otherPlayer").each(
				function(index) {
					if (state.otherPlayersState[index].active) {
						$(this).addClass("otherPlayerActive");
						isOtherPlayerActive = true;
					} else {
						$(this).removeClass("otherPlayerActive");
					}
					$(this).find(".cardsLeft").text(
							state.otherPlayersState[index].cardsLeft);
				});
	}
	// TODO: there might be no active player yet in a real game
	if (isOtherPlayerActive === false) {
		$("#connectedPlayer").addClass("otherPlayerActive");
	} else {
		$("#connectedPlayer").removeClass("otherPlayerActive");
	}

	// apply game actions
	if (state.gameActions) {
		// TODO make this somehow a loop
		if (state.gameActions.isOwner === true) {
			$("#closeTableForm input[type=submit]").prop("disabled", false);
		} else {
			$("#closeTableForm input[type=submit]").prop("disabled", true);
		}

		if (state.gameActions.canAddBot === true) {
			$("#addBotForm input[type=submit]").prop("disabled", false);
		} else {
			$("#addBotForm input[type=submit]").prop("disabled", true);
		}

		if (state.gameActions.canDeal === true) {
			$("#startDealingForm input[type=submit]").prop("disabled", false);
		} else {
			$("#startDealingForm input[type=submit]").prop("disabled", true);
		}

		if (state.gameActions.canTakeMoreCards === true) {
			$('#takeMoreCardsForm input[type="submit"]')
					.prop("disabled", false);
		} else {
			$('#takeMoreCardsForm input[type="submit"]').prop("disabled", true);
		}
	}
}

function initCardList() {
	$("#hand").sortable({
		connectWith : ".cardlist",
		scroll : false,
		update : function(event, ui) {
			if (ui.item[0].parentNode != event.target) {
				console.log("CARD WAS PLAYED");
				// ajax call to see if playing was successful
				
				var cardId = $(ui.item[0]).data("card-id");
				console.log(cardId);
				$.ajax({
					url : "/table/playCard",
					type : "POST",
					data : {cardId: cardId},
					success : function(json) {
						if (json === "true") {
							console.log("Fixing gamestate");
						}
						$(ui.item[0]).addClass("playedCard");
						console.log(json);
					},
					error : function(json) {
						$("#hand").sortable("cancel");
					},
				});
			} else {
				// ajax call to persist shuffle
				var hand = [];
				$("#hand li").each(function(index) {
					hand[index] = $(this).data("card-id");
				});
				hand = JSON.stringify(hand);
				$.ajax({
					url : "/table/shuffeled",
					type : "POST",
					data : {hand: hand},
				});

			}

		},
		placeholder : "ui-state-highlight"
	});

	$("#centerCardsField").sortable({
		connectWith : ".cardlist",
		items: "li:not(.playedCard)"
	});

}