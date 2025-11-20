package com.kh.eco.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardVO {
	
	private Long boardNo;
	private int refMno;
	private String boardCategory;
	private String boardtitle;
	private String boardContent;
	private Date regDate;
	private long viewCount;
	private char status;

}
