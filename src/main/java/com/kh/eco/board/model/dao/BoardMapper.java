package com.kh.eco.board.model.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.dto.BoardReportDTO;
import com.kh.eco.board.model.dto.FeedBoardDTO;

@Mapper
public interface BoardMapper {
    
    // [중요] 조회 메서드들이 Map을 받도록 설정
    int selectListCount(Map<String, Object> map);
    List<BoardDTO> selectBoardList(Map<String, Object> map);
    List<BoardDTO> selectTopBoardList(Map<String, Object> map);

    BoardDetailDTO selectBoardDetail(Long boardNo);
    long getBoardCountForParticipation();
    BoardDTO selectBoardOne(int boardNo);

    int increaseViewCount(Long boardNo);
    int insertBoard(BoardDTO board);
    int updateBoard(BoardDTO board);
    int deleteBoard(Map<String, Object> map);

    int insertAttachment(Map<String, Object> fileMap);
    int updateAttachment(Map<String, Object> fileMap);
    
    // 피드 관련
    List<FeedBoardDTO> selectFeedList(Map<String, Object> params);
    int saveFeed(FeedBoardDTO feed);
    int todayParticipants(String category);
    int todayPost(String category);
    int saveAttachment(FeedBoardDTO feed);
    
    int boardReport(BoardReportDTO reportDTO);
}