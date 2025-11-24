package com.kh.eco.board.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
	
	private Long boardNo;      
	private String memberName;   
	private String boardCategory;   
	private String boardTitle;      
	private String boardContent;   
	private Date regDate;         
	private int viewCount;        
	private int likeCount;          
	
}