package com.kh.eco.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;
import com.kh.eco.comment.model.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 1. 댓글 작성
    @PostMapping("/board/{boardNo}")
    public ResponseEntity<?> insertComment(@PathVariable("boardNo") Long boardNo,
                                           @RequestBody CommentDTO comment,
                                           @AuthenticationPrincipal CustomUserDetails user) {
    	log.info("가나다라마바사아자카{}", boardNo);
        
        // [수정] CommentDTO는 Long을 원하므로 (long) 형변환
        comment.setRefBno(boardNo); 
        comment.setRefMno((long) user.getMemberNo()); 
        
        int result = commentService.insertComment(comment);
        return result > 0 ? ResponseEntity.ok("댓글 등록 성공") : ResponseEntity.status(500).build();
    }

    // 2. 댓글 수정
    @PutMapping("/{commentNo}")
    public ResponseEntity<?> updateComment(@PathVariable("commentNo") Long commentNo,
                                           @RequestBody CommentDTO comment,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        comment.setCommentNo(commentNo); 
        try {
            commentService.updateComment(comment, user);
            return ResponseEntity.ok("댓글 수정 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // 3. 댓글 삭제
    @DeleteMapping("/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") Long commentNo,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        try {
            commentService.deleteComment(commentNo, user);
            return ResponseEntity.ok("댓글 삭제 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // 4. 댓글 신고
    @PostMapping("/{commentNo}/reports")
    public ResponseEntity<?> reportComment(@PathVariable("commentNo") Long commentNo,
                                           @RequestBody CommentReportDTO report,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        
        // [수정] CommentReportDTO.refCno가 Long이면 그대로, int면 .intValue()
        // 에러 로그(image_de94a0)상 Long을 원하므로 그대로 둡니다.
        report.setRefCno(commentNo);
        
        int result = commentService.reportComment(report, user);
        return result > 0 ? ResponseEntity.ok("신고 완료") : ResponseEntity.status(500).build();
    }
}