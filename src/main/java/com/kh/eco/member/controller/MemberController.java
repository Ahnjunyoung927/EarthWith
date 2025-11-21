package com.kh.eco.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.member.model.dto.ChangePasswordDTO;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.dto.UpdateEmailDTO;
import com.kh.eco.member.model.dto.UpdatePhoneDTO;
import com.kh.eco.member.model.dto.UpdateProfileDTO;
import com.kh.eco.member.model.dto.UpdateRegionDTO;
import com.kh.eco.member.model.service.MemberService;
import com.kh.eco.member.model.vo.MemberVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
    @PostMapping
    public ResponseEntity<?> signUp(  		
            @Valid MemberSignUpDTO member, 
            @RequestParam(name = "profileImg", required = false) MultipartFile profileImg) {
        
        log.info("회원가입 요청 - 회원정보: {}", member);
        
        if (profileImg != null && !profileImg.isEmpty()) {
            log.info("프로필 이미지: {} ({}bytes)", 
                    profileImg.getOriginalFilename(), 
                    profileImg.getSize());
        } else {
            log.info("프로필 이미지: 없음");
        }
        
        
        memberService.signUp(member, profileImg);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

	
	/**
	 * changePassword - 비밀번호 변경 메소드
	 * @param password
	 * @return
	 */
	@PutMapping("password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO password) {
		log.info("비밀번호 정보 : {}", password);
		
		memberService.changePassword(password);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	// 지역, 프로필
	
	/**
	 * changeEmail - 이메일 변경 메소드
	 * @param email
	 * @return
	 */
	@PutMapping("email")
	public ResponseEntity<?> changeEmail(@Valid @RequestBody UpdateEmailDTO email) {
	
		log.info("이메일 정보 : {}", email);
		memberService.updateMemberEmail(email);
		return ResponseEntity.ok("이메일 변경 완료");
	}
	
	@PutMapping("phone")
	public ResponseEntity<?> changePhone(@Valid @RequestBody UpdatePhoneDTO phone) {
		memberService.updateMemberPhone(phone);
		return ResponseEntity.ok("번호 변경 완료");
	}
	
	@PostMapping("profile")
	public ResponseEntity<?> changeProfile(@RequestParam(name="file", required=false)MultipartFile file, UpdateProfileDTO profile) {
		log.info("file, profile : {}, {}",file, profile);
		memberService.updateMemberProfile(file, profile);
		return ResponseEntity.ok("프로필 변경 완료");
	}
	
	@PutMapping("region")
	public ResponseEntity<?> changeRegion(@Valid @RequestBody UpdateRegionDTO region) {
		memberService.updateMemberRegion(region);
		return ResponseEntity.ok("번호 변경 완료");
	}
}
