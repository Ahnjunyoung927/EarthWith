package com.kh.eco.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class ChangePasswordDTO {
	
	private String currentPassword;
	
	@Pattern(regexp = "^(?=.{6,20}$)(?=.*[a-z])(?=.*\\d)[^\\s]+$", message = "비밀번호는 영어(소문자)/숫자가 각각 1개 이상 필요합니다.")
	@Size(min = 6, max = 20, message = "비밀번호 값은 6글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	private String newPassword;
}
