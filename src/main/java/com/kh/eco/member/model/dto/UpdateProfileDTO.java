package com.kh.eco.member.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UpdateProfileDTO {
	private String memberId; // 회원 ID
    private MultipartFile newImage; // 새 이미지 파일

    // 서버에 저장 후 DB에 넣을 경로
    private String imagePath;
}
