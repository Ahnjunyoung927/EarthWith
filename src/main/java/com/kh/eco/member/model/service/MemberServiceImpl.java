package com.kh.eco.member.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.eco.exception.IdDuplicateException;
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

	@Override
	public void signUp(MemberSignUpDTO member) {
		
		// 아이디 중복 검사
		int count = memberMapper.countByMemberId(member.getMemberId());
		if(1 == count) {
			throw new IdDuplicateException("이미 존재하는 아이디입니다.");
		}
		
		MemberVO signUpMember = MemberVO.builder().memberName(member.getMemberName()).memberId(member.getMemberId())
				                                  .memberPwd(passwordEncoder.encode(member.getMemberPwd()))
						                          .phone(member.getPhone()).email(member.getEmail())
						                          .regionNo(member.getRegionNo()).build();
		
		memberMapper.signUp(signUpMember);
		log.info("사용자등록 성공 : {}", signUpMember);

	}
	

}
