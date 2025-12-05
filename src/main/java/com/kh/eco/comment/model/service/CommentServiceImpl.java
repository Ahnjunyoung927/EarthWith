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
        // [수정] Mapper는 int를 원할 수도 있음. 안전하게 .intValue() 사용 고려
        // 하지만 Mapper 인터페이스를 Long으로 바꿨다면 그대로 둠.
        CommentDTO original = commentMapper.selectComment(comment.getCommentNo());
        if(original == null) return 0;
        
        int userNo = user.getMemberNo();
        long writerNo = original.getRefMno(); // CommentDTO는 Long
        
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

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
        // [핵심 수정] 에러 로그(image_e97360)상 refMno는 int를 원함
        // (long)을 제거하고 int 그대로 대입
        report.setRefMno(user.getMemberNo());
        
        return commentMapper.insertCommentReport(report);
    }
}