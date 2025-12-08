package com.kh.eco.bookmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.bookmark.model.dto.BookmarkDTO;
import com.kh.eco.bookmark.model.service.BookmarkService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"boards", "feeds"})
public class BookmarkController {
	
	private final BookmarkService bookmarkService;
	
	/**
	 * 즐겨찾기 등록 / 해제
	 * @param boardNo 게시글번호
	 * @param bookmarkDTO 회원번호 + 게시글번호
	 * @param userDetails 회원번호 꺼내올 정보
	 * @return
	 */
	@PostMapping("/{boardNo}/bookmark")
	public ResponseEntity<?> insertBookmark(@PathVariable(name = "boardNo") @Min(value = 1, message = "게시글이 존재하지 않습니다.") int boardNo,
			                          @RequestBody BookmarkDTO bookmarkDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		bookmarkDTO.setMemberNo(userDetails.getMemberNo());
		bookmarkDTO.setBoardNo(boardNo);
		
		return bookmarkService.insertBookmark(bookmarkDTO);
	
	}
	
}
