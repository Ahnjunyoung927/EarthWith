package com.kh.eco.comment.model.dao;

import org.apache.ibatis.annotations.Mapper;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;

@Mapper
public interface CommentMapper {
    int insertComment(CommentDTO comment);
    int updateComment(CommentDTO comment);
    
    // ID는 Long으로 받음
    int deleteComment(Long commentNo);
    CommentDTO selectComment(Long commentNo);

    int insertCommentReport(CommentReportDTO report);
}