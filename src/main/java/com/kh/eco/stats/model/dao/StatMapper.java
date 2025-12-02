package com.kh.eco.stats.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatMapper {

	int todayParticipants(String category);
	
	int todayPost(String category);
	
	long getBoardCountForParticipation ();
}
