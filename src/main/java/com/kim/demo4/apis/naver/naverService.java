package com.kim.demo4.apis.naver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kim.demo4.utillService;
import com.kim.demo4.apis.naver.login.naverLoginService;
import com.kim.demo4.member.memberDto;

@Service
public class naverService {
	 private final static Logger LOGGER=LoggerFactory.getLogger(naverService.class);

	  private RestTemplate restTemplate=new RestTemplate();
	    private final String get="authorization_code";
	    private final String update="refresh_token";
	    private final String delete="delete";
	    
	    private String naverId="uCpaKUxvJfq_PNFlwgy_";
	    private String pwd="SNjrG5x2hl";
	    private String loginCallbackUrl="http://localhost:8080/naver/loginCallback";


	    @Autowired
	    private naverLoginService naverLoginService;


	    public JSONObject getNaverLogin() {
	       LOGGER.info("getNaverLogin");
	       return naverLoginService.getNaverLogin(naverId);
	    }
	    public void tryNaverLogin(HttpServletRequest request,HttpServletResponse response) {
	        LOGGER.info("tryNaverLogin naverService");
	        memberDto uservo=naverLoginService.tryNaverLogin(getToken(request.getParameter("code"), request.getParameter("state")), response);
	        request.getSession().setAttribute("email", uservo.getEmail());
	        request.getSession().setAttribute("role", uservo.getRole());
		       utillService.doRedirect(response, "/demo4/");
	       
	        
	    }
	    private JSONObject getToken(String code,String state) {
	        LOGGER.info("getToken");
	        return restTemplate.getForObject("https://nid.naver.com/oauth2.0/token?grant_type="+get+"&client_id="+naverId+"&client_secret="+pwd+"&code="+code+"&state="+state+"", JSONObject.class);
	    }
}
