package com.kim.demo4.sns;

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
	
	public JSONObject sendMail(trySendDto sendDto,HttpServletRequest request) {
		logger.debug("sendMail");
		String message="인증번호 전송완료";
		try {
			HttpSession session=request.getSession();
			String randNum=utillService.GetRandomNum(numLength);
			String email=sendDto.getPhoneOrEmail();
			session.setAttribute("email", email);
			session.setAttribute("num", randNum);
			session.setAttribute("detail", sendDto.getDetail());
			session.setAttribute("flag", false);
			
			sendMail(email, "안녕하세요 00입니다", "인증번호는 "+randNum+"입니다");
		} catch (Exception e) {
			message="인증번호 전송실패";
		}
	
		return utillService.makeJson(true, message);
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
