package com.kh.eco.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.admin.model.dto.CommentPageResponse;
import com.kh.eco.admin.model.dto.PageResponse;
import com.kh.eco.admin.model.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	

	@GetMapping("boards")
	public ResponseEntity<PageResponse> findBoardAll(@RequestParam(name="page", defaultValue="0") int pageNo ){
	    PageResponse result = adminService.findBoardAll(pageNo);
	    log.info("{}", result);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("boards/reported")
	public ResponseEntity<PageResponse> findReportedBoard(@RequestParam(name="page", defaultValue="0") int pageNo ){
	    PageResponse result = adminService.findReportedBoard(pageNo);
	    log.info("{}", result);
	    return ResponseEntity.ok(result);
	} 
	
	@GetMapping("comments")
	public ResponseEntity<CommentPageResponse> findCommentAll(@RequestParam(name="page", defaultValue="0") int pageNo){
		CommentPageResponse result = adminService.findCommentAll(pageNo);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("comments/reported")
	public ResponseEntity<CommentPageResponse> findReportedComment(@RequestParam(name="page", defaultValue="0") int pageNo ){
	    CommentPageResponse result = adminService.findReportedComment(pageNo);
	    log.info("{}", result);
	    return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("boards")
	public ResponseEntity<?> deleteBoard(@RequestParam(name="boardNo") Long boardNo){
		adminService.deleteBoard(boardNo);
		return ResponseEntity.ok("게시글이 비공개처리 되었습니다.");
	}
	
}
