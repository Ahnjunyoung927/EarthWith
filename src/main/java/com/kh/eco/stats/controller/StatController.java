package com.kh.eco.stats.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.eco.stats.model.service.StatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatController {
	
	private final StatService statService;

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