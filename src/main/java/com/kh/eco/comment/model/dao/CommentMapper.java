package com.kh.eco.comment.model.dao;

import org.apache.ibatis.annotations.Mapper;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;

@Mapper
public interface CommentMapper {
    // 댓글 작성, 수정, 삭제, 조회
    int insertComment(CommentDTO comment);
    int updateComment(CommentDTO comment);
    int deleteComment(int commentNo);
    CommentDTO selectComment(int commentNo);

    // 댓글 신고
    int insertCommentReport(CommentReportDTO report);
}