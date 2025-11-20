package com.kh.eco.member.model.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.member.model.dao.MemberMapper;
import com.kh.eco.member.model.dto.MemberSignUpDTO;
import com.kh.eco.member.model.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final MemberInfoDuplicateCheck midc;

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