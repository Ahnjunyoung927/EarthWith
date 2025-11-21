package com.kh.eco.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminCommentDTO;
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
	public ResponseEntity<List<AdminBoardDTO>> findBoardAll(@RequestParam(name="page", defaultValue="0") int pageNo ){
		List<AdminBoardDTO> boards = adminService.findBoardAll(pageNo);
		return ResponseEntity.ok(boards);
	}
	
	@GetMapping("comments")
	public ResponseEntity<List<AdminCommentDTO>> findCommentAll(@RequestParam(name="page", defaultValue="0") int pageNo){
		List<AdminCommentDTO> comments = adminService.findCommentAll(pageNo);
		return ResponseEntity.ok(comments);
	}
	
	@DeleteMapping("boards")
	public ResponseEntity<?> deleteBoard(@RequestParam(name="boardNo") Long boardNo){
		adminService.deleteBoard(boardNo);
		return ResponseEntity.ok("게시글이 비공개처리 되었습니다.");
	}
	
	
	
}
