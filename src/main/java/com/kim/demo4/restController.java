package com.kim.demo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kim.demo4.member.memService;

@RestController
public class restController {
	@Autowired
	private memService memService;
	
	@RequestMapping(value = "/user/crud/**",method = RequestMethod.GET)
	public void tryJoin() {
		System.out.println("tryJoin");
		memService.insert();
	}
}
