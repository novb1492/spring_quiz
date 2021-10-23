package com.kim.demo4.member;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kim.demo4.utillService;

import Daos.memberDao;


@Service
public class memService {
	@Autowired
	private memberDao memberDao;
	private static Logger logger=LoggerFactory.getLogger(memService.class);
	
	public JSONObject insert(tryInsertDto insertDto,HttpServletRequest request) {
		logger.debug("insert");
		String message="알수 없는 오류 발생";
		confrim(insertDto);
		try {
			boolean flag=(boolean)request.getSession().getAttribute("flag");
			if(flag) {
				return insert(insertDto);
			}
			message="인증이 완료 되지 않았습니다";
		} catch (RuntimeException e) {
			message="인증요청을 번저 부탁드립니다";
		}
		throw utillService.makeRunTimeEx(message, "insert");
		
		
	}
	public void login(tryLoginDto loginDto,HttpServletRequest request) {
		logger.debug("login");
		
	}
	private void confrim(tryInsertDto insertDto) {
		logger.debug("confrim");
		String message=null;
		if(!insertDto.getPwd().equals(insertDto.getPwd2())) {
			message="비밀번호 불일치";
		}else if(countByEmail(insertDto.getEmail())) {
			message="이미존재하는 이메일입니다";
		}else {
			logger.debug("회원가입 유효성 통과");
			return;
		}
		throw utillService.makeRunTimeEx(message,"confrim" );
	}
	 public memberDto insertOauth(memberDto uservo) {
		 	logger.debug("insertOauth");
	        int already=memberDao.countByEmail(uservo.getEmail());
	        if(already==0){
	        	logger.debug(uservo+"로그인 회원가입시도");
	        	uservo.setPwd(new BCryptPasswordEncoder().encode("oauthpwd"));
	        	memberDao.insert(uservo);
	        }
	        return uservo;
	    }
	public JSONObject checkSame(HttpServletRequest request) {
		logger.debug("checkSame");
		String message=null;
		boolean flag=countByEmail(request.getParameter("email"));
		if(flag) {
			message="이미존재 하는 이메일입니다";
		}else {
			message="사용가능한 이메일입니다";
		}
		return utillService.makeJson(false, message);
	}
	private boolean countByEmail(String email) {
		logger.debug("countByEmail");
		if(memberDao.countByEmail(email)!=0) {
			return true;
		}
		return false;
	}
	
	public JSONObject insert(tryInsertDto insertDto) {
		logger.debug("insert");
		String message="회원가입에 성공했습니다";
		try {
			insertDto.getAddress();
			memberDto dto=memberDto.builder()
									.email(insertDto.getEmail())
									.address(insertDto.getPostcode()+","+insertDto.getAddress()+","+insertDto.getDetailAddress())
									.created(Timestamp.valueOf(LocalDateTime.now()))
									.name(insertDto.getName())
									.gender(insertDto.getGender())
									.pwd(new BCryptPasswordEncoder().encode(insertDto.getPwd()))
									.provider("home")
									.build();
						
			memberDao.insert(dto);
		
		} catch (Exception e) {
			e.printStackTrace();
			message="회원가입에 실패했습니다";
		}
		return utillService.makeJson(true, message);
		
	}
	
}
