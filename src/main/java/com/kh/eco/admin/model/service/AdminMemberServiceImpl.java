package com.kh.eco.admin.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.eco.admin.model.dao.AdminMemberMapper;
import com.kh.eco.admin.model.dto.AdminMemberDTO;
import com.kh.eco.admin.model.dto.UpdateEmailByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateIdByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateNameByAdminDTO;
import com.kh.eco.admin.model.dto.UpdatePasswordByAdminDTO;
import com.kh.eco.admin.model.dto.UpdatePhoneByAdminDTO;
import com.kh.eco.admin.model.dto.UpdatePointByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateRegionByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateRoleByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateStatusByAdminDTO;
import com.kh.eco.exception.FindFailureException;
import com.kh.eco.exception.MemberInfoDuplicatedException;
import com.kh.eco.exception.SQLResponseException;
import com.kh.eco.member.model.service.MemberInfoDuplicateCheck;
import com.kh.eco.token.model.dao.TokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
	
	private final AdminMemberMapper adminMemberMapper;
	private final MemberInfoDuplicateCheck midc;
	private final PasswordEncoder passwordEncoder;
	private final TokenMapper tokenMapper;

	@Override
	public AdminMemberDTO findMemberById(String memberId) {
		AdminMemberDTO member = adminMemberMapper.findMemberById(memberId);
		if(member == null) {
			throw new FindFailureException("요청하신 정보와 일치하는 회원이 없습니다.");
		} else { 
			return member;
		}
	}

	@Override
	public void updateMemberIdByAdmin(UpdateIdByAdminDTO member) {
		midc.idDuplicateCheck(member.getNewId()); // 아이디 중복 췤
		
		int result = adminMemberMapper.updateMemberIdByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("아이디 변경 실패");
		}
	}

	@Override
	public void updatePasswordByAdmin(UpdatePasswordByAdminDTO member) {
		// String encodedPassword = passwordEncoder.encode(member.getNewPwd());
		// log.info("암호화한 비밀번호 : {}", encodedPassword );
		member.setNewPwd(passwordEncoder.encode(member.getNewPwd()));
		
		int result = adminMemberMapper.updatePasswordByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("비밀번호 변경 실패");
		}
	}

	@Override
	public void updatePhoneByAdmin(UpdatePhoneByAdminDTO member) {
		midc.phoneDuplicateCheck(member.getNewPhone()); // 휴대폰 번호 중복 췤
		
		int result = adminMemberMapper.updatePhoneByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("전화번호 변경 실패");
		}
		
	}

	@Override
	public void updateEmailByAdmin(UpdateEmailByAdminDTO member) {
		midc.emailDuplicateCheck(member.getNewEmail()); // 이메일 중복 체크
		
		int result = adminMemberMapper.updateEmailByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("이메일 변경 실패");
		}
	}

	@Override
	public void updateRegionByAdmin(UpdateRegionByAdminDTO member) {
		UpdateRegionByAdminDTO findRegion = adminMemberMapper.findRegionNo(member.getNewRegion());
		if(findRegion == null) {
			throw new SQLResponseException("등록된 지역명이 아닙니다.");
		}
		member.setRegionNo(findRegion.getRegionNo());
		// log.info("제대로 지역번호 세팅되었나? : {}", member);
		
		int result = adminMemberMapper.updateRegionByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("지역 변경 실패");
		}
		
	}

	@Override
	public void updatePointByAdmin(UpdatePointByAdminDTO member) {
		int result = adminMemberMapper.updatePointByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("포인트 변경 실패");
		}
	}

	@Override
	@Transactional
	public void updateStatusByAdmin(UpdateStatusByAdminDTO member) {
		AdminMemberDTO statusChecker = adminMemberMapper.findStatus(member.getMemberNo());
		// log.info("기존 회원 상태값 : {}", statusChecker.getStatus());
		// log.info("요청들어온 회원 상태값 : {}", member.getNewStatus());
		if(statusChecker.getStatus().equals(member.getNewStatus())) {
			throw new MemberInfoDuplicatedException("요청이 이미 반영되어 있습니다.");
		}
		
		int result = adminMemberMapper.updateStatusByAdmin(member);
		tokenMapper.deleteToken(statusChecker.getMemberId());
		if(result == 0) {
			throw new SQLResponseException("회원상태 변경 실패");
		}
	}

	@Override
	public void updateRoleByAdmin(UpdateRoleByAdminDTO member) {
		AdminMemberDTO roleChecker = adminMemberMapper.findRole(member.getMemberNo());
		// log.info("기존 회원 권한값 : {}", roleChecker.getRole());
		// log.info("요청들어온 회원 권한값 : {}", member.getNewRole());
		if(roleChecker.getRole().equals(member.getNewRole())) {
			throw new MemberInfoDuplicatedException("요청이 이미 반영되어 있습니다.");
		}
		
		int result = adminMemberMapper.updateRoleByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("회원상태 변경 실패");
		}
	}

	@Override
	public void updateNameByAdmin(UpdateNameByAdminDTO member) {
		AdminMemberDTO nameChecker = adminMemberMapper.findName(member.getMemberNo());
		// log.info("기존 회원 이름값 : {}", nameChecker.getMemberName());
		// log.info("요청들어온 회원 이름값 : {}", member.getNewName());	
		if(nameChecker.getMemberName().equals(member.getNewName())) {
			throw new MemberInfoDuplicatedException("요청이 이미 반영되어 있습니다.");
		}
		
		int result = adminMemberMapper.updateNameByAdmin(member);
		if(result == 0) {
			throw new SQLResponseException("이름 변경 실패");
		}
		
	}
	
	

}
