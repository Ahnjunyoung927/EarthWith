package com.kh.eco.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dao.BoardMapper;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.vo.BoardVO;
import com.kh.eco.file.FileService;

import org.springframework.stereotype.Service;
import com.kh.eco.board.model.dao.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	
	@Override
	public List<BoardDTO> getFeedList(String category, Long fetchOffset, int limit) {
		
		if("C".equals(category)) {
			category = "C%";
		}
		
		return boardMapper.selectFeedList(category, fetchOffset, limit);
	}
	
	@Override
	public void saveFeed(BoardDTO feed, MultipartFile file, String username) {
		

	}

	
	@Override
	public int todayParticipants(String category) {
		return boardMapper.todayParticipants(category);
	}
	
	@Override
	public int todayPost(String category) {
		return boardMapper.todayPost(category);
	}

	@Override
	public long getBoardCountForParticipation() {
		return 0;
	}
}
