package com.kh.eco.member.model.service;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.exception.CustomAuthenticationException;
import com.kh.eco.exception.IdDuplicateException;
import com.kh.eco.file.FileService;
import com.kh.eco.member.model.dao.MemberMapper;
import com.kh.eco.member.model.dto.ChangePasswordDTO;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.dto.UpdateEmailDTO;
import com.kh.eco.member.model.dto.UpdatePhoneDTO;
import com.kh.eco.member.model.dto.UpdateProfileDTO;
import com.kh.eco.member.model.dto.UpdateRegionDTO;
import com.kh.eco.member.model.dto.UpdateProfileDTO;
import com.kh.eco.member.model.vo.MemberVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MemberInfoDuplicateCheck midc;
	private final FileService fileService;

    @Override
    public void signUp(MemberSignUpDTO member, MultipartFile profileImg) {

		midc.idDuplicateCheck(member); // 아이디 중복검사
		
		midc.phoneDuplicateCheck(member); // 폰 중복검사
		
		midc.emailDuplicateCheck(member); // 이메일 중복검사

        // 프로필 이미지 URL 변수
        String profileImgUrl = null;

        // 기본 프로필 이미지 URL
        String defaultImageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Funknownblog.tistory.com%2F343&psig=AOvVaw1L0tJupKrwUuzY4b2x4Wq-&ust=1763535249259000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNCBj_WO-5ADFQAAAAAdAAAAABAL";

        // 프로필이미지 저장
        if (profileImg != null && !profileImg.isEmpty()) {
            String savePath = "/eco/src/main/resources/img/profileImg";

            //원본 파일명
            String originalFilename = profileImg.getOriginalFilename();

            //고유 파일명 생성
            String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            String ext = "";
            if(originalFilename != null && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = currentTime + "_" + (int)(Math.random() * 1000) + ext;

            try {
                File saveDir = new File(savePath);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                profileImg.transferTo(new File(savePath + uniqueFilename));

                profileImgUrl = "/resources/profile/" + uniqueFilename;

            } catch (IllegalStateException | IOException e) {
                log.error("프로필 이미지 저장 실패", e);
            }
        }

        if (profileImgUrl == null) {
            profileImgUrl = defaultImageUrl;
        }

        // 현재 시간을 sql.Date로 변환
        Date currentDate = new Date(System.currentTimeMillis());
        
        // MemberVo 빌드
        MemberVO signUpMember = MemberVO.builder()
                                  .memberName(member.getMemberName())
                                  .memberId(member.getMemberId())
                                  .memberPwd(passwordEncoder.encode(member.getMemberPwd()))
                                  .phone(member.getPhone())
                                  .email(member.getEmail())
                                  .refRno(member.getRefRno())
                                  .memberImageUrl(profileImgUrl)
                                  .memberPoint(0L)
                                  .enrollDate(currentDate)
                                  .status('Y')
                                  .role("ROLE_USER")
                                  .build();

        memberMapper.signUp(signUpMember);
        log.info("사용자등록 성공 : {}", signUpMember);
		
	}
	
	@Override
	public void changePassword(ChangePasswordDTO password) {
		CustomUserDetails user = validatePassword(password.getCurrentPassword());
		
		String newPassword = passwordEncoder.encode(password.getNewPassword()); // 
		
		Map<String, String> changeRequest = Map.of("memberId", user.getUsername(),
												   "newPassword", newPassword);
		
		memberMapper.changePassword(changeRequest);
	}
	
	private CustomUserDetails validatePassword(String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		// 검증이 맞다면
		log.info("pw : {}", password);
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomAuthenticationException("비밀번호가 일치하지 않습니다");
		}
		
		return user;
		
	}
	
	@Override
	public void updateMemberEmail(UpdateEmailDTO email) {
		CustomUserDetails user = validateEmail(email.getNewEmail());
		log.info("serviceImpl email : {}" , email);
		String newEmail = email.getNewEmail();
		
		Map<String, String> changeRequest = Map.of("memberId", user.getUsername(),
												   "newEmail", newEmail);
		
		// 4) 이메일 업데이트
		memberMapper.updateEmail(changeRequest);
	}
	
	private CustomUserDetails validateEmail(String string) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		log.info("string : {}" , string);
		
		// 3) 이미 사용중인 이메일인지 체크
		MemberVO duplicated = memberMapper.findByEmail(string);
		if(duplicated != null && !duplicated.getEmail().equals(string)) {
			throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
		}
		
		return user;
		
	}
	
	@Override
	public void updateMemberPhone(UpdatePhoneDTO phone) {
		CustomUserDetails user = validatePhone(phone.getNewPhone());
		
		String newPhone = phone.getNewPhone();
		//log.info("newPhone, getUsername : {} {}", newPhone, user.getUsername());
		Map<String, String> changeRequest = Map.of("memberId", user.getUsername(),
												   "newPhone", newPhone);
		
		memberMapper.updatePhone(changeRequest);
		
	}
	
	private CustomUserDetails validatePhone(String phone) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		
		if(phone == null || phone.trim().isEmpty()) {
			throw new IllegalArgumentException("번호는 비어 있을 수 없습니다");
		}
		
		if(!phone.matches("^0\\d{1,2}-\\d{3,4}-\\d{4}$")) {
			throw new IllegalArgumentException("올바른 번호 형식이 아닙니다");
		}
		MemberVO duplicated = memberMapper.findByPhone(phone);
		if(duplicated != null && !duplicated.getPhone().equals(phone)) {
			throw new IllegalArgumentException("이미 사용중인 번호입니다");
		}
		
		return user;
	}
	
	@Override
	public void updateMemberRegion(UpdateRegionDTO region) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		//user = region.get
		
		int newRegion = region.getNewRegion();
		log.info("region : {}", region );
		Map<String, Object> changeRequest = Map.of("memberId", user.getUsername(),
												   "newRegion", newRegion);
		log.info("changeRequest : {}", changeRequest);
		memberMapper.updateRegion(changeRequest);
		
	}
	
	private CustomUserDetails validateRegion(String region) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		
		if(region == null || region.trim().isEmpty()) {
			throw new IllegalArgumentException("지역은 비어 있을 수 없습니다");
		}
	}
	
	@Override
	public void updateMemberProfile(MultipartFile file, UpdateProfileDTO profile) {

		String newImage = profile.getNewImage();
		
		memberMapper.updateProfile(profile);

	}
	
//	@Override
//	public void updateMemberProfile(MultipartFile file) {
//		CustomUserDetails user = validateProfile(file.getCurrentImageUrl());
//		
//		String newImageUrl = file.getNewImageUrl();
//		//log.info("newPhone, getUsername : {} {}", newPhone, user.getUsername());
//		Map<String, String> changeRequest = Map.of("memberId", user.getUsername(),
//												   "newImageUrl", newImageUrl);
//		
//		memberMapper.updateProfile(changeRequest);		
//	}
//	
//	private CustomUserDetails validateProfile(String profile) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
//		
//		if(profile == null || profile.trim().isEmpty()) {
//			throw new IllegalArgumentException("번호는 비어 있을 수 없습니다");
//		}
//		
//		return user;
//	}
	
	
    @Override
    public long getActiveMemberCount() {
        try {
            return memberMapper.getActiveMemberCount();
        } catch (Exception e) {
            log.error("활성 회원 수 조회 중 오류 발생", e);
            return 0L;
        }
    }

    @Override
    public List<Map<String, Object>> getMemberRank() {
        return memberMapper.getMemberRank();
    }


}
