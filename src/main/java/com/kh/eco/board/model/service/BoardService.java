package com.kh.eco.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dto.FeedBoardDTO;
import com.kh.eco.like.model.vo.LikeResponse;

public interface BoardService {
	 /**
     * 피드 게시글 목록 조회 (무한 스크롤)
     *
     * @param category    게시판 카테고리 ("C" = 피드)
     * @param fetchOffset 마지막으로 조회한 게시글 번호 (이 번호보다 작은 글만 조회), 처음이면 null
     * @param limit       한 번에 가져올 개수
     */
	List<FeedBoardDTO> getFeedList(String category, Long fetchOffset, Long limit);
	
	int saveFeed(FeedBoardDTO feed, MultipartFile file, String username);
	
	int todayParticipants(String category);
	
	int todayPost(String category);

	long getBoardCountForParticipation();
	
	LikeResponse toggleLike(Long boardNo, int memberNo);


}
