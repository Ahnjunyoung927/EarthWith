package com.kh.eco.member.model.service;

import org.springframework.stereotype.Service;

import com.kh.eco.exception.MemberInfoDuplicatedException;
import com.kh.eco.member.model.dao.MemberMapper;
import com.kh.eco.member.model.dto.MemberSignUpDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberInfoDuplicateCheck {
	
	private final MemberMapper memberMapper;
	
	public void idDuplicateCheck(MemberSignUpDTO member) {
		int count = memberMapper.countByMemberId(member.getMemberId());
		if(1 <= count) {
			throw new MemberInfoDuplicatedException("이미 존재하는 아이디입니다.");
		} 
	}
	
	public void phoneDuplicateCheck(MemberSignUpDTO member) {
		int count = memberMapper.countByPhone(member.getPhone());
		if(1 <= count) {
			throw new MemberInfoDuplicatedException("사용 불가능한 휴대전화 번호입니다.");
		} 
	}
	
	public void emailDuplicateCheck(MemberSignUpDTO member) {
		int count = memberMapper.countByEmail(member.getEmail());
		if(1 <= count) {
			throw new MemberInfoDuplicatedException("사용 불가능한 이메일입니다.");
		} 
	}
	
	
	

}