package com.kim.demo4.member;




import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class memberDto {
	

	private String email;
	private String pwd;
	private String address;
	private String name;
	private Timestamp created;



}
