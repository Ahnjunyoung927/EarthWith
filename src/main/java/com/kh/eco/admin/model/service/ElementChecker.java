package com.kh.eco.admin.model.service;

import java.security.InvalidParameterException;

public class ElementChecker {
	
	public void checkGreaterThenZero(int pageNo) {
		if(pageNo < 1) {
			throw new InvalidParameterException("유효하지 않은 요청입니다.");
		}
	}

}
