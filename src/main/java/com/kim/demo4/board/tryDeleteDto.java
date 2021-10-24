package com.kim.demo4.board;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class tryDeleteDto {
	
	@NotBlank(message = "삭제할 글번호가 잘못되었습니다")
	private int id;
}
