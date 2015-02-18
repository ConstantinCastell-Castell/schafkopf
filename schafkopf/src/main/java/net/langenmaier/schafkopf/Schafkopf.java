package net.langenmaier.schafkopf;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import net.langenmaier.schafkopf.models.Bot;
import net.langenmaier.schafkopf.models.GameActions;
import net.langenmaier.schafkopf.models.GameState;
import net.langenmaier.schafkopf.models.Player;
import net.langenmaier.schafkopf.models.Table;
import net.langenmaier.schafkopf.services.ConnectedPlayerService;
import net.langenmaier.schafkopf.services.TablesService;
import net.langenmaier.schafkopf.utils.JsonTransformer;
import net.langenmaier.schafkopf.utils.MustacheTemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Session;

 
public class Schafkopf {
 
    public static void main(String[] args) {
    	staticFileLocation("/public");
    	
        get("/", (request, reponse) -> {return new ModelAndView(null, "root.mustache");}, new MustacheTemplateEngine());
        get("/loader", (request, reponse) -> loadCurrentGame(request), new MustacheTemplateEngine());
        post("/connect", (request, reponse) -> connectPlayer(request), new MustacheTemplateEngine());
        post("/table/add", (request, reponse) -> addTable(request), new MustacheTemplateEngine());
        post("/table/leave", (request, reponse) -> leaveTable(request), new MustacheTemplateEngine());
        post("/table/close", (request, reponse) -> closeTable(request), new MustacheTemplateEngine());
        get("/table/state", "application/json", (request, reponse) -> tableState(request), new JsonTransformer());
        post("/table/addBot", (request, reponse) -> addBotToTable(request), new MustacheTemplateEngine());
        post("/table/shuffeled", "application/json", (request, reponse) -> shuffeledTable(request), new JsonTransformer());
        post("/table/playCard", "application/json", (request, reponse) -> playCard(request), new JsonTransformer());
        post("/table/startDealing", (request, reponse) -> startDealing(request), new MustacheTemplateEngine());
        post("/table/takeMoreCards", (request, reponse) -> takeMoreCards(request), new MustacheTemplateEngine());
        get("/table/join/:id", (request, reponse) -> joinTable(request), new MustacheTemplateEngine());
        

    }

    private static ModelAndView connectPlayer(Request request) {
    	request.session().attribute("player", new Player(request.queryParams("name")));
		return loadCurrentGame(request);
	}
    
    private static ModelAndView addTable(Request request) {
    	Table table = new Table(request.queryParams("name"), ConnectedPlayerService.getCurrentPlayer(request));
		TablesService.addTable(table);
		request.session().attribute("table", table);
		return loadCurrentGame(request);
	}
    
    private static ModelAndView leaveTable(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("table")) {
    		Table table = session.attribute("table");
    		Player player = ConnectedPlayerService.getCurrentPlayer(request);
    		table.removePlayer(player);
    		session.removeAttribute("table");
    	}
        return loadCurrentGame(request);
	}
    
    private static ModelAndView closeTable(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("table")) {
    		Table table = session.attribute("table");
    		TablesService.closeTable(table);
    		session.removeAttribute("table");
    	}
        return loadCurrentGame(request);
	}
    
    private static GameState tableState(Request request) {
    	Session session = request.session();
    	synchronized (Schafkopf.class) {
    		try {
    			Schafkopf.class.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (session.attributes().contains("table")) {
			Table table = session.attribute("table");
			Player player = session.attribute("player");
    		return new GameState(table, player);
    	}
        return new GameState(null, null);
	}
    
    private static ModelAndView addBotToTable(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("table")) {
    		Table table = session.attribute("table");
    		Bot bot = new Bot();
    		table.addPlayer(bot);
    		(new Thread(bot)).start();
    	}
        return loadCurrentGame(request);
	}
    
    private static Boolean shuffeledTable(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("player")) {
			Player player = session.attribute("player");
			String jsonHand = request.queryParams("hand");
			Gson gson = new Gson();
			int[] cardIds = gson.fromJson(jsonHand, int[].class);
    		return player.setShuffeledHand(cardIds);
    	}
        return false;
	}
    
    private static Boolean playCard(Request request) {
    	Session session = request.session();
    	System.out.println("play card");
		if (session.attributes().contains("table")) {
			Player player = session.attribute("player");
			Table table = session.attribute("table");
			System.out.println(request.queryParams("cardId"));
			int cardId = Integer.parseInt(request.queryParams("cardId"));
			return table.playCard(player, cardId);
    	}
        return false;
	}

    private static ModelAndView startDealing(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("table")) {
    		Table table = session.attribute("table");
    		table.startDealing();
    	}
        return loadCurrentGame(request);
	}
    
    private static ModelAndView takeMoreCards(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("table")) {
    		Table table = session.attribute("table");
    		Player player = session.attribute("player");
    		if (player.getHand().size()<8) {
    			player.takeCards(table.getDeck().deal());
    		}
    	}
        return loadCurrentGame(request);
	}

    private static ModelAndView joinTable(Request request) {
    	int id = Integer.decode(request.params(":id"));
        Table table = TablesService.getTableById(id);
        if (table != null) {
	        table.addPlayer(ConnectedPlayerService.getCurrentPlayer(request));
	        request.session().attribute("table", table);
        }
        return loadCurrentGame(request);
	}


	private static ModelAndView loadCurrentGame(Request request) {
		Session session = request.session();
		if (!session.attributes().contains("player")) {
			return new ModelAndView(null, "connect.mustache");
		}
		Player player = session.attribute("player");
		
		if (!session.attributes().contains("table")) {
			Map tablesModel = new HashMap<>();
			tablesModel.put("player", session.attribute("player"));
			tablesModel.put("tables", TablesService.getTables());
			return new ModelAndView(tablesModel, "tables.mustache");
		}
		Table table = session.attribute("table");
		
		if(!TablesService.hasTable(table)) {
			session.removeAttribute("table");
			table = null;
		}
		
		if (player != null && table != null) {
			HashMap<String, Object> tableModel = new HashMap<>();
			tableModel.put("player", player);
			tableModel.put("table", table);
			tableModel.put("otherPlayers", table.getOtherPlayers(player));
			tableModel.put("gameActions", new GameActions(player, table));
			return new ModelAndView(tableModel, "table.mustache");
		}
		
		return new ModelAndView(null, "connect.mustache");
	}
    
    
}
