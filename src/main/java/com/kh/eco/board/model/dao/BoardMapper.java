package com.kh.eco.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.vo.BoardVO;

@Mapper
public interface BoardMapper {
	
	List<FeedBoardDTO> selectFeedList(@Param("category") String category,
			                     @Param("fetchOffset") Long fetchOffset,
			                     @Param("limit") int limit);
	
	void saveFeed(BoardVO feed);
	
	int todayParticipants(@Param("category") String category);
	
	int todayPost(@Param("category") String category);
	
	long getBoardCountForParticipation ();


}
