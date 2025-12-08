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
public class UpdateStatusByAdminDTO {
	
	@Positive(message = "회원번호는 1 이상의 양수여야 합니다.") 
	private int memberNo;
	
	@Pattern(regexp = "^[YN]$", message = "상태 값이 부정확합니다.")
	@Size(min = 1, max = 1)
	@NotBlank
	private String newStatus;

}
