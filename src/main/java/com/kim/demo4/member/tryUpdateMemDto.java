package com.kim.demo4.member;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class tryUpdateMemDto {
	
	private String postcode;
	private String address;
	private String detailAddress;
	
	@NotBlank(message = "디테일이 빈칸입니다")
	private String detail;
	
}
