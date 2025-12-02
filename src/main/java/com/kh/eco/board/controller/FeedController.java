package com.kh.eco.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.service.FeedService;
import com.kh.eco.exception.UsenameNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feeds")
public class FeedController {
	
	private final FeedService feedService;
	
	@GetMapping
	public ResponseEntity<List<FeedBoardDTO>> selectFeedList(@RequestParam(name = "category", defaultValue = "C") String category,
			                                      @RequestParam(name = "fetchOffset", required = false) Long fetchOffset,
			                                      @RequestParam(name = "limit", defaultValue = "3") Long limit) {
		
		log.info("GET /feeds 요청 - category={}, fetchOffset={}, limit={}",
                category, fetchOffset, limit);
		
		return ResponseEntity.ok(feedService.selectFeedList(category, fetchOffset, limit));
		
	}
	
	/**
	 * 피드 게시글 작성하기
	 * @param feed	게시글 값이 담긴 DTO
	 * @param file	첨부파일(NULL일 수도 있음)
	 * @param userDetails	회원 번호가 담긴 정보
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> insertFeed(@Valid FeedBoardDTO feed, @RequestParam(name="file", required=false) MultipartFile file, 
			                          @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		int memberNo = userDetails.getMemberNo();
		
		// 작성자 정보 SET
		feed.setBoardAuthor(memberNo);
		
		// INSERT 요청
		feedService.insertFeed(feed, file);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	
	@DeleteMapping("/{boardNo}/delete")
	public ResponseEntity<?> deleteFeed(@PathVariable(name = "boardNo") @Min(value = 1, message = "게시글이 존재하지 않습니다.") int boardNo, @AuthenticationPrincipal CustomUserDetails userDetails) {
		feedService.deleteFeed(boardNo, userDetails);
		return ResponseEntity.ok("게시글이 성공적으로 삭제됐습니다.");
	}

}
