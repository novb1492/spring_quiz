package com.kim.demo4.apis.naver.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.kim.demo4.utillService;
import com.kim.demo4.member.memService;
import com.kim.demo4.member.memberDto;

@Service
public class naverLoginService {
	
	  private final static Logger LOGGER=LoggerFactory.getLogger(naverLoginService.class);
	    private String loginCallback="/demo4/naver/loginCallback";
	    @Autowired
	    private memService userService;
	    @Autowired
	    private com.kim.demo4.apis.requestTo requestTo;
	    
	    public JSONObject getNaverLogin(String naverId) {
	        LOGGER.info("getNaverLogin");
	        String state="";
	        try {
	            state = URLEncoder.encode(loginCallback, "UTF-8");
	            return utillService.makeJson(true, "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+naverId+"&redirect_uri="+""+loginCallback+""+"&state="+state+"");
	        } catch (UnsupportedEncodingException e1) {
	            throw new RuntimeException("naverLogin 오류 발생");
	        } 
	    }
	    public memberDto tryNaverLogin(JSONObject tokens,HttpServletResponse response) {
	        LOGGER.info("tryNaverLogin naverLoginService");
	        HttpHeaders headers=requestTo.getHeaders();
	        headers.add("Authorization","Bearer " + tokens.get("access_token"));
	        JSONObject getNaver=requestTo.requestToApi("https://openapi.naver.com/v1/nid/me", headers); 
	        LOGGER.info(getNaver.toString());
	        return userService.insertOauth(makeVo((LinkedHashMap<String,Object>)getNaver.get("response")));
	    }
	    private memberDto makeVo(LinkedHashMap<String,Object> getNaver) {
	        LOGGER.info("makeVo");
	        memberDto vo=memberDto.builder()
	                        .email((String)getNaver.get("email"))
	                        .name((String)getNaver.get("name"))
	                        .created(Timestamp.valueOf(LocalDateTime.now()))
	                        .gender("남")
	                        .provider("kakao")
	                        .address("주소,정보,없음")
	                        .role("user")
	                        .build();
	        LOGGER.info("통과");                
	        return vo;
	    }
}
