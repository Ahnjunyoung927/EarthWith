package com.kh.eco.file;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MyRenamePolicy {
	
	// FileRenamePoliCy 인터페이스가 가지고 있는 rename추상메소드가 있음
	// rename메소드를 오버라이딩해서 기존파일명을 전달받아서 파일명을 수정한 뒤
	// 수정 파일을 반환해줄 것
	
	public File rename(File originFile) {

		// 원본 파일명
		String originName = originFile.getName();
		
		// 1. 원본파일의 확장자
		String ext = originName.substring(originName.lastIndexOf("."));
		
		// 2. 년월일시분초
		String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		// 3. 랜덤숫자
		int randomNo = (int)(Math.random() * 900) + 100;
		
		// 1 + 2 + 3
		String changeName = "Eco_" + currentTime + "_" + randomNo + ext;
		
		// 기존 파일명을 수정된 파일명으로 적용시켜서 반환
		return new File(originFile.getParent(), changeName);
	}
	


}