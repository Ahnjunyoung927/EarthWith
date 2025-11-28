package com.kh.eco.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dao.FeedMapper;
import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.vo.BoardVO;
import com.kh.eco.file.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
	
	private final FeedMapper feedMapper;
	private final FileService fileService;

	@Override
	public List<FeedBoardDTO> selectFeedList(String category, Long fetchOffset, Long limit) {
		
		if("C".equals(category)) {
			category = "C%";
		}
		
		return feedMapper.selectFeedList(category, fetchOffset, limit);
	}
	
	@Override
	public int insertFeed(FeedBoardDTO feed, MultipartFile file, String username) {
		BoardVO b = null;
	  //  String filePath = fileService.store(file);
			
			b = BoardVO.builder()//.boardNo(feed.getBoardNo())
					             .refMno(feed.getBoardAuthor())
					             .boardCategory(feed.getBoardCategory())
					             .boardTitle(feed.getBoardTitle())
					             .boardContent(feed.getBoardContent())
					             .regDate(feed.getRegDate())
					             .build();	 
			
			int result = feedMapper.insertFeed(b);
			
			if (result == 1 && file != null && !file.isEmpty()) {

		        // 3) 파일 저장 → 경로 얻기
		        String filePath = fileService.store(file);
		        
		        // 4) 방금 생성된 게시글 번호 사용
		        
		       BoardVO res = feedMapper.findNewBoardNo(b.getRefMno());
		       Long newBoardNo = res.getBoardNo(); 
		        //Integer boardNo = b.getBoardNo();
		        //log.info("보드넘버 : {}", String.valueOf(boardNo));
		        saveAttachment(newBoardNo, filePath);
		    }

		    return result;
	}
	
	public void saveAttachment(Long newBoardNo, String filePath) {
		feedMapper.saveAttachment(newBoardNo, filePath);
		
	}
	
}
