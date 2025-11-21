package com.kh.eco.admin.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminBoardDTO {
	
	private Long boardNo;
	private String boardTitle;
	private String boardContent;
	private Date regDate;
	private String status;
	private int boardReportCount;
	private String memberId;

}
