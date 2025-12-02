package com.kh.eco.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.vo.CommentVO;

@Mapper
public interface CommentMapper {

	/**
	 * 댓글 작성
	 * DTO -> VO 로 가공된 값(댓글)
	 * @param c
	 */
	void insertComment(CommentVO c);
	
	/**
	 * 댓글 조회
	 */
	List<CommentDTO> findAll(Long boardNo);
	
}
