package com.kh.eco.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dao.BoardMapper;
import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.board.model.vo.BoardVO;
import com.kh.eco.file.FileService;
import com.kh.eco.like.model.dao.LikeMapper;
import com.kh.eco.like.model.vo.LikeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	private final LikeMapper likeMapper;
	
	@Override
	public List<FeedBoardDTO> getFeedList(String category, Long fetchOffset, Long limit) {
		
		if("C".equals(category)) {
			category = "C%";
		}
		
		return boardMapper.selectFeedList(category, fetchOffset, limit);
	}
	
	@Override
	public int saveFeed(FeedBoardDTO feed, MultipartFile file, String username) {
		BoardVO b = null;
	  //  String filePath = fileService.store(file);
			
			b = BoardVO.builder()//.boardNo(feed.getBoardNo())
					             .refMno(feed.getBoardAuthor())
					             .boardCategory(feed.getBoardCategory())
					             .boardTitle(feed.getBoardTitle())
					             .boardContent(feed.getBoardContent())
					             .regDate(feed.getRegDate())
					             .build();	 
			
			int result = boardMapper.saveFeed(b);
			
			if (result == 1 && file != null && !file.isEmpty()) {

		        // 3) 파일 저장 → 경로 얻기
		        String filePath = fileService.store(file);
		        
		        // 4) 방금 생성된 게시글 번호 사용
		        
		       BoardVO res = boardMapper.findNewBoardNo(b.getRefMno());
		       Long newBoardNo = res.getBoardNo(); 
		        //Integer boardNo = b.getBoardNo();
		        //log.info("보드넘버 : {}", String.valueOf(boardNo));
		        saveAttachment(newBoardNo, filePath);
		    }

		    return result;
	}
	
	private void saveAttachment(Long newBoardNo, String filePath) {
		boardMapper.saveAttachment(newBoardNo, filePath);
		
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
	
	@Override
	@Transactional
	public LikeResponse toggleLike(Long boardNo, int memberNo) {
		
		boolean isLiked;
		
		int result = likeMapper.existsBoardLike(boardNo, memberNo);
		
		int boardExists = likeMapper.existsBoard(boardNo);
		if(boardExists == 0) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다. boardNo = " + boardNo);
		}
		
		int likeCountRowExists = likeMapper.existsLikeCountRow(boardNo);
		if(likeCountRowExists == 0) {
			likeMapper.insertLikeCountRow(boardNo);
		}
		
		
		// 좋아요를 이미 누른 상태 -- 좋아요 취소 & 카운트 -1
		if(result > 0) {
			likeMapper.deleteBoardLike(boardNo, memberNo);
			likeMapper.decreaseBoardLikeCount(boardNo);
			isLiked = false;
		} else { // 좋아요를 안누른 상태 -- 좋아요 추가 & 카운트 +1
			likeMapper.insertBoardLike(boardNo, memberNo);
			likeMapper.increaseBoardLikeCount(boardNo);
			isLiked = true;
		}
		
		int likeCount = likeMapper.getBoardLikeCount(boardNo);
		
		return new LikeResponse(isLiked, likeCount);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
