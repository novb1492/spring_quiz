package com.kim.demo4.apis.kakao.login;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;


import com.kim.demo4.member.memService;
import com.kim.demo4.member.memberDto;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class kakaoLoginService {
   
    private final static Logger LOGGER=LoggerFactory.getLogger(kakaoLoginService.class);

    private String loginCallbackUrl="http://localhost:8080/demo4/kakao/callback?scope=login";

    @Autowired
    private com.kim.demo4.apis.requestTo requestTo;
    @Autowired
    private memService userService;
   

    public String showLoingPage(String apikey) {
        LOGGER.info("kakaoLoginService showLoginPage");
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+apikey+"&redirect_uri="+loginCallbackUrl+"";
    }
    public memberDto tryLogin(HttpServletRequest request,String apiKey) {
        LOGGER.info("tryLogin");
        JSONObject reseponseTokens=getKakaoToken(request.getParameter("code").toString(), apiKey);
        LOGGER.info(reseponseTokens.toString());
        JSONObject responseUserInfor=getUserInfor(reseponseTokens.get("access_token").toString());
        LOGGER.info(responseUserInfor.toString());
        LinkedHashMap<String,Object>userInfor=(LinkedHashMap<String, Object>) responseUserInfor.get("kakao_account");
        memberDto vo=mapToVo(userInfor);
        userService.insertOauth(vo);
        return vo;
    }
    private JSONObject getKakaoToken(String code,String apikey) {
        LOGGER.info("getKakaoToken");
        HttpHeaders headers=requestTo.getHeaders();
        MultiValueMap<String,Object> body=requestTo.getMultiValueBody();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        body.add("grant_type", "authorization_code");
        body.add("client_id", apikey);
        body.add("redirect_uri", loginCallbackUrl);
        body.add("code", code);
        return requestTo.requestToApi(body, "https://kauth.kakao.com/oauth/token", headers);
    }
    private JSONObject getUserInfor(String accessToken) {
        LOGGER.info("getUserInfor");
        HttpHeaders headers=requestTo.getHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer "+accessToken);
        return requestTo.requestToApi("https://kapi.kakao.com/v2/user/me", headers);
    }
    private memberDto mapToVo(LinkedHashMap<String,Object>userInfor) {
        LOGGER.info("mapToVo");
        LinkedHashMap<String,Object>profile=(LinkedHashMap<String, Object>) userInfor.get("profile");
        memberDto vo=memberDto.builder()
                        .address("테스트 주소 안줌")
                        .email((String)userInfor.get("email"))
                        .name((String)profile.get("nickname"))
                        .created(Timestamp.valueOf(LocalDateTime.now()))
                        .gender("제공받지 못함")
                        .provider("kakao")
                        .build();
                        return vo;
    }
}
