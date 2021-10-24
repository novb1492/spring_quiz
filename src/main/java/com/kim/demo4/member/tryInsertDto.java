package com.kim.demo4.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class tryInsertDto {
	
	@NotBlank(message = "이메일이 빈칸입니다")
	@Email(message = "이메일 형식이 아닙니다")
	private String email;
	
	@NotBlank
	@Size(min = 4,max = 10,message = "비밀번호는 최소 4자리 최대 10자리입니다")
	private String pwd;
	@NotBlank
	@Size(min = 4,max = 10,message = "비밀번호는 최소 4자리 최대 10자리입니다")
	private String pwd2;
	
	@NotBlank(message = "우편번호가 빈칸입니다")
	private String postcode;
	
	@NotBlank(message = "주소가 빈칸입니다")
	private String address;
	
	@NotBlank(message = "상세주소가 빈칸입니다")
	private String detailAddress;
	
	@NotBlank(message = "이름이 빈칸입니다")
	private String name;
	
	@NotBlank(message = "성별을 선택해주세요")
	private String gender;
	
}
