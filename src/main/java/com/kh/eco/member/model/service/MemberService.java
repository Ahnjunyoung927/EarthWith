package com.kh.eco.member.model.service;


import com.kh.eco.member.model.dto.ChangePasswordDTO;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.dto.UpdateEmailDTO;

import jakarta.validation.Valid;

public interface MemberService {
	

	void signUp(MemberSignUpDTO member, MultipartFile profileImg );
	
	void changePassword(ChangePasswordDTO password);

	void updateMemberEmail(UpdateEmailDTO email);
	
	long getActiveMemberCount();

	List<Map<String, Object>> getMemberRank();
	
	


	
}
