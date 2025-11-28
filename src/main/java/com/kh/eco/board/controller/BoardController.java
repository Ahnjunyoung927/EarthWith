package com.kh.eco.board.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

	private final BoardService boardService;

    /**
     * selectBoardList
     * 보드 전체조회 메소드
     * @param currentPage
     * @return
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> selectBoardList(
            @RequestParam(value = "page", defaultValue = "1") int currentPage) {
        
        Map<String, Object> responseData = boardService.selectBoardList(currentPage);
        return ResponseEntity.ok(responseData);
    }
    
    /**
     * selectBoardDetail
     * 보드 상세조회 메소드
     * @param boardNo
     * @return
     */
    @GetMapping("/{boardNo}")
    public ResponseEntity<?> selectBoardDetail(@PathVariable("boardNo") int boardNo) {
        
        BoardDetailDTO board = boardService.selectBoardDetail(boardNo);
        
        if(board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }				
    }
    
    @PostMapping
    public ResponseEntity<?> insertBoard(@RequestPart("board") @Valid BoardDTO board,
    								     @RequestPart(value="file", required = false) MultipartFile file,
    								     @AuthenticationPrincipal CustomUserDetails user ){
    	//로그인 유저 아이디 추출
    	String userId = user.getUsername();
    	
    	//서비스로 가랏
    	int result = boardService.insertBoard(board, file, userId );
    	
    	
    	return null;
    	
    }
}
