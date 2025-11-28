package com.kh.eco.member.model.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
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

public class UpdateRegionDTO {
	private int currentRegion;
	private int newRegion;
	public MultipartFile trim() {
		return null;
	}
}
