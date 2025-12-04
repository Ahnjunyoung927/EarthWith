package com.kh.eco.comment.model.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;
import com.kh.eco.comment.model.vo.CommentVO;

public interface CommentService {

	/**
	 * 댓글 작성
	 */
	CommentVO insertComment(CommentDTO comment, CustomUserDetails userDetails);
	
	/**
	 * 댓글 조회
	 */
	List<CommentDTO> findAll(Long boardNo);
	
	/**
	 * 댓글 존재여부 확인
	 */
	int existById(Long CommentNo);
	
	/**
	 * 댓글 신고
	 */
	int commentReport(CommentReportDTO reportDTO);
	
	/**
	 * 댓글 본인 여부
	 */
	boolean isOwner(Long commentNo, int mno);
	
	/**
	 * 댓글 삭제
	 */
	int deleteComment(Long commentNo);
	
	/**
	 * 댓글 수정
	 */
	int updateComment(CommentDTO comment);
}
