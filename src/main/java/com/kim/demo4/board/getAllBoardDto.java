package com.kim.demo4.board;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class getAllBoardDto {
	private int id;
	private String email;
	private String text;
	private String title;
	private Timestamp created;
	private int hit;
	private int totalcount;
}
