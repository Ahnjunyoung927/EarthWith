package com.kh.eco.comment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentDTO {
	
	private Long commentNo;
	private String commentContent;
	private String memberName;
	private String regDate;

}
