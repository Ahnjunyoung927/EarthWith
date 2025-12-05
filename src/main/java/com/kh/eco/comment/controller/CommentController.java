package com.kh.eco.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;
import com.kh.eco.comment.model.service.CommentService;
import lombok.RequiredArgsConstructor;

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
        
        // [수정] CommentDTO는 Long을 원함 -> (long) 형변환 필수
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
        comment.setCommentNo(commentNo); // PathVariable이 Long이므로 그대로 세팅
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
        
        // [수정] CommentReportDTO.refCno는 Long 타입임 (에러 이미지 기반)
        // 따라서 .intValue()를 제거하고 Long 그대로 전달
        report.setRefCno(commentNo); 
        
        int result = commentService.reportComment(report, user);
        return result > 0 ? ResponseEntity.ok("신고 완료") : ResponseEntity.status(500).build();
    }
}