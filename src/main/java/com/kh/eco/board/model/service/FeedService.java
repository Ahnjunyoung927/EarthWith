package com.kh.eco.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dto.FeedBoardDTO;

public interface FeedService {

List<FeedBoardDTO> selectFeedList(String category, Long fetchOffset, Long limit);
	
	int insertFeed(FeedBoardDTO feed, MultipartFile file, String username);
}
