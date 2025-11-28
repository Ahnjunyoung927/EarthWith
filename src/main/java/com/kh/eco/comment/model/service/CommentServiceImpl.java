package com.kh.eco.comment.model.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.service.BoardService;
import com.kh.eco.board.model.service.FeedService;
import com.kh.eco.comment.model.dao.CommentMapper;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.vo.CommentVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final BoardService boardService;
	private final FeedService feedService;
	private final CommentMapper commentMapper;
	
	@Override
	public CommentVO insertComment(CommentDTO comment, CustomUserDetails userDetails) { 
		
		feedService.selectFeedList(null, null, comment.getRefBno());
		String memberId = userDetails.getUsername();
		
		CommentVO c = CommentVO.builder()
				               .refMno(comment.getRefMno())
				               .refBno(comment.getRefBno())
				               .commentContent(comment.getCommentContent())
				               .build();
		
		commentMapper.insertComment(c);
		return c;
		
	}
	
	@Override
	public List<CommentDTO> findAll(Long boardNo) {
		
		feedService.selectFeedList(null, null, boardNo);
		
		List<CommentDTO> resultSet =  commentMapper.findAll(boardNo);
		log.info("결과들: ", resultSet);
		return resultSet;
	}
}
