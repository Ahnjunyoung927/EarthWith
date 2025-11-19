package com.kh.eco.member.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.member.model.dto.MemberSignUpDTO;

public interface MemberService {
	
	void signUp(MemberSignUpDTO member, MultipartFile profileImg );

	long getActiveMemberCount();

	List<Map<String, Object>> getMemberRank();
	
	

}
