package com.kh.eco.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
    @PostMapping
    public ResponseEntity<?> signUp(  		
            @Valid MemberSignUpDTO member, 
            @RequestParam(name = "profileImg", required = false) MultipartFile profileImg) {
        
        log.info("회원가입 요청 - 회원정보: {}", member);
        
        if (profileImg != null && !profileImg.isEmpty()) {
            log.info("프로필 이미지: {} ({}bytes)", 
                    profileImg.getOriginalFilename(), 
                    profileImg.getSize());
        } else {
            log.info("프로필 이미지: 없음");
        }
        
        memberService.signUp(member, profileImg);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

	

}
