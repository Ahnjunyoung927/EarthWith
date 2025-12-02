package com.kh.eco.like.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.like.model.service.LikeService;
import com.kh.eco.like.model.vo.LikeResponse;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentLikeController {
	
	private final LikeService likeService;
	
	@PostMapping("/{commentNo}/like")
	public LikeResponse commentLike(@PathVariable(name = "commentNo") @Min(value = 1, message = "잘못된 접근입니다.") Long commentNo, @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		int memberNo = userDetails.getMemberNo();
		
		return likeService.commentLike(commentNo, memberNo);
	}
}
