package com.kh.eco.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

    private final Path fileLocation;
    private final MyRenamePolicy renamePolicy = new MyRenamePolicy();

    public FileService() {
        this.fileLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (IOException e) {
            throw new RuntimeException("업로드 폴더 생성 실패", e);
        }
    }

    public String store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        try {
            // 1) 새 파일명 생성
            String newFilename = renamePolicy.rename(file);

            // 2) 저장할 경로
            Path targetPath = this.fileLocation.resolve(newFilename);

            // 3) 파일 저장
            Files.copy(
                file.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
            );

            // 4) URL 또는 경로 반환
            return "http://localhost:8081/uploads/" + newFilename;

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류", e);
        }
    }
}