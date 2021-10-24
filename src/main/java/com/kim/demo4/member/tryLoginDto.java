package com.kim.demo4.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class tryLoginDto {
	
	@Email(message = "이메일 형식이아닙니다")
	@NotBlank(message = "이메일이 빈칸입니다")
	private String email;
	
	@Size(min = 4,max = 10,message = "비밀번호는 최소 4자리 최대 10자리 입니다")
	@NotBlank(message = "비밀번호가 빈칸입니다")
	private String pwd;
}
