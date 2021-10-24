package com.kim.demo4.board;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class boardDto {
	
	private int id;
	private String email;
	private String text;
	private String title;
	private Timestamp created;
}
