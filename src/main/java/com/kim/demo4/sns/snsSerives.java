package com.kim.demo4.sns;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.demo4.utillService;

@Service
public class snsSerives {
	
	private static Logger logger=LoggerFactory.getLogger(snsSerives.class);
	

	@Autowired
	private MailService mailService;
	
	public JSONObject send(trySendDto sendDto,HttpServletRequest request) {
		logger.debug("send");
		String kind=sendDto.getKind();
		if(kind.equals("email")) {
			logger.debug("email 인증번호 전송요청");
			System.out.println(sendDto.getPhoneOrEmail());
			return mailService.sendMail(sendDto,request);
		}else if(kind.equals("phone")) {
			logger.debug("phone 인증번호 전송요청");
			return null;
			
		}else {
			return utillService.makeJson(false, "지원하지 않는 인증 수단입니다");
		}
		
	}
	public JSONObject checkNum(tryCheckNumDto checkNumDto,HttpServletRequest request) {
		logger.debug("send");
		String kind=checkNumDto.getKind();
		if(kind.equals("email")) {
			logger.debug("email 인증번호 확인");
			 return mailService.confrimNum(checkNumDto,request);
		}else if(kind.equals("phone")) {
			logger.debug("phone 인증번호 확인");
			return null;
			
		}else {
			 return utillService.makeJson(false, "지원하지 않는 인증 수단입니다");
		}
	}
}
