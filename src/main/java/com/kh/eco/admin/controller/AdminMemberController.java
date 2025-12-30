package com.kh.eco.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.kh.eco.admin.model.service.AdminMemberService;
import com.kh.eco.common.responseData.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("eco/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
	
	private final AdminMemberService adminMemberService;
	
	@GetMapping() // 회원정보 조회
	public ResponseEntity<SuccessResponse<AdminMemberDTO>> findMemberById(@RequestParam(value="keyword") @Valid String memberId){
		AdminMemberDTO member = adminMemberService.findMemberById(memberId);
		return SuccessResponse.ok(member);
	}
	
	/*
	 * 회원정보 변경해야할게 뭐뭐 있는가 > 아이디, 비밀번호, 휴대폰, 이메일, 지역, 포인트, 상태, 관리자권한
	 * 아이디 > 유니크 > 변경하려는 아이디가 이미 있는지 체크 후 변경
	 * 비밀번호 > 관리자가 직접 변경할지, 아니면 임의의 비밀번호 a123456같은걸로 자동으로 바뀌게 할것인지?
	 * 휴대폰번호 > 유니크 > 변경하려는 휴대폰번호가 이미 있는지 체크 후 변경
	 * 이메일 > 변경하려는 이메일이 이미 있는지 체크 후 변경
	 * 지역 > 선택지 제공하여 선택 후 변경
	 * 포인트 > 관리자가 직접 입력한 포인트로 변경
	 * 상태 > 딸깍
	 * 관리자권한 > 딸깍
	 */
	
	@PutMapping("id")
	public ResponseEntity<SuccessResponse<UpdateIdByAdminDTO>> updateMemberIdByAdmin(@RequestBody @Valid UpdateIdByAdminDTO member){
		adminMemberService.updateMemberIdByAdmin(member);
		
		return SuccessResponse.ok(member);
	}
	
	@PutMapping("password")
	public ResponseEntity<SuccessResponse<String>> updatePasswordByAdmin(@RequestBody @Valid UpdatePasswordByAdminDTO member){
		adminMemberService.updatePasswordByAdmin(member);
		return SuccessResponse.noContent("비밀번호 변경 성공");
	}
	
	@PutMapping("phone")
	public ResponseEntity<SuccessResponse<String>> updatePhoneByAdmin(@RequestBody @Valid UpdatePhoneByAdminDTO member){
		adminMemberService.updatePhoneByAdmin(member);
		return SuccessResponse.noContent("전화번호 변경 성공");
	}
	
	@PutMapping("email")
	public ResponseEntity<SuccessResponse<String>> updateEmailByAdmin(@RequestBody @Valid UpdateEmailByAdminDTO member){
		adminMemberService.updateEmailByAdmin(member);
		return SuccessResponse.noContent("이메일 변경 성공");
	}
	
	@PutMapping("region")
	public ResponseEntity<SuccessResponse<String>> updateRegionByAdmin(@RequestBody @Valid UpdateRegionByAdminDTO member){
		adminMemberService.updateRegionByAdmin(member);
		return SuccessResponse.noContent("지역 변경 성공");
	}
	
	@PutMapping("point")
	public ResponseEntity<SuccessResponse<String>> updatePointByAdmin(@RequestBody @Valid UpdatePointByAdminDTO member){
		adminMemberService.updatePointByAdmin(member);
		return SuccessResponse.noContent("포인트 변경 성공");
	}
	
	@PutMapping("status")
	public ResponseEntity<SuccessResponse<String>> updateStatusByAdmin(@RequestBody @Valid UpdateStatusByAdminDTO member){
		adminMemberService.updateStatusByAdmin(member);

		return SuccessResponse.noContent("회원상태 변경 성공");
	}
	
	@PutMapping("role")
	public ResponseEntity<SuccessResponse<String>> updateRoleByAdmin(@RequestBody @Valid UpdateRoleByAdminDTO member){
		adminMemberService.updateRoleByAdmin(member);
		return SuccessResponse.noContent("권한 변경 성공");
	}
	
	@PutMapping("name")
	public ResponseEntity<SuccessResponse<String>> updateNameByAdmin(@RequestBody @Valid UpdateNameByAdminDTO member){
		adminMemberService.updateNameByAdmin(member);
		return SuccessResponse.noContent("이름 변경 성공");
	}

	
}
