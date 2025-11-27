package com.kh.eco.comment.model.service;

import java.util.List;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.vo.CommentVO;

public interface CommentService {

	CommentVO save(CommentDTO comment, CustomUserDetails userDetails);
	
	List<CommentDTO> findAll(Long boardNo);
}
