package com.kh.eco.member.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.eco.member.model.dto.MemberLoginDTO;
import com.kh.eco.member.model.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	int signUp(MemberVO member);
	
	int countByMemberId(String memberId);
	int countByPhone(String phone);
	int countByEmail(String email);
	
	MemberLoginDTO loadUser(String memberId);
	void changePassword(Map<String, String> changeRequest);

	MemberVO findById(Long memberId);

	MemberVO findByEmail(String newEmail);

	void updateEmail(Map<String, String> changeRequest);
	
	//void updateEmail(Long memberId, String newEmail);
		
}
