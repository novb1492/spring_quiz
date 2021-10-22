package com.kim.demo4;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kim.demo4.member.memService;
import com.kim.demo4.member.memberDto;

@RestController
public class restController {
	@Autowired
	private memService memService;
	
	@RequestMapping(value = "/user/crud/**",method = RequestMethod.POST)
	public void tryJoin(@RequestBody memberDto memberDto,HttpServletResponse response) {
		System.out.println("tryJoin");
		memService.insert();
	}
}
