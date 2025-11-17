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
public class MemberLogoutDTO {
	
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "적합하지 않은 요청입니다.")
	@Size(min = 2, max = 40, message = "적합하지 않은 요청입니다.")
	@NotBlank(message = "적합하지 않은 요청입니다.")
	private String memberId;
	
	@NotBlank(message = "적합하지 않은 요청입니다.")
	private String refreshToken;

}
