package com.kh.eco.auth.model.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.exception.CustomAuthenticationException;
import com.kh.eco.exception.LogoutFailureException;
import com.kh.eco.member.model.dto.MemberLoginDTO;
import com.kh.eco.member.model.dto.MemberLogoutDTO;
import com.kh.eco.token.model.dao.TokenMapper;
import com.kh.eco.token.model.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final TokenMapper tokenMapper;
	
	@Override
	public Map<String, String> login(MemberLoginDTO member) {

		Authentication auth = null;
		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getMemberPwd()));
		} catch(AuthenticationException e) {
			throw new CustomAuthenticationException("비밀번호 미일치");
		}
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		// log.info("로그인 성공");
		// log.info("인증에 성공한 사용자의 정보 : {}", user);
		
		Map<String, String> loginResponse = tokenService.generateToken(user.getUsername());
		loginResponse.put("memberId", user.getUsername());
		loginResponse.put("memberName", user.getMemberName());
		loginResponse.put("role", user.getAuthorities().toString());		
		
		return loginResponse;
	}

	@Override
	public void logout(MemberLogoutDTO member) {
		
		int result = tokenMapper.deleteTokenForLogout(member);
		
		if(result == 1) {
			return;
		} else {
			throw new LogoutFailureException("로그아웃 실패, 관리자에게 문의해주세요.");
		}
		
	}

}
