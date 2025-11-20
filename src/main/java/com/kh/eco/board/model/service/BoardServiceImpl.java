package com.kh.eco.board.model.service;

import org.springframework.stereotype.Service;

import com.kh.eco.board.model.dao.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;

	@Override
	public long getBoardCountForParticipation() {
		
		return boardMapper.getBoardCountForParticipation();
	}

}
