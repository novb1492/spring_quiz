package com.kim.demo4;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
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
	 public static int getTotalPage(int totalCount,int pagesize) {
		 logger.debug("getTotalpages");
		 logger.debug("총 개수"+totalCount);
	        int totalpage=0;
	        totalpage=totalCount/pagesize;
	        if(totalCount%pagesize>0){
	            totalpage++;
	        }
	        logger.debug(totalpage+"전체페이지");
	        if(totalpage==0){
	            totalpage=1;
	        }
	        logger.debug(totalpage+" 전체 페이지");
	        return totalpage;
	}
	    public static Map<String, Object> getStart(int nowPage,int pagesize) {
	    	logger.debug("getPagingStartEnd");
	    	int start=0;
	    	Map<String, Object>map=new HashMap<>();
	    	if(nowPage!=1) {
	    		start=(nowPage-1)*pagesize+1;
			}
			map.put("start", start);
			map.put("end", start+pagesize);
			return map;
		}
	    public static List<String> getImgSrc(String text) {
	    	System.out.println("getImgSrc");
	    	List<String>array=new ArrayList<>();
	    	Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
			Matcher matcher = nonValidPattern.matcher(text);
			while (matcher.find()) {
				array.add(matcher.group(1));
			}
			return array;
		}
	    public static Map<String, Object> getEmailAndRole(HttpServletRequest request) {
			System.out.println("getEmailAndRole");
			Map<String, Object>map=new HashMap<String, Object>();
			HttpSession session=request.getSession();
			map.put("email", session.getAttribute("email"));
			map.put("role", session.getAttribute("role"));
			return map;
		}
	    public static boolean checkNull(String obString) {
			System.out.println("checkNull");
			if(obString==null||obString.isBlank()||obString.equals("null")) {
				return true;
			}
			return false;
		}
	    
}
