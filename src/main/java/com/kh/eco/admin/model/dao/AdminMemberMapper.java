package com.kh.eco.admin.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.eco.admin.model.dto.AdminMemberDTO;
import com.kh.eco.admin.model.dto.UpdateEmailByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateIdByAdminDTO;
import com.kh.eco.admin.model.dto.UpdatePasswordByAdminDTO;
import com.kh.eco.admin.model.dto.UpdatePhoneByAdminDTO;
import com.kh.eco.admin.model.dto.UpdatePointByAdminDTO;
import com.kh.eco.admin.model.dto.UpdateRegionByAdminDTO;

@Mapper
public interface AdminMemberMapper {
	
	// 아이디로 회원정보 조회하기
	AdminMemberDTO findMemberById(String memberId);
	
	int updateMemberIdByAdmin(UpdateIdByAdminDTO member);
	
	int updatePasswordByAdmin(UpdatePasswordByAdminDTO member);
	
	int updatePhoneByAdmin(UpdatePhoneByAdminDTO member);
	
	int updateEmailByAdmin(UpdateEmailByAdminDTO member);
	
	UpdateRegionByAdminDTO findRegionNo(String newRegion);
	
	int updateRegionByAdmin(UpdateRegionByAdminDTO member);

	int updatePointByAdmin(UpdatePointByAdminDTO member);
}
