package com.kim.demo4.member;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class getMembersDto {
	private String email;
	private String address;
	private String name;
	private String gender;
	private Timestamp created;
	private String provider;
	private int totalCount;
	private int id;
	private String role;
}
