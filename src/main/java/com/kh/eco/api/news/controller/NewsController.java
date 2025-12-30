package com.kh.eco.api.news.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.api.news.model.dto.NewsDTO;
import com.kh.eco.api.news.model.service.NewsService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("eco/api")
public class NewsController {
	
	private final NewsService newsService;
	
	@GetMapping("/news")
	public List<NewsDTO> getNews (@RequestParam(value = "query", required = false , defaultValue = "환경") String query) {
		
		return newsService.findAllNews(query);
	
	}
}
