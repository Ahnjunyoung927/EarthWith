package com.kh.eco.admin.model.service;

import java.util.List;

import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminCommentDTO;
import com.kh.eco.common.PageInfo;

public interface AdminService {
	
	
	List<AdminBoardDTO> findBoardAll(int pageNo);
	
	AdminBoardDTO findByBoardNo(Long boardNo);
	
	List<AdminCommentDTO> findCommentAll(int pageNo);
	
	AdminCommentDTO findByCommentNo(Long boardNo);
	
	void deleteBoard(Long boardNo);
	
	// int deleteByBoardNo(Long boardNo);
	// 삭제는 게시판 담당자가 만든 기능에 중복될것 같으니 일단 구현 대기
	// int deleteByCommentNo(Long commentNo);
	
	
}
