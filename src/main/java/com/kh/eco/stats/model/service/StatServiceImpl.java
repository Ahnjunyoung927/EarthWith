package com.kh.eco.stats.model.service;

import org.springframework.stereotype.Service;

import com.kh.eco.file.FileService;
import com.kh.eco.stats.model.dao.StatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
	private final StatMapper statMapper;
	
	@Override
	public int todayParticipants(String category) {
		return statMapper.todayParticipants(category);
	}
	
	@Override
	public int todayPost(String category) {
		return statMapper.todayPost(category);
	}

	@Override
	public long getBoardCountForParticipation() {
		return 0;
		
	}
	
}
