package com.kh.eco.board.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> selectBoardList(
            @RequestParam(value = "page", defaultValue = "1") int currentPage) {
        
        Map<String, Object> responseData = boardService.selectBoardList(currentPage);
        return ResponseEntity.ok(responseData);
    }
    
    @GetMapping("/{boardNo}")
    public ResponseEntity<?> selectBoardDetail(@PathVariable("boardNo") int boardNo) {
        
        BoardDetailDTO board = boardService.selectBoardDetail(boardNo);
        
        if(board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }
    }
}