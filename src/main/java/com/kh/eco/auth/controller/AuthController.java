package com.kh.eco.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.auth.model.service.AuthService;
import com.kh.eco.member.model.dto.MemberLoginDTO;
import com.kh.eco.member.model.dto.MemberLogoutDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("eco/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	// private final TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody MemberLoginDTO member){
		Map<String, String> loginResponse = authService.login(member);
		return ResponseEntity.ok(loginResponse);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Valid @RequestBody MemberLogoutDTO member){
		authService.logout(member);
		return ResponseEntity.ok(Map.of("message", "로그아웃 완료"));
	}
	
	
	
	
}
