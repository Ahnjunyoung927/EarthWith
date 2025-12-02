package com.kh.eco.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dao.BoardMapper;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;

import com.kh.eco.common.PageInfo;
import com.kh.eco.file.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService; 

	//일반게시판 
	@Override
	public long getBoardCountForParticipation() {
		return boardMapper.getBoardCountForParticipation();
	}
	
	@Override
	public List<BoardDTO> selectBoardAll() {
		return boardMapper.selectBoardAll();
	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		int listCount = boardMapper.selectListCount();
		
		int pageLimit = 10;
		int boardLimit = 10;
		
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		int offset = (currentPage - 1) * boardLimit;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		int startRow = (currentPage - 1) * boardLimit + 1;
		int endRow = startRow + boardLimit - 1;

		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage, offset); 
		
		List<BoardDTO> topPosts = boardMapper.selectTopBoardList();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);
		
		List<BoardDTO> listPosts = boardMapper.selectBoardList(paramMap);

		Map<String, Object> map = new HashMap<>();
		map.put("topPosts", topPosts);
		map.put("list", listPosts);
		map.put("pi", pi);
		
		return map;
	}

    @Transactional
    @Override
    public BoardDetailDTO selectBoardDetail(int boardNo) {
        
        int result = boardMapper.increaseViewCount(boardNo);
       
        if(result > 0) {
            return boardMapper.selectBoardDetail(boardNo);
        } else {
            return null;
        }
    }
    

	@Override
	public int insertBoard(@Valid BoardDTO board, MultipartFile file, String userId) {
		return boardMapper.insertBoard(board, file, userId);
	}
	
	
}

