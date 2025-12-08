package com.kh.eco.admin.model.service;

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

public interface AdminMemberService {
	
	AdminMemberDTO findMemberById(String memberId);
	
	void updateMemberIdByAdmin(UpdateIdByAdminDTO member);
	
	void updatePasswordByAdmin(UpdatePasswordByAdminDTO member);
	
	void updatePhoneByAdmin(UpdatePhoneByAdminDTO member);
	
	void updateEmailByAdmin(UpdateEmailByAdminDTO member);
	
	void updateRegionByAdmin(UpdateRegionByAdminDTO member);
	
	void updatePointByAdmin(UpdatePointByAdminDTO member);
	
	void updateStatusByAdmin(UpdateStatusByAdminDTO member);
	
	void updateRoleByAdmin(UpdateRoleByAdminDTO member);
	
	void updateNameByAdmin(UpdateNameByAdminDTO member);

}
