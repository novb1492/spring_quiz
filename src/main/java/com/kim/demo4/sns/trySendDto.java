package com.kim.demo4.sns;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class trySendDto {
	@NotBlank(message = "빈칸입니다")
	private String phoneOrEmail;
	@NotBlank(message = "kind 빈칸입니다")
	private String kind;
	@NotBlank(message = "detail 빈칸입니다")
	private String detail;
}
