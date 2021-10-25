package com.kim.demo4;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.demo4.apis.kakao.kakaoService;
import com.kim.demo4.apis.naver.naverService;
import com.kim.demo4.board.boardService;
import com.kim.demo4.board.tryArticleInsertDto;
import com.kim.demo4.board.tryDeleteDto;
import com.kim.demo4.member.memService;
import com.kim.demo4.member.tryInsertDto;
import com.kim.demo4.member.tryLoginDto;
import com.kim.demo4.member.tryUpdateMemDto;
import com.kim.demo4.sns.MailService;
import com.kim.demo4.sns.tryCheckNumDto;
import com.kim.demo4.sns.trySendDto;
import com.kim.demo4.uploadService.uploadService;



@RestController
public class restController {
	private static Logger logger=LoggerFactory.getLogger(restController.class);
	
	@Autowired
	private memService memService;
	
	@Autowired
	private com.kim.demo4.sns.snsSerives snsSerives;
	@Autowired
	private kakaoService kakaoService;
	
	@Autowired
	private uploadService uploadService;
	@Autowired
	private boardService boardService;
	@Autowired
	private naverService naverService;



	
	@RequestMapping(value = "/user/crud/**",method = RequestMethod.POST)
	public JSONObject tryJoin(@Valid @RequestBody tryInsertDto insertDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("tryJoin");
		return memService.insert(insertDto, request);
	}
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public JSONObject tryLogin(@Valid @RequestBody tryLoginDto loginDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("tryJoin");
		 return memService.login(loginDto, request);
	}
	@RequestMapping(value = "/user/crud/**",method = RequestMethod.GET)
	public JSONObject checkSame(HttpServletRequest request,HttpServletResponse response) {
		logger.debug("checkSame");
		return memService.checkSame(request);
	}
	@RequestMapping(value = "/user/crud/**",method = RequestMethod.PUT)
	public JSONObject tryUpdate(@Valid @RequestBody tryUpdateMemDto updateMemDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("tryUpdate");
		return memService.tryUpdate(updateMemDto,request);
	}
	@RequestMapping(value = "/user/**",method = RequestMethod.GET)
	public void logout(HttpServletRequest request,HttpServletResponse response) {
		logger.debug("logout");
		request.getSession().invalidate();
		utillService.doRedirect(response, "/demo4/");
	}
	@RequestMapping(value = "/sns/**",method = RequestMethod.POST)
	public JSONObject sendSns(@Valid @RequestBody trySendDto sendDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("sendSns");
		return snsSerives.send(sendDto,request);
	}
	@RequestMapping(value = "/sns/**",method = RequestMethod.PUT)
	public JSONObject sendSns(@Valid @RequestBody tryCheckNumDto checkNumDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("sendSns");
		 return snsSerives.checkNum(checkNumDto, request);
	}
	@RequestMapping(value = "/kakao/showPage",method = RequestMethod.GET)
    public JSONObject showKakaoPage(HttpServletRequest request ,HttpServletResponse response) {
		logger.info("showKakaoLoginPage restcontroller");
        return kakaoService.showPage(request);
    }
    @GetMapping("/kakao/callback/**")
    public void kakaoCallback(HttpServletRequest request ,HttpServletResponse response) {
    	logger.info("showKakaoLoginPage restcontroller");
        kakaoService.callback(request, response);
       
    }
    @RequestMapping(value = "/img",method = RequestMethod.POST)
	public JSONObject img(MultipartHttpServletRequest request,HttpSession session,HttpServletResponse response) {
		System.out.println("img");
		utillService.checkLogin(session, response);
		return uploadService.imageUpload(request);
    }
	@RequestMapping(value = "/board/crud/**",method = RequestMethod.POST)
	public JSONObject tryInsertArticle(@Valid @RequestBody tryArticleInsertDto articleInsertDto ,HttpSession session,HttpServletResponse response) {
		logger.debug("tryInsertArticle");
		utillService.checkLogin(session, response);
		return boardService.insert(articleInsertDto, session);
	}
	@RequestMapping(value = "/board/crud/**",method = RequestMethod.DELETE)
	public JSONObject tryDeleteArticle(HttpServletRequest request  ,HttpSession session,HttpServletResponse response) {
		logger.debug("tryDeleteArticle");
		utillService.checkLogin(session, response);
		return boardService.delete(request, session);
	}
	   @GetMapping("/naver/showPage")
	    public JSONObject showNaverLoginPage(HttpServletRequest request ,HttpServletResponse response) {
	        logger.info("showNaverLoginPage restcontroller");
	        return naverService.getNaverLogin();
	    }
	    @GetMapping("/naver/loginCallback")
	    public void naverLoginCallback(HttpServletRequest request ,HttpServletResponse response) {
	    	logger.info("naverLoginCallback restcontroller");
	        naverService.tryNaverLogin(request,response);
	    }

}
