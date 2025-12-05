package com.kh.eco.comment.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dao.CommentMapper;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public int insertComment(CommentDTO comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    @Transactional
    public int updateComment(CommentDTO comment, CustomUserDetails user) {
        CommentDTO original = commentMapper.selectComment(comment.getCommentNo());
        if(original == null) return 0;
        
        int userNo = Integer.parseInt(user.getMemberNo());
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (original.getRefMno() == userNo || isAdmin) {
            return commentMapper.updateComment(comment);
        } else {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public int deleteComment(int commentNo, CustomUserDetails user) {
        CommentDTO original = commentMapper.selectComment(commentNo);
        if(original == null) return 0;

        int userNo = Integer.parseInt(user.getMemberNo());
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (original.getRefMno() == userNo || isAdmin) {
            return commentMapper.deleteComment(commentNo);
        } else {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public int reportComment(CommentReportDTO report, CustomUserDetails user) {
        int userNo = Integer.parseInt(user.getMemberNo());
        report.setRefMno(userNo);
        return commentMapper.insertCommentReport(report);
    }
}