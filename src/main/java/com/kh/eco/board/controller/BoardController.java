package com.kh.eco.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectBoardList(
            @RequestParam(value = "page", defaultValue = "1") Integer currentPage, // int -> Integer로 변경 (안정성 확보)
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
  
        log.info("목록 조회 요청 - page: {}, type: {}, category: {}", currentPage, type, category);

        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", currentPage);
        
        if (type != null) map.put("type", type);
        if (category != null && !"ALL".equals(category)) map.put("category", category);
        if (keyword != null) map.put("keyword", keyword);

        Map<String, Object> responseData = boardService.selectBoardList(map);
        return ResponseEntity.ok(responseData);
    }
    
    // 게시글 상세 조회
    @GetMapping("/{boardNo}")
    public ResponseEntity<?> selectBoardDetail(@PathVariable("boardNo") Long boardNo) {
        BoardDetailDTO board = boardService.selectBoardDetail(boardNo);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
    
    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> insertBoard(
            @RequestPart("board") @Valid BoardDTO board,
            @RequestPart(value="file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        int result = boardService.insertBoard(board, file, user.getUsername());
        return result > 0 ? ResponseEntity.ok("성공") : ResponseEntity.status(500).body("실패");
    }

    // 게시글 수정
    @PutMapping("/{boardNo}")
    public ResponseEntity<?> updateBoard(
            @PathVariable("boardNo") Long boardNo,
            @RequestPart("board") @Valid BoardDTO board,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        board.setBoardNo(boardNo);
        int result = boardService.updateBoard(board, file, user.getUsername());
        return result > 0 ? ResponseEntity.ok("성공") : ResponseEntity.status(500).body("실패");
    }
    
    // 게시글 삭제
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<?> deleteBoard(
            @PathVariable("boardNo") Long boardNo, 
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        int result = boardService.deleteBoard(boardNo, user.getUsername());
        return result > 0 ? ResponseEntity.ok("성공") : ResponseEntity.status(500).body("실패");
    }
}