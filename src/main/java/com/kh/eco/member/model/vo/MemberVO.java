package com.kh.eco.member.model.vo;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class MemberVO {
	private String memberName;
	private String memberId;
	private String memberPwd;
	private String phone;
	private String email;
	private int refRno;
	private String memberImage;

}
