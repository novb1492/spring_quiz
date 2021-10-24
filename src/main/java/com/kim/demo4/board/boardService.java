package com.kim.demo4.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kim.demo4.utillService;
import com.kim.demo4.apis.kakao.kakaoService;

@Service
public class boardService {
	
	 private final static Logger logger=LoggerFactory.getLogger(kakaoService.class);
	
	 public void checkLogin(HttpSession session,HttpServletResponse response) {
		 logger.debug("checkLogin");
		 String email=(String)session.getAttribute("email");
		 if(email==null) {
			 utillService.doRedirect(response, "/demo4/");
		 }
	 }
}
