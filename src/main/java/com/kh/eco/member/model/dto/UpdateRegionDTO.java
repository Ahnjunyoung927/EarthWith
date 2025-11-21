package com.kh.eco.member.model.dto;

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
	private String memberId;
	private int currentRegion;
	private int newRegion;
}
