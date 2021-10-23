package com.kim.demo4.sns;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kim.demo4.intEnums;
import com.kim.demo4.utillService;

@Service
public class MailService {
	private static Logger logger=LoggerFactory.getLogger(MailService.class);
	private final int numLength=intEnums.randNumLength.getValue();
	
	@Autowired 
	private JavaMailSender mailSender;
	@Autowired
	private confrimService confrimService;
	
	public JSONObject sendMail(trySendDto sendDto,HttpServletRequest request) {
		logger.debug("sendMail");
		String message="인증번호 전송완료";
		try {
			HttpSession session=request.getSession();
			String randNum=utillService.GetRandomNum(numLength);
			String email=sendDto.getPhoneOrEmail();
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("email", email);
			map.put("num", randNum);
			map.put("detail",  sendDto.getDetail());
			map.put("flag", false);
			map.put("requestTime", LocalDateTime.now());
			session.setAttribute(sendDto.getDetail(), map);
			sendMail(email, "안녕하세요 00입니다", "인증번호는 "+randNum+"입니다");
			return utillService.makeJson(true, message);
		} catch (Exception e) {
			message="인증번호 전송실패";
		}
	
		return utillService.makeJson(false, message);
	}
	public JSONObject confrimNum(tryCheckNumDto checkNumDto,HttpServletRequest request) {
		logger.debug("confrimNum");
		String message="알수 없는 에러발생";
		try {
			HttpSession httpSession=request.getSession();
			Map<String, Object>map=(Map<String, Object>) httpSession.getAttribute("confrim");
			LocalDateTime requestTime=(LocalDateTime)map.get("requestTime");
			System.out.println(map.get("requestTime")+" 요청시간");
			confrimService.confrimNum(requestTime,(String)map.get("num"), checkNumDto.getNum());
			httpSession.setAttribute("flag", true);
			
			return utillService.makeJson(true, "인증에 성공했습니다");
		} catch (NullPointerException e) {
			message="인증요청부터 해주세요";
		}
		return utillService.makeJson(false, message);
	
	}
    private void sendMail(String to, String subject, String body) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
		    MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
		    messageHelper.setSubject(subject);//제목
		    messageHelper.setTo(to); // 수신자
		    messageHelper.setText(body);//내용
		    mailSender.send(message);
		}catch(Exception e) {
		    e.printStackTrace();
		}
    }
}
