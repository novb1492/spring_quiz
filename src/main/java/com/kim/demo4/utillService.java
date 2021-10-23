package com.kim.demo4;


import java.util.Random;


import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class utillService {
	private static Logger logger=LoggerFactory.getLogger(utillService.class);
	
	public static JSONObject makeJson(boolean flag,String message) {
		logger.debug("makeJson");
		JSONObject response=new JSONObject();
		response.put("flag", flag);
		response.put("message", message);
		return response;
	}
	 public  static String GetRandomNum(int end) {
	        String num="";
	        Random random=new Random();
	        for(int i=0;i<end;i++){
	            num+=Integer.toString(random.nextInt(10));
	        }
	        return num;
	  }
	 public static RuntimeException makeRunTimeEx(String message,String methodName) {
		 logger.debug("makeRunTimeEx");
		 logger.debug("에러 메소드: "+methodName);
		return new RuntimeException(message);
	}
	 public static void deleteSession(HttpSession session,String name) {
		logger.debug("deleteSession");
		session.removeAttribute(name);
	}
}
