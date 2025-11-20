package com.kh.eco.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/feed")
	public ResponseEntity<List<BoardDTO>> getFeedList(@RequestParam(name = "category", defaultValue = "C") String category,
			                                      @RequestParam(name = "fetchOffset", required = false) Long fetchOffset,
			                                      @RequestParam(name = "limit", defaultValue = "10") int limit) {
		
		log.info("GET /api/boards/feed 요청 - category={}, fetchOffset={}, limit={}",
                category, fetchOffset, limit);
		
		return ResponseEntity.ok(boardService.getFeedList(category, fetchOffset, limit));
		
	}
	
	@PostMapping("/feed")
	public ResponseEntity<?> saveFeed(@Valid BoardDTO feed, @RequestParam(name="file", required=false) MultipartFile file, 
			                          @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		boardService.saveFeed(feed, file, userDetails.getUsername());
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/stats/today")
	public ResponseEntity<?> todayParticipants(@RequestParam(name="category") String category) {
		
		int count = boardService.todayParticipants(category);
		
		Map<String, Object> body = new HashMap<>();
		body.put("category", category);
		body.put("todayParticipants", count);
		
		return ResponseEntity.ok(body);
	}
	
	@GetMapping("/stats/todayPost")
	public ResponseEntity<?> todayPost(@RequestParam(name="category") String category) {
		
		int count = boardService.todayPost(category);
		
		Map<String, Object> body = new HashMap<>();
		body.put("category", category);
		body.put("todayPost", count);
		
		return ResponseEntity.ok(body);
	}
			
			
			
}

