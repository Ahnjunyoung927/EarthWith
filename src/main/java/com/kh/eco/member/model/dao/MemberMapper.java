package com.kh.eco.member.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.member.model.dto.MemberLoginDTO;
import com.kh.eco.member.model.dto.UpdateProfileDTO;
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
	
	long getActiveMemberCount();
	
	List<Map<String, Object>> getMemberRank();

	void updatePhone(Map<String, String> changeRequest);

	MemberVO findByPhone(String phone);

	void updateProfile(UpdateProfileDTO profile);

	void updateRegion(Map<String, Object> changeRequest);
	

		
}
