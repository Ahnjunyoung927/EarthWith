package com.kh.eco.admin.model.service;

import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminCommentDTO;
import com.kh.eco.admin.model.dto.CommentPageResponse;
import com.kh.eco.admin.model.dto.PageResponse;

public interface AdminService {
	
	
	PageResponse findBoardAll(int pageNo);
	
	PageResponse findReportedBoard(int pageNo);
	
	AdminBoardDTO findByBoardNo(Long boardNo);
	
	CommentPageResponse findCommentAll(int pageNo);
	
	CommentPageResponse findReportedComment(int pageNo);
	
	AdminCommentDTO findByCommentNo(Long boardNo);
	
	void deleteBoard(Long boardNo);
	
	// int deleteByBoardNo(Long boardNo);
	// 삭제는 게시판 담당자가 만든 기능에 중복될것 같으니 일단 구현 대기
	// int deleteByCommentNo(Long commentNo);
	
	
}
