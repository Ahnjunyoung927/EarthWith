package com.kh.eco.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;

@Mapper
public interface BoardMapper {

	long getBoardCountForParticipation ();

	List<BoardDTO> selectBoardAll();

	int selectListCount();

	List<BoardDTO> selectBoardList(Map<String, Object> paramMap);

	List<BoardDTO> selectTopBoardList();

	int increaseViewCount(int boardNo);

	BoardDetailDTO selectBoardDetail(int boardNo);

}
