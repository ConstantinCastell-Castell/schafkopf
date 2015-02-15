package net.langenmaier.schafkopf.utils;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Route;

public class JsonTransformer implements ResponseTransformer {
	
	private Gson gson = new Gson();
	
	@Override
	public String render(Object model) throws Exception {
		return gson.toJson(model);
	}

}
