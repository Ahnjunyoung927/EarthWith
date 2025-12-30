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

import com.kh.eco.admin.model.dto.AdminBoardDetailDTO;
import com.kh.eco.admin.model.dto.PageResponse;
import com.kh.eco.admin.model.service.AdminBoardService;
import com.kh.eco.common.responseData.SuccessResponse;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("eco/admin/boards")
@RequiredArgsConstructor
public class AdminBoardController {
	
	private final AdminBoardService adminBoardService;
	
	@GetMapping() // 게시글 전체 조회
	public ResponseEntity<SuccessResponse<PageResponse>> findBoardAll(@RequestParam(name="page", defaultValue="0") @Min(value=0, message="잘못된 접근입니다.") int pageNo ){
	    PageResponse result = adminBoardService.findBoardAll(pageNo);
	    // log.info("{}", result);
	    /*
	    SuccessResponse sr = SuccessResponse.builder()
	    									.status(200)
	    									.success(true)
	    							  		.message("게시글 전체조회 성공")
	    							  		.data(result)
	    							  		.timeStamp(LocalDateTime.now())
	    							  		.build();
	    */
	    return SuccessResponse.ok(result, "게시글 전체조회 성공");
	}
	
	
	@GetMapping("reported") // 신고된 게시글만 조회
	public ResponseEntity<SuccessResponse<PageResponse>> findReportedBoard(@RequestParam(name="page", defaultValue="0") @Min(value=0, message="잘못된 접근입니다.") int pageNo ){
	    PageResponse result = adminBoardService.findReportedBoard(pageNo);
	    // log.info("{}", result);
	    return SuccessResponse.ok(result);
	}
	
	
	@GetMapping("{boardNo:\\d+}") // 게시글 상세 조회
	public ResponseEntity<SuccessResponse<AdminBoardDetailDTO>> selectBoard(@PathVariable(name="boardNo") @Min(value=1, message="잘못된 접근입니다.") Long boardNo){
		AdminBoardDetailDTO board = adminBoardService.findByBoardNo(boardNo);
		// log.info("{}", board);
		return SuccessResponse.ok(board);
	}
	
	@DeleteMapping("{boardNo}") // 게시글 삭제
	public ResponseEntity<SuccessResponse<String>> deleteBoard(@PathVariable(name="boardNo") @Min(value=1, message="잘못된 접근입니다.") Long boardNo){
		adminBoardService.deleteBoard(boardNo);
		return SuccessResponse.noContent("게시글이 비공개처리 되었습니다.");
	}
	
	@PutMapping("{boardNo}") // 게시글 복원
	public ResponseEntity<SuccessResponse<String>> restoreBoard(@PathVariable(name="boardNo") @Min(value=1, message="잘못된 접근입니다.") Long boardNo){
		// log.info("{}", boardNo);
		adminBoardService.restoreBoard(boardNo);
		return SuccessResponse.noContent("게시글이 복원 되었습니다.");
	}
	
	@PutMapping("report/{boardNo}") // 게시글 신고 확인 처리
	public ResponseEntity<SuccessResponse<String>> handleReport(@PathVariable(name="boardNo") @Min(value=1, message="잘못된 접근입니다.") Long boardNo){
		adminBoardService.handleReport(boardNo);
		return SuccessResponse.noContent("신고 확인 완료.");
	}
	

}










