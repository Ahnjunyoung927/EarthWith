package com.kh.eco.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dao.BoardMapper;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.vo.BoardVO;
import com.kh.eco.file.FileService;

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
		
		
		log.info("saveFeed 호출 - title={}, category={}, author={}, username={}",
                feed.getBoardTitle(), feed.getBoardCategory(),
                feed.getBoardAuthor(), username);
		
		
		
		
		
		BoardVO.BoardVOBuilder builder = BoardVO.builder()   
		          		     .boardTitle(feed.getBoardTitle())
				 			 .boardContent(feed.getBoardContent())
				 			 .boardCategory(feed.getBoardCategory())
				 			 .boardAuthor(feed.getBoardAuthor())
				 			 .memberId(username);
				 			 
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			builder.attachmentPath(filePath);
	
			
		}
		
		BoardVO board = builder.build();
		boardMapper.saveFeed(board);
	}

	
	@Override
	public int todayParticipants(String category) {
		return boardMapper.todayParticipants(category);
	}
	
	@Override
	public int todayPost(String category) {
		return boardMapper.todayPost(category);
	}
}
