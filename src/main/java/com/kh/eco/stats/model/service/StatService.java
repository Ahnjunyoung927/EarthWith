package com.kh.eco.stats.model.service;

public interface StatService {

	int todayParticipants(String category);
	
	int todayPost(String category);

	long getBoardCountForParticipation();
}
