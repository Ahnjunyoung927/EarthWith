package com.kh.eco.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.dao.FeedMapper;
import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.vo.BoardVO;
import com.kh.eco.exception.PageNotFoundException;
import com.kh.eco.file.FileService;
import com.kh.eco.file.MyRenamePolicy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
	
	private final FeedMapper feedMapper;
	private final FileService fileService;
	private final MyRenamePolicy myRenamePolicy;
	private final BoardReportService boardReportService;

	/**
	 * 피드 게시글 조회하기
	 */
	@Override
	public List<FeedBoardDTO> selectFeedList(String category, Long fetchOffset, Long limit) {
		
		if("C".equals(category)) {
			category = "C%";
		}
		
		return feedMapper.selectFeedList(category, fetchOffset, limit);
	}
	
	/**
	 * 피드 게시글 조회하기 (매개변수 1개용)
	 */
	@Override
	public List<FeedBoardDTO>  selectFeedList(Long boardNo) {
		
		return selectFeedList(null, null, boardNo);
	}
	
	
	
	/**
	 * 게시글 작성하기 (수정중)
	 */
	@Override
	@Transactional
	public int insertFeed(FeedBoardDTO feed, MultipartFile file) {
		BoardVO b = null;
	  //  String filePath = fileService.store(file);
			log.info("카테고리 값 : {}", feed.getBoardCategory());
			b = BoardVO.builder()//.boardNo(feed.getBoardNo())
					             .refMno(feed.getBoardAuthor())
					             .boardCategory(feed.getBoardCategory())
					             .boardTitle(feed.getBoardTitle())
					             .boardContent(feed.getBoardContent())
					             .regDate(feed.getRegDate())
					             .build();	 
			
			int result = feedMapper.insertFeed(b);
			
			if(result <= 0) {
				throw new PageNotFoundException("게시글 작성에 실패했습니다.");
			}
			
			if(file != null && !file.isEmpty()) {
				String changeName = fileService.store(file);
				String originName = file.getOriginalFilename();
				String attachmentPath = "http://localhost:8081/uploads/" + changeName;
				
				Map<String, Object> fileMap = new HashMap<>();
				fileMap.put("refBno", b.getBoardNo());
				fileMap.put("originName", originName);
				fileMap.put("changeName", changeName);
				fileMap.put("attachmentPath", attachmentPath);
				
				feedMapper.saveAttachment(fileMap);
		    }

		    return result;
	}
	
	/*
 
  
            
            boardMapper.insertAttachment(fileMap);
        }
        
        return result; 
	 
	 */
	
	/**
	 * 게시글 파일첨부 하기 (수정중)
	 */
	public void saveAttachment(FeedBoardDTO boardDTO) {
		feedMapper.saveAttachment(boardDTO);
		
	}
	
	/**
	 * 게시글 삭제하기
	 */
	public int deleteFeed(int boardNo, CustomUserDetails userDetails) {
		// 본인여부 검증용
		
		isOwner(boardNo, userDetails);
		
		// 게시글 존재 여부 검증
		boardReportService.selectBoardOne(boardNo);
		
		int result = feedMapper.deleteFeed(boardNo);
		if(result == 0) {
			throw new PageNotFoundException("게시글이 존재하지 않습니다.");
		} 
		
		return result;
	}
	
	/**
	 * 게시글 본인여부 검증 메소드
	 */
	@Override
	public int isOwner(int boardNo, CustomUserDetails userDetails) {
		int memberNo = userDetails.getMemberNo();
		int result = feedMapper.isOwner(boardNo, memberNo);
		
	    if(result == 0) {
			throw new PageNotFoundException("본인의 게시글만 삭제할 수 있습니다.");
		}
	    
		log.info("{}", result);
		
		return result;
	}
	
	
}
