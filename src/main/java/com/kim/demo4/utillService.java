package com.kim.demo4;

import org.json.simple.JSONObject;

public class utillService {
	
	public static JSONObject makeJson(boolean flag,String message) {
		System.out.println("makeJson");
		JSONObject response=new JSONObject();
		response.put("flag", flag);
		response.put("message", message);
		return response;
	}
}
