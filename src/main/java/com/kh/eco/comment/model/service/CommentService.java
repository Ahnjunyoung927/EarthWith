package com.kh.eco.comment.model.service;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;

public interface CommentService {
    int insertComment(CommentDTO comment);
    int updateComment(CommentDTO comment, CustomUserDetails user);
    int deleteComment(Long commentNo, CustomUserDetails user); // Long 확인
    int reportComment(CommentReportDTO report, CustomUserDetails user);
}