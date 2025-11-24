package com.kh.eco.board.model.service;

import java.util.List;
import java.util.Map;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;

public interface BoardService {
    
    long getBoardCountForParticipation();
    
    List<BoardDTO> selectBoardAll();
    
    Map<String, Object> selectBoardList(int currentPage);
   
    BoardDetailDTO selectBoardDetail(int boardNo);
}