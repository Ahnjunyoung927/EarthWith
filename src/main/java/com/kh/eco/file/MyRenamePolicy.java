package com.kh.eco.file;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MyRenamePolicy {

    public File rename(File originFile) {

        // 원본 파일명
        String originName = originFile.getName();


        //1. 원본파일의 확장자 
        String ext = originName.substring(originName.lastIndexOf("."));

        // 2. 년월일시분초
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 3. 랜덤 숫자
        int randomNo = (int)(Math.random() * 900) + 100;

        // 4. 최종 파일명
        String changeName = "Eco_" + currentTime + "_" + randomNo + ext;

        return new File(originFile.getParent(), changeName);
    }
}