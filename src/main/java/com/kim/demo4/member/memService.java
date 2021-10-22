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
		String message=null;
		try {
			boolean flag=(boolean)request.getSession().getAttribute("flag");
			if(flag) {
				return insert(insertDto);
			}
			message="인증이 완료 되지 않았습니다";
		} catch (IllegalArgumentException e) {
			message="인증요청을 번저 부탁드립니다";
		}catch (Exception e) {
			message="알수 없는 오류 발생";
		}
		throw utillService.makeRunTimeEx(message, "insert");
		
		
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
									.build();
						
			memberDao.insert(dto);
		
		} catch (Exception e) {
			e.printStackTrace();
			message="회원가입에 실패했습니다";
		}
		return utillService.makeJson(true, message);
		
	}
	
}
