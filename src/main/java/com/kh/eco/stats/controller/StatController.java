package com.kh.eco.stats.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.member.model.service.MemberService;
import com.kh.eco.stats.model.service.StatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {
	
	private final StatService statService;
	private final MemberService memberservice;

	@GetMapping("/landing")
	public ResponseEntity<Map<String, Object>> getLandingStats() {
		
		Map<String, Object> stats = statService.getLandingStats();
	
		return ResponseEntity.ok(stats);

	}
	
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        
        Map<String, Object> stats = statService.getDashboardStats();
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/mainpage")
    public ResponseEntity<Map<String, Object>> getMethodName() {
        Map<String, Object> main = statService.getMainpage();
        return ResponseEntity.ok(main);
    }
    
    @GetMapping("/ranking")
    public ResponseEntity<List<Map<String, Object>>> getMemberRankList() { 
    	List<Map<String, Object>> rankList = memberservice.getMemberRank(); 

    	return ResponseEntity.ok(rankList);
    }
    
    // 오늘의 참여태그 수
    @GetMapping("/today")
	public ResponseEntity<Map<String, Object>> todayParticipants(@RequestParam(name="category") String category) {
		
		int count = statService.todayParticipants(category);
		
		Map<String, Object> result = new HashMap<>();
		result.put("category", category);
		result.put("todayParticipants", count);
		
		
		return ResponseEntity.ok(result);
	}
	
    // 오늘의 새 글
	@GetMapping("/todayPost")
	public ResponseEntity<Map<String, Object>> todayPost(@RequestParam(name="category") String category) {
		
		int count = statService.todayPost(category);
		
		Map<String, Object> result = new HashMap<>();
		result.put("category", category);
		result.put("todayPost", count);
		
		return ResponseEntity.ok(result);
	}
    
}