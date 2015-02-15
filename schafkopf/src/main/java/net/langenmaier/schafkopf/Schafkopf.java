package net.langenmaier.schafkopf;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import net.langenmaier.schafkopf.models.Bot;
import net.langenmaier.schafkopf.models.GamePhase;
import net.langenmaier.schafkopf.models.Player;
import net.langenmaier.schafkopf.models.Table;
import net.langenmaier.schafkopf.services.ConnectedPlayerService;
import net.langenmaier.schafkopf.services.TablesService;
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
        post("/table/addBot", (request, reponse) -> addBotToTable(request), new MustacheTemplateEngine());
        get("/table/otherPlayers", (request, reponse) -> updateOtherPlayers(request), new MustacheTemplateEngine());
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
    
    private static ModelAndView addBotToTable(Request request) {
    	Session session = request.session();
		if (session.attributes().contains("table")) {
    		Table table = session.attribute("table");
    		Bot bot = new Bot();
    		table.addPlayer(bot);
    		(new Thread(bot)).start();
    		System.out.println("BOT ADDED");
    	}
        return loadCurrentGame(request);
	}
    
    private static ModelAndView updateOtherPlayers(Request request) {
    	Session session = request.session();
    	synchronized (Schafkopf.class) {
    		try {
    			Schafkopf.class.wait(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (session.attributes().contains("table")) {
			Table table = session.attribute("table");
			Player player = session.attribute("player");
			HashMap<String, Object> tableModel = new HashMap<>();
    		tableModel.put("otherPlayers", table.getOtherPlayers(player));
    		System.out.println(tableModel.size());
    		return new ModelAndView(tableModel, "otherPlayers.mustache");
    	}
        return new ModelAndView(null, "otherPlayers.mustache");
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
			System.out.println(table.hashCode());
			tableModel.put("otherPlayers", table.getOtherPlayers(player));
			tableModel.put("isOwner", player == table.getOwner());
			tableModel.put("isPlayer", player != table.getOwner());
			tableModel.put("canAddBot", (player == table.getOwner()) && (table.getPlayers().size()<4));
			tableModel.put("canDeal", (player == table.getDealer()) && (table.getGamePhase() == GamePhase.NO_GAME));
			tableModel.put("canTakeMoreCards", (player.isActivePlayer() && (player.getHand().size()<8)));
			return new ModelAndView(tableModel, "table.mustache");
		}
		
		return new ModelAndView(null, "connect.mustache");
	}
    
    
}
