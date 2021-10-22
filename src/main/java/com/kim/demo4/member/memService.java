package com.kim.demo4.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Daos.memberDao;


@Service
public class memService {
	@Autowired
	private memberDao memberDao;
	
	public void insert() {
		System.out.println("insert");
		memberDto memberDTO=new memberDto();
		memberDTO.setEmail("em12313");
		memberDTO.setPwd("3123121");
		memberDao.insert(memberDTO);
	}
}
