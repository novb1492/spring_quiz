package com.kim.demo4.sns;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kim.demo4.intEnums;
import com.kim.demo4.utillService;

@Service
public class confrimService {
	private static Logger logger=LoggerFactory.getLogger(confrimService.class);
	private final int confrimCoolMin=intEnums.coolTime.getValue();
	
	public void confrimNum(Timestamp reqTimestamp,String num,String submitNum) {
		logger.debug("confrimNum");
		String message=null;
		if(LocalDateTime.now().isAfter(reqTimestamp.toLocalDateTime().plusMinutes(confrimCoolMin))) {
			message="유효시간이 지났습니다";
		}else if(!num.equals(submitNum)) {
			message="인증번호 불일치";
		}else {
			logger.debug("인증번호 유효성 통과");
			return;
		}
		throw utillService.makeRunTimeEx(message, "confrimNum");
	}
}
