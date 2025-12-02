package com.kh.eco.board.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Setter
public class BoardVO {
	
	private Long boardNo;            // TB_BOARD 게시글번호
	private Integer refMno;
	private String boardCategory;    // TB_BOARD 게시글카테고리 C%
	private String boardTitle;       // TB_BOARD 게시글제목
	private String boardContent;     // TB_BOARD 게시글내용
	private Date regDate;            // TB_BOARD 작성일자
	private long viewCount;
	private char status;

}
