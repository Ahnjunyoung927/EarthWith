package com.kh.eco.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UpdateEmailDTO {
	private String currentEmail;
	
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "이메일 형식이 올바르지 않습니다")
	@NotBlank(message = "이메일은 필수 입력사항입니다.")
	private String newEmail;
}
