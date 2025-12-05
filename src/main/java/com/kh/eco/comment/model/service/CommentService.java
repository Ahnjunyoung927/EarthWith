package com.kh.eco.comment.model.service;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;

public interface CommentService {
    int insertComment(CommentDTO comment);
    int updateComment(CommentDTO comment, CustomUserDetails user);
    
    // [핵심] int -> Long 으로 변경
    int deleteComment(Long commentNo, CustomUserDetails user);
    
    int reportComment(CommentReportDTO report, CustomUserDetails user);
}