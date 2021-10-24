package com.kim.demo4;


import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class utillService {
	private static Logger logger=LoggerFactory.getLogger(utillService.class);
	 private static  String getEmail=stringEnums.email.getValue();
	 
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
	 public static void doRedirect(HttpServletResponse response,String url) {
		logger.debug("doRedirect");
		logger.info(url+"리다이렉트 요청 url");
	        try {
	            response.sendRedirect(url);
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.info("doRedirect error"+e.getMessage());
	        }
	}
	 public static void checkLogin(HttpSession session,HttpServletResponse response) {
		 String email=(String)session.getAttribute(getEmail);
		 if(email==null) {
			 doRedirect(response, "/demo4/");
		 }
	}
}
