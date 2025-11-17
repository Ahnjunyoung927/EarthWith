package com.kh.eco.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.member.model.dto.MemberLoginDTO;
import com.kh.eco.member.model.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	int signUp(MemberVO member);
	
	int countByMemberId(String memberId);
	
	MemberLoginDTO loadUser(String memberId);
	

}
