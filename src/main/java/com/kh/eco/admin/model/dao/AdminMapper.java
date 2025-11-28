package com.kh.eco.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminCommentDTO;

@Mapper
public interface AdminMapper {
	
	List<AdminBoardDTO> findBoardAll(RowBounds rb);
	
	List<AdminBoardDTO> findReportedBoard(RowBounds rb);
	
	List<AdminCommentDTO> findCommentAll(RowBounds rb);
	
	List<AdminCommentDTO> findReportedComment(RowBounds rb);
	
	int countBoards();
	int countReportedBoards();
	
	int countComments();
	int countReportedComments();

}
