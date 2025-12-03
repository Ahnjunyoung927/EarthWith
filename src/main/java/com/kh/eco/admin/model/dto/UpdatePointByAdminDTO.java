package com.kh.eco.admin.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class UpdatePointByAdminDTO {
	
	@Positive(message = "회원번호는 1 이상의 숫자여야 합니다.")
	private int memberNo;
	
	@Min(value = 0, message = "포인트는 0 이상의 숫자여야 합니다.")
	private int newPoint;
	
}
