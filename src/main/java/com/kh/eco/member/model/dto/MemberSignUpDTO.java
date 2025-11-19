package com.kh.eco.member.model.dto;

import jakarta.validation.constraints.Email;
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
public class MemberSignUpDTO {
	
	@Pattern(regexp = "^[a-z가-힣]*$", message = "이름은 영어, 한글만 사용 가능합니다.")
	@Size(min = 2, max = 40, message = "이름은 2글자 이상 40글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "이름은 필수 입력사항입니다.")
	private String memberName;
	
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어/숫자만 사용 가능합니다.")
	@Size(min = 2, max = 20, message = "아이디 값은 2글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	private String memberId;
	
	@Pattern(regexp = "^(?=.{6,20}$)(?=.*[a-z])(?=.*\\d)[^\\s]+$", message = "비밀번호는 영어(소문자)/숫자가 각각 1개 이상 필요합니다.")
	@Size(min = 6, max = 20, message = "비밀번호 값은 6글자 이상 20글자 이하만 사용할 수 있습니다.")
	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	private String memberPwd;
	
	@Pattern(regexp = "^0\\d{1,2}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
	@NotBlank(message = "전화번호는 필수 입력사항입니다.")
	private String phone;
	
	@NotBlank(message = "이메일은 필수 입력사항입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	
	private int regionNo;
	
	// private String role;
	// private String memberImageUrl;

}
