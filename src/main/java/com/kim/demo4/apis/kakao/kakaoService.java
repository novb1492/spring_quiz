package com.kim.demo4.apis.kakao;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.demo4.utillService;
import com.kim.demo4.member.memberDto;

@Service
public class kakaoService {
	 private final static Logger LOGGER=LoggerFactory.getLogger(kakaoService.class);
	   
	    private String appKey="065752680e02c1edc00247e4d773b9b7";
	    private String apiKey="eddc922f499b13499caccb77af50dc03";
	    


	    
	    @Autowired
	    private com.kim.demo4.apis.kakao.login.kakaoLoginService kakaoLoginService;


	    public JSONObject showPage(HttpServletRequest request) {
	        LOGGER.info("kakaoService showLoginPage");
	        String scope=request.getParameter("scope");
	        String url=null;
	        if(scope.equals("login")){
	            LOGGER.info("카카오 로그인 화면요청");
	            url=kakaoLoginService.showLoingPage(apiKey);
	        }else {
	        	return utillService.makeJson(false, "지원하지 않는 카카오기능입니다");
	        }
	        return utillService.makeJson(true, url);
	    }
	    public void callback(HttpServletRequest request,HttpServletResponse response) {
	        LOGGER.info("callback");
	        String scope=request.getParameter("scope");
	        if(scope.equals("login")){
	            LOGGER.info("카카오 로그인 콜백");
	            tryKakaoLogin(request,response);
	        }else{
	            throw new RuntimeException("카카오 콜백 잘못된 스코프입니다");
	        }
	    }
	    private void tryKakaoLogin(HttpServletRequest request,HttpServletResponse response) {
	        LOGGER.info("tryKakaoLogin");
	        memberDto uservo =kakaoLoginService.tryLogin(request,apiKey);
	        request.getSession().setAttribute("email", uservo.getEmail());
	       utillService.doRedirect(response, "/demo4/");
	       
	    }
}
