package com.kh.eco.comment.model.service;

import java.util.List;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
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
}
