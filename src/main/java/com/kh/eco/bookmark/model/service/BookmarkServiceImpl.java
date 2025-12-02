package com.kh.eco.bookmark.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.eco.board.model.service.BoardReportService;
import com.kh.eco.bookmark.model.dao.BookmarkMapper;
import com.kh.eco.bookmark.model.dto.BookmarkDTO;
import com.kh.eco.exception.UsenameNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
	private final BookmarkMapper bookmarkMapper;
	private final BoardReportService boardReportService;
	
	/**
	 * 즐겨찾기 등록 / 해제
	 */
	@Override
	@Transactional
	public ResponseEntity<?> insertBookmark(BookmarkDTO bookmarkDTO) {
		
		// 로그인 여부 검증
		int memberNo = bookmarkDTO.getMemberNo();
		if(memberNo == 0) {
			throw new UsenameNotFoundException("로그인 후 이용할 수 있습니다.");
		}
		
		// 게시글 존재 여부 검증
		boardReportService.selectBoardOne(bookmarkDTO.getBoardNo());
		
		
		// 즐겨찾기 행이 있는지 여부
		if(bookmarkMapper.existsBookmark(bookmarkDTO) == 0) { // 없다
		   int result = bookmarkMapper.insertBookmark(bookmarkDTO);
		   if(result <= 0) { // 실패
			   throw new IllegalArgumentException("즐겨찾기 등록을 실패했습니다.");
		   }   return ResponseEntity.ok("즐겨찾기 등록을 성공했습니다.");
		   
		   
		} else { // 있다
		   int result = bookmarkMapper.cancleBookmark(bookmarkDTO); // 삭제
		   if(result <= 0) { // 실패
			   throw new IllegalArgumentException("즐겨찾기 해제를 실패했습니다.");
		   }   return ResponseEntity.ok("즐겨찾기 해제를 성공했습니다."); 
		}
	
		
	}

}
