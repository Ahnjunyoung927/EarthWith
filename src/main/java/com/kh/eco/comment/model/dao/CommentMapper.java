package com.kh.eco.comment.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.vo.CommentVO;

@Mapper
public interface CommentMapper {

	void save(CommentVO c);
	
	List<CommentDTO> findAll(Long boardNo);
	
}
