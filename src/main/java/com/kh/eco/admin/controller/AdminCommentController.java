package com.kh.eco.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.admin.model.dto.AdminCommentDTO;
import com.kh.eco.admin.model.dto.CommentPageResponse;
import com.kh.eco.admin.model.service.AdminCommentService;
import com.kh.eco.common.responseData.SuccessResponse;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("eco/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

	private final AdminCommentService adminCommentService;
	
	@GetMapping() // 댓글 전체 조회
	public ResponseEntity<SuccessResponse<CommentPageResponse>> findCommentAll(@RequestParam(name="page", defaultValue="0") @Min(value=0, message="잘못된 접근입니다.") int pageNo){
		CommentPageResponse result = adminCommentService.findCommentAll(pageNo);
		return SuccessResponse.ok(result, "댓글 전체조회 성공");
	}
	
	@GetMapping("reported") // 신고된 댓글만 조회
	public ResponseEntity<SuccessResponse<CommentPageResponse>> findReportedComment(@RequestParam(name="page", defaultValue="0") @Min(value=0, message="잘못된 접근입니다.") int pageNo ){
	    CommentPageResponse result = adminCommentService.findReportedComment(pageNo);
	    // log.info("{}", result);
	    return SuccessResponse.ok(result);
	}
	
	@GetMapping("{commentNo:\\d+}") // 댓글 상세 조회
	public ResponseEntity<SuccessResponse<AdminCommentDTO>> selectComment(@PathVariable(name="commentNo") @Min(value=1, message="잘못된 접근입니다.") Long commentNo){
		AdminCommentDTO comment = adminCommentService.findByCommentNo(commentNo);
		// log.info("{}", comment);
		return SuccessResponse.ok(comment);
	}
	
	@DeleteMapping("{commentNo}") // 댓글 삭제
	public ResponseEntity<SuccessResponse<String>> deleteComment(@PathVariable(name="commentNo") @Min(value=1, message="잘못된 접근입니다.") Long commentNo){
		adminCommentService.deleteComment(commentNo);
		return SuccessResponse.noContent("댓글이 비공개처리 되었습니다.");
	}
	
	@PutMapping("{commentNo}") // 댓글 복원
	public ResponseEntity<SuccessResponse<String>> restoreComment(@PathVariable(name="commentNo") @Min(value=1, message="잘못된 접근입니다.") Long commentNo){
		adminCommentService.restoreComment(commentNo);
		return SuccessResponse.noContent("댓글이 복원 되었습니다.");
	}
	
	@PutMapping("report/{commentNo}") // 댓글 신고 확인 처리
	public ResponseEntity<SuccessResponse<String>> handleReport(@PathVariable(name="commentNo") @Min(value=1, message="잘못된 접근입니다.") Long commentNo){
		
		adminCommentService.handleReport(commentNo);
		return SuccessResponse.noContent("신고 확인 완료.");
	}
	
	
	
	

}
