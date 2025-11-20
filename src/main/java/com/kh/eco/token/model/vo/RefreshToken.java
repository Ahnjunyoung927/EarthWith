package com.kh.eco.token.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
	private int tokenNo;
	private String username;
	private String token;
	private Date regDate;
	private Long expiration;
}