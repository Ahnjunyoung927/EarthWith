package com.kh.eco.member.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.eco.member.model.dao.MemberMapper;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
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
		/*
		int count = memberMapper.countByMemberId(member.getMemberId());
		if(1 == count) {
			throw new IdDuplicateException("이미 존재하는 아이디입니다.");
		}
		*/
		midc.idDuplicateCheck(member); // 아이디 중복검사
		
		midc.phoneDuplicateCheck(member); // 폰 중복검사
		
		midc.emailDuplicateCheck(member); // 이메일 중복검사

		MemberVO signUpMember = MemberVO.builder().memberName(member.getMemberName()).memberId(member.getMemberId())
				                                  .memberPwd(passwordEncoder.encode(member.getMemberPwd()))
						                          .phone(member.getPhone()).email(member.getEmail())
						                          .refRno(member.getRefRno()).build();
		
		memberMapper.signUp(signUpMember);
	}
	

}
