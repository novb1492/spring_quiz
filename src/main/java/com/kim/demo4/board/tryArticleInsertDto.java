package com.kim.demo4.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class tryArticleInsertDto {
	
	@NotBlank(message = "본문이 빈칸입니다")
	private String text;
	
	@Size(max = 30,message = "제목은 최대 30글자입니다")
	@NotBlank(message = "제목이 빈칸입니다")
	private String title;
}
