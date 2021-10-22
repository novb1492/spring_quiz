package com.kim.demo4.sns;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class tryCheckNumDto {

	@NotBlank(message = "인증번호가 빈칸입니다")
	private String num;
	@NotBlank(message = "message가 빈칸입니다")
	private String kind;
	@NotBlank(message = "detail가 빈칸입니다")
	private String detail;
}
