package com.kh.eco.comment.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CommentDTO {
	
	@Min(value = 1, message = "댓글 번호의 값이 1보다 작을 수 없습니다.")
	@NotNull(message = "NULL 값이 올 수 없습니다.")
	private Long commentNo;
	
	@Min(value = 1, message = "회원 번호의 값이 1보다 작을 수 없습니다.")
	@NotNull(message = "NULL 값이 올 수 없습니다.")
	private Long refMno;
	
	@Min(value = 1, message = "게시글 번호의 값이 1보다 작을 수 없습니다.")
	@NotNull(message = "NULL 값이 올 수 없습니다.")
	private Long refBno;
	
	@NotBlank(message = "내용을 입력해주세요.")
	@NotNull(message = "NULL 값이 올 수 없습니다.")
	private String commentContent;
	
	private Date regDate;
	private char status;


}
