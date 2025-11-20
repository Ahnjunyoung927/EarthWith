package com.kh.eco.member.model.service;

import com.kh.eco.member.model.dto.ChangePasswordDTO;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.dto.UpdateEmailDTO;

import jakarta.validation.Valid;

public interface MemberService {
	
	void signUp(MemberSignUpDTO member);
	
	void changePassword(ChangePasswordDTO password);

	void updateMemberEmail(UpdateEmailDTO email);
	
	//void updateMemberEmail(Long memberId, String newEmail);
	

}
