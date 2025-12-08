package com.kh.eco.stats.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.board.model.service.BoardService;
import com.kh.eco.board.model.service.FeedService;
import com.kh.eco.member.model.service.MemberService;
import com.kh.eco.stats.model.service.StatService;

import lombok.RequiredArgsConstructor;

@RestController //@Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {
	
	private final MemberService memberService;
	private final BoardService boardService;
	private final FeedService feedService;
	private final StatService statService;
	
	@GetMapping("/member-count")
	public ResponseEntity<Map<String, Object>> getMemberCount(){
		
		//
		long memberCount = memberService.getActiveMemberCount();
		
		Map<String, Object> response = new HashMap<>();
        response.put("memberCount", memberCount);
		
		
        return ResponseEntity.ok(response);
	}
	
    @GetMapping("/member-rank-10")
    public ResponseEntity<List<Map<String, Object>>> getMemberRankList() { 
    	List<Map<String, Object>> rankList = memberService.getMemberRank(); 

    	return ResponseEntity.ok(rankList);
    }
    
    @GetMapping("/boards-join")
    public ResponseEntity<Map<String, Object>> getBoardCountForParticipation(){
    	
    	long boardParticipationCount  = boardService.getBoardCountForParticipation();
    	
		Map<String, Object> response = new HashMap<>();
        response.put("boardParticipationCount", boardParticipationCount);
    	
        return ResponseEntity.ok(response);
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
