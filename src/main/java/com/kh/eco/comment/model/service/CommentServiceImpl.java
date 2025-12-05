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
        // comment.getCommentNo()는 Long
        CommentDTO original = commentMapper.selectComment(comment.getCommentNo());
        if(original == null) return 0;
        
        // user.getMemberNo()는 int
        int userNo = user.getMemberNo();
        
        // original.getRefMno()는 Long (CommentDTO 정의)
        long writerNo = original.getRefMno();
        
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // int와 long 비교 (Java가 자동으로 처리)
        if (writerNo == userNo || isAdmin) {
            return commentMapper.updateComment(comment);
        } else {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public int deleteComment(Long commentNo, CustomUserDetails user) {
        CommentDTO original = commentMapper.selectComment(commentNo);
        if(original == null) return 0;

        int userNo = user.getMemberNo();
        long writerNo = original.getRefMno();

        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (writerNo == userNo || isAdmin) {
            return commentMapper.deleteComment(commentNo);
        } else {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public int reportComment(CommentReportDTO report, CustomUserDetails user) {
        // [수정] CommentReportDTO.refMno는 int 타입임 (에러 이미지 기반)
        // 따라서 (long) 형변환을 제거하고 int 그대로 전달
        report.setRefMno(user.getMemberNo());
        
        return commentMapper.insertCommentReport(report);
    }
}