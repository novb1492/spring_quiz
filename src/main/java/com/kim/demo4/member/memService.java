package com.kim.demo4.member;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kim.demo4.utillService;

import Daos.memberDao;


@Service
public class memService {
	@Autowired
	private memberDao memberDao;
	
	public JSONObject insert(tryInsertDto insertDto) {
		System.out.println("insert");
		String message="회원가입에 성공했습니다";
		try {
			insertDto.getAddress();
			memberDto dto=memberDto.builder()
									.email(insertDto.getEmail())
									.address(insertDto.getPostcode()+","+insertDto.getAddress()+","+insertDto.getDetailAddress())
									.created(Timestamp.valueOf(LocalDateTime.now()))
									.name(insertDto.getName())
									.pwd(new BCryptPasswordEncoder().encode(insertDto.getPwd()))
									.build();
						
			memberDao.insert(dto);
		
		} catch (Exception e) {
			message="회원가입에 실패했습니다";
		}
		return utillService.makeJson(true, message);
		
	}
}
