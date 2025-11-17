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
public class MemberLoginDTO {
	
	private String memberName;
	
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어/숫자만 사용 가능합니다.")
	@Size(min = 2, max = 40, message = "아이디 값은 2글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	private String memberId;
	
	@Pattern(regexp = "^(?=.{6,20}$)(?=.*[a-z])(?=.*\\d)[^\\s]+$", message = "비밀번호는 영어(소문자)/숫자가 각각 1개 이상 필요합니다.")
	@Size(min = 6, max = 20, message = "비밀번호 값은 6글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	private String memberPwd;
	
	/*
	private String phone;
	private String email;
	private int regionNo;
	*/
	private String role;
	

}
