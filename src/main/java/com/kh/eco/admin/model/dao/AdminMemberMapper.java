package com.kh.eco.admin.model.dao;

import org.apache.ibatis.annotations.Mapper;

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

@Mapper
public interface AdminMemberMapper {
	
	// 아이디로 회원정보 조회하기
	AdminMemberDTO findMemberById(String memberId);
	
	// 아이디변경
	int updateMemberIdByAdmin(UpdateIdByAdminDTO member);
	
	// 비밀번호 변경
	int updatePasswordByAdmin(UpdatePasswordByAdminDTO member);
	
	// 휴대폰번호 변경
	int updatePhoneByAdmin(UpdatePhoneByAdminDTO member);
	
	// 이메일 변경
	int updateEmailByAdmin(UpdateEmailByAdminDTO member);
	
	// 지역 변경 로직 중 입력받은 지역이 DB에 등록된 지역이 맞는지 확인
	UpdateRegionByAdminDTO findRegionNo(String newRegion);
	
	// 지역 변경
	int updateRegionByAdmin(UpdateRegionByAdminDTO member);
	
	// 포인트 변경
	int updatePointByAdmin(UpdatePointByAdminDTO member);
	
	// 회원 상태 변경 중 변경 요청받은 값이 현재 값이랑 같은지 검사하기 위한 용도
	AdminMemberDTO findStatus(int memberNo);
	
	// 회원 상태 변경
	int updateStatusByAdmin(UpdateStatusByAdminDTO member);
	
	// 회원 권한 변경 중 변경 요청받은 값이 현재 값이랑 같은지 검사하기 위한 용도
	AdminMemberDTO findRole(int memberNo);
	
	// 회원 권한 변경
	int updateRoleByAdmin(UpdateRoleByAdminDTO member);
	
	// 회원 이름 체크
	AdminMemberDTO findName(int memberNo);
	
	// 회원 이름 변경
	int updateNameByAdmin(UpdateNameByAdminDTO member);
	
	
}
