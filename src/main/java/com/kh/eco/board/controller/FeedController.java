package com.kh.eco.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PostMapping
	public ResponseEntity<?> insertFeed(@Valid FeedBoardDTO feed, @RequestParam(name="file", required=false) MultipartFile file, 
			                          @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		if(userDetails != null) {
		
		int memberNo = userDetails.getMemberNo();
		
		if(memberNo >= 1) {
			
			feed.setBoardAuthor(memberNo);
			feedService.insertFeed(feed, file, userDetails.getUsername());
			
		} else {
			throw new UsenameNotFoundException("잘못된 접근입니다.");
		}
		
		
		} else {
			throw new UsenameNotFoundException("로그인 후 이용이 가능합니다.");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
