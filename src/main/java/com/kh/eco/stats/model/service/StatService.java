package com.kh.eco.stats.model.service;

public interface StatService {
	/**
    * 특정 카테고리의 오늘의 참여자 수를 조회합니다.
    *
    * @param category 카테고리 코드
    * @return 오늘의 참여자 수
    */
	int todayParticipants(String category);
	
	
	/**
     * 특정 카테고리의 오늘의 게시글 작성 수를 조회합니다.
     *
     * @param category 카테고리 코드
     * @return 오늘의 게시글 수
     */
	int todayPost(String category);

	
	
}
