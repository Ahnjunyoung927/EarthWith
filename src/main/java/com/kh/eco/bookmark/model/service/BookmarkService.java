package com.kh.eco.bookmark.model.service;

import org.springframework.http.ResponseEntity;

import com.kh.eco.bookmark.model.dto.BookmarkDTO;

public interface BookmarkService {
	
	/**
	 * 즐겨찾기 등록 / 해제
	 * @param bookmarkDTO
	 * @return
	 */
	ResponseEntity<?> insertBookmark(BookmarkDTO bookmarkDTO);
}
