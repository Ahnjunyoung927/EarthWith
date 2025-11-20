package com.kh.eco.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.member.model.dto.ChangePasswordDTO;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.dto.UpdateEmailDTO;
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
	public ResponseEntity<?> signUp(@Valid @RequestBody MemberSignUpDTO member){
		log.info("멤버 잘들어오는지 확인 : {}", member);
		memberService.signUp(member);
		
		return ResponseEntity.status(201).build();
	}
	
	// 비밀번호 변경 메소드
	/**
	 * 메소드명 - 기능설명 
	 * @param password
	 * @return
	 */
	@PutMapping("password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO password) {
		log.info("비밀번호 정보 : {}", password);
		
		memberService.changePassword(password);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	// 지역, 이메일, 프로필, 번호
	
	@PutMapping("email")
	public ResponseEntity<?> changeEmail(@Valid @RequestBody UpdateEmailDTO email) {
	
		log.info("이메일 정보 : {}", email);
		memberService.updateMemberEmail(email);
		return ResponseEntity.ok("이메일 변경 완료");
	}
	
//	@PutMapping("email")
//	public ResponseEntity<?> changeEmail(
//			@Valid Long memberId,
//			@RequestBody String newEmail) {
//	
//		log.info("이메일 정보 : {}", newEmail);
//		memberService.updateMemberEmail(memberId, newEmail);
//		return ResponseEntity.ok("이메일 변경 완료");
//	}
}
