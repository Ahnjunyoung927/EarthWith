package com.kh.eco.comment.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentVO {

	private Long commentNo;
	private Long refMno;
	private Long refBno;
	private String commentContent;
	private Date regDate;
	private char status;
}
