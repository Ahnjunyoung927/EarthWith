package com.kh.eco.file;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class MyRenamePolicy {

    public String rename(MultipartFile file) {

        // 원본 파일명
        String originalName = file.getOriginalFilename();

        if (originalName == null) {
            throw new IllegalArgumentException("파일 이름이 없습니다.");
        }

        // 1. 확장자
        String ext = originalName.substring(originalName.lastIndexOf("."));

        // 2. 년월일시분초
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 3. 랜덤 숫자
        int randomNo = (int)(Math.random() * 900) + 100;

        // 4. 최종 파일명
        String changeName = "Eco_" + currentTime + "_" + randomNo + ext;

        return changeName;
    }
}