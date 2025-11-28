package com.kh.eco.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.vo.BoardVO;

@Mapper
public interface FeedMapper {

	List<FeedBoardDTO> selectFeedList(@Param("category") String category,
            @Param("fetchOffset") Long fetchOffset,
            @Param("limit") Long limit);

	int insertFeed(BoardVO feed);

	void saveAttachment(@Param("boardNo") Long boardNo, @Param("file") String filePath);
	
	BoardVO findNewBoardNo(int memberNo);
	
}
