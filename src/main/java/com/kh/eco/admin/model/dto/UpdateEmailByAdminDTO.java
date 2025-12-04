package com.kh.eco.admin.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateEmailByAdminDTO {
	
	@Positive(message = "회원번호는 1 이상의 양수여야 합니다.")
	private int memberNo;
	
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@NotBlank
	private String newEmail;

}
