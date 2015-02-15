/**
 * Register events
 */

//$('form').ajaxForm({
//    delegation: true
//});

$(document).ready(function() {
	$("body").on("submit", "#closeTableForm", function( event ) {
		console.log(event);
		//console.log(ui);
		event.preventDefault();
		console.log(event);
	} );
	
	$("body").on("submit", "#createTableForm", function( event ) {
		console.log(event);
		//console.log(ui);
		event.preventDefault();
		console.log(event);
	} );
	
	$("body").on("submit", "#connectForm", function( event ) {
		console.log(event);
		//console.log(ui);
		event.preventDefault();
		console.log(event);
		$(this).ajaxSubmit({
	        target: 'myResultsDiv'
	    })
	} );
});

//$(document).ready(function() {
//    $('#closeTableForm').ajaxForm({
//        target: '#gameArea',
//        success: function() { 
//            $('#gameArea').fadeIn('slow'); 
//        } 
//    }); 
//});

function initCardList() {
//	$(function() {
		$("#hand").sortable({
			connectWith: ".cardlist",
			scroll: false,
			update: function(event, ui) {
				console.log("Changed an element");
				console.log(event);
				console.log(ui);
				console.log(ui.item[0].parentNode);
				console.log(event.target);
				if (ui.item[0].parentNode != event.target) {
					console.log("CARD WAS PLAYED");
					// ajax call to see if playing was successful
					$( "#hand" ).sortable( "cancel" );
				} else {
					console.log("CARD WAS SHUFFELED");
				}
				
			},
			placeholder: "ui-state-highlight"
		});
//	});
//	$(function() {
		$("#centerCardsField").sortable({
			connectWith: ".cardlist",
			receive: function(event, ui) {
				console.log("Received an element");
				console.log(event);
				console.log(ui);
			}
		});
//	});
}