package com.kh.eco.stats.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController //@Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {
	
	private final MemberService memberService;
	
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
		
}
