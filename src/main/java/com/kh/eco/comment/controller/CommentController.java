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
@RequestMapping("/comments") // 기본 URL 변경
public class CommentController {

    private final CommentService commentService;

    // 1. 댓글 작성 (URL: POST /comments/board/{boardNo})
    @PostMapping("/board/{boardNo}")
    public ResponseEntity<?> insertComment(@PathVariable("boardNo") int boardNo,
                                           @RequestBody CommentDTO comment,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        comment.setRefBno(boardNo);
        comment.setRefMno(Integer.parseInt(user.getMemberNo()));
        int result = commentService.insertComment(comment);
        return result > 0 ? ResponseEntity.ok("댓글 등록 성공") : ResponseEntity.status(500).build();
    }

    // 2. 댓글 수정 (URL: PUT /comments/{commentNo})
    @PutMapping("/{commentNo}")
    public ResponseEntity<?> updateComment(@PathVariable("commentNo") int commentNo,
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

    // 3. 댓글 삭제 (URL: DELETE /comments/{commentNo})
    @DeleteMapping("/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") int commentNo,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        try {
            commentService.deleteComment(commentNo, user);
            return ResponseEntity.ok("댓글 삭제 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // 4. 댓글 신고 (URL: POST /comments/{commentNo}/reports)
    @PostMapping("/{commentNo}/reports")
    public ResponseEntity<?> reportComment(@PathVariable("commentNo") int commentNo,
                                           @RequestBody CommentReportDTO report,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        report.setRefCno(commentNo);
        int result = commentService.reportComment(report, user);
        return result > 0 ? ResponseEntity.ok("신고 완료") : ResponseEntity.status(500).build();
    }
}