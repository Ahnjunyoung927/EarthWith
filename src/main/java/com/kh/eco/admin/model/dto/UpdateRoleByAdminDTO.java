package com.kh.eco.admin.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class UpdateRoleByAdminDTO {
	
	@Positive(message = "회원번호는 1 이상의 양수여야 합니다.") 
	private int memberNo;
	
	@Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN)$", message = "권한 값이 올바르지 않습니다.")
	@NotBlank
	private String newRole;

}
