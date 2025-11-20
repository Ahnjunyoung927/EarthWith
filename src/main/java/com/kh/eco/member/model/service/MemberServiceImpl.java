package com.kh.eco.member.model.service;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.exception.CustomAuthenticationException;
import com.kh.eco.exception.IdDuplicateException;
import com.kh.eco.member.model.dao.MemberMapper;
import com.kh.eco.member.model.dto.ChangePasswordDTO;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.dto.UpdateEmailDTO;
import com.kh.eco.member.model.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MemberInfoDuplicateCheck midc;

	@Override
	public void signUp(MemberSignUpDTO member) {
		
		// 아이디 중복 검사
		int count = memberMapper.countByMemberId(member.getMemberId());
		if(1 == count) {
			throw new IdDuplicateException("이미 존재하는 아이디입니다.");
		}
		
		midc.idDuplicateCheck(member); // 아이디 중복검사
		
		midc.phoneDuplicateCheck(member); // 폰 중복검사
		
		midc.emailDuplicateCheck(member); // 이메일 중복검사
		
		MemberVO signUpMember = MemberVO.builder().memberName(member.getMemberName()).memberId(member.getMemberId())
				                                  .memberPwd(passwordEncoder.encode(member.getMemberPwd()))
						                          .phone(member.getPhone()).email(member.getEmail())
						                          .refRno(member.getRefRno()).build();
		
		memberMapper.signUp(signUpMember);
		log.info("사용자등록 성공 : {}", signUpMember);

	}
	
	@Override
	public void changePassword(ChangePasswordDTO password) {
		CustomUserDetails user = validatePassword(password.getCurrentPassword());
		
		String newPassword = passwordEncoder.encode(password.getNewPassword()); // 
		
		Map<String, String> changeRequest = Map.of("memberId", user.getUsername(),
												   "newPassword", newPassword);
		
		memberMapper.changePassword(changeRequest);
	}
	
	private CustomUserDetails validatePassword(String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		// 검증이 맞다면
		log.info("pw : {}", password);
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomAuthenticationException("비밀번호가 일치하지 않습니다");
		}
		
		return user;
		
	}
	
	@Override
	public void updateMemberEmail(UpdateEmailDTO email) {
		CustomUserDetails user = validateEmail(email.getCurrentEmail());
		log.info("serviceImpl email : {}" , email);
		String newEmail = email.getNewEmail();
		
		Map<String, String> changeRequest = Map.of("memberId", user.getUsername(),
												   "newEmail", newEmail);
		
		// 4) 이메일 업데이트
		memberMapper.updateEmail(changeRequest);
	}
	
	private CustomUserDetails validateEmail(String string) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		// log.info("email : {}", email);
		// 1) 이메일 유효성 체크(비어있거나 형식 이상)
		if(string == null || string.trim().isEmpty()) {
			throw new IllegalArgumentException("이메일은 비어 있을 수 없습니다");
		}
		
		// 정규식
		if(!string.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
		}
		
		// 3) 이미 사용중인 이메일인지 체크
		MemberVO duplicated = memberMapper.findByEmail(string);
		if(duplicated != null && !duplicated.getEmail().equals(string)) {
			throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
		}
		
		return user;
		
	}
	
//	@Override
//	public void updateMemberEmail(Long memberId, String newEmail) {
//		
//		// 1) 이메일 유효성 체크(비어있거나 형식 이상)
//		if(newEmail == null || newEmail.trim().isEmpty()) {
//			throw new IllegalArgumentException("이메일은 비어 있을 수 없습니다");
//		}
//		
//		// 정규식
//		if(!newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//			throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
//		}
//		
//		// 2) 회원 존재 여부 확인
//		MemberVO member = memberMapper.findById(memberId);
//		if(member == null) {
//			throw new IllegalArgumentException("해당 회원을 찾을 수 없습니다. id=" + memberId);
//		}
//		
//		// 3) 이미 사용중인 이메일인지 체크
//		MemberVO duplicated = memberMapper.findByEmail(newEmail);
//		if(duplicated != null && !duplicated.getMemberId().equals(memberId)) {
//			throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
//		}
//		
//		// 4) 이메일 업데이트
//		memberMapper.updateEmail(memberId, newEmail);
//	}
}
