package com.kh.eco.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.vo.BoardVO;

@Mapper
public interface BoardMapper {
	

	int todayParticipants(String category);
	
	int todayPost(String category);
	
	long getBoardCountForParticipation ();


}
