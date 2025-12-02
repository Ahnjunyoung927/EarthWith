package com.kh.eco.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;

@Mapper
public interface BoardMapper {
    
    // 조회 관련 
    int selectListCount();
    List<BoardDTO> selectTopBoardList();
    List<BoardDTO> selectBoardList(Map<String, Object> paramMap);
    BoardDetailDTO selectBoardDetail(int boardNo);
    int increaseViewCount(int boardNo);

    // 게시글 쓰기/수정/삭제
    int insertBoard(BoardDTO board);
    int updateBoard(BoardDTO board);
    int deleteBoard(Map<String, Object> map);

    // 첨부파일 관련
    int insertAttachment(Map<String, Object> fileMap);
    int updateAttachment(Map<String, Object> fileMap);
}