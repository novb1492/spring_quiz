package com.kim.demo4;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.demo4.member.memService;
import com.kim.demo4.member.tryInsertDto;
import com.kim.demo4.sns.MailService;
import com.kim.demo4.sns.tryCheckNumDto;
import com.kim.demo4.sns.trySendDto;



@RestController
public class restController {
	private static Logger logger=LoggerFactory.getLogger(restController.class);
	
	@Autowired
	private memService memService;
	
	@Autowired
	private com.kim.demo4.sns.snsSerives snsSerives;
	



	
	@RequestMapping(value = "/user/crud/**",method = RequestMethod.POST)
	public JSONObject tryJoin(@Valid @RequestBody tryInsertDto insertDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("tryJoin");
		return memService.insert(insertDto, request);
	}
	@RequestMapping(value = "/sns/**",method = RequestMethod.POST)
	public JSONObject sendSns(@Valid @RequestBody trySendDto sendDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("sendSns");
		return snsSerives.send(sendDto,request);
	}
	@RequestMapping(value = "/sns/**",method = RequestMethod.PUT)
	public void sendSns(@Valid @RequestBody tryCheckNumDto checkNumDto,HttpServletRequest request,HttpServletResponse response) {
		logger.debug("sendSns");
		 snsSerives.checkNum(checkNumDto, request);
	}
}
