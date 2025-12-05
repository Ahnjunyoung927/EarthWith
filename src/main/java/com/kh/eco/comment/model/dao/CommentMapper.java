package com.kh.eco.comment.model.dao;

import org.apache.ibatis.annotations.Mapper;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;

@Mapper
public interface CommentMapper {
    int insertComment(CommentDTO comment);
    int updateComment(CommentDTO comment);
    
    // [핵심] int -> Long 으로 변경
    int deleteComment(Long commentNo);
    
    // [핵심] int -> Long 으로 변경
    CommentDTO selectComment(Long commentNo);

    int insertCommentReport(CommentReportDTO report);
}