package com.kh.eco.admin.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.admin.model.dao.AdminNoticeMapper;
import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminBoardDetailDTO;
import com.kh.eco.admin.model.dto.AttachmentDTO;
import com.kh.eco.admin.model.dto.NoticeRequestDTO;
import com.kh.eco.admin.model.dto.PageResponse;
import com.kh.eco.exception.DeleteFailureException;
import com.kh.eco.exception.PageNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminNoticeServiceImpl implements AdminNoticeService {

    private final AdminNoticeMapper adminNoticeMapper;

    // 파일 저장 경로 (프로젝트 루트의 uploads 폴더)
    private final String UPLOAD_DIR = "uploads/";

    @Override
    public PageResponse selectNoticeList(int pageNo) {
        // 기존 코드와 동일한 RowBounds 방식 사용
        RowBounds rb = new RowBounds(pageNo * 5, 5); // 한 페이지당 5개
        List<AdminBoardDTO> list = adminNoticeMapper.selectNoticeList(rb);
        
        if (list == null) {
            throw new PageNotFoundException("조회된 공지사항이 없습니다.");
        }
        
        int totalCount = adminNoticeMapper.countNotices();
        return new PageResponse(list, totalCount);
    }

    @Override
    public AdminBoardDetailDTO selectNoticeDetail(Long boardNo) {
        AdminBoardDTO board = adminNoticeMapper.selectNotice(boardNo);
        if (board == null) {
            throw new PageNotFoundException("존재하지 않는 공지사항입니다.");
        }
        
        List<AttachmentDTO> attachments = adminNoticeMapper.selectAttachments(boardNo);
        return new AdminBoardDetailDTO(board, attachments);
    }

    @Override
    @Transactional
    public void insertNotice(NoticeRequestDTO notice, List<MultipartFile> files) {
        // 01. 게시글(공지사항) 등록
        int result = adminNoticeMapper.insertNotice(notice);
        if (result == 0) throw new RuntimeException("공지사항 등록 실패");

        // 02. 파일 저장 및 Attachment 등록
        if (files != null && !files.isEmpty()) {
            saveFiles(files, notice.getBoardNo());
        }
    }

    @Override
    @Transactional
    public void updateNotice(NoticeRequestDTO notice, List<MultipartFile> files) {
        // 01. 게시글 내용 수정
        int result = adminNoticeMapper.updateNotice(notice);
        if (result == 0) throw new RuntimeException("공지사항 수정 실패");

        // 02. 파일 처리 (간단하게 기존 파일 유지 + 새 파일 추가 방식으로 구현)
        // 만약 기존 파일을 삭제하고 싶다면 deleteAttachments 호출 필요
        if (files != null && !files.isEmpty()) {
            saveFiles(files, notice.getBoardNo());
        }
    }

    @Override
    @Transactional
    public void deleteNotice(Long boardNo) {
        int result = adminNoticeMapper.deleteNotice(boardNo);
        if (result == 0) {
            throw new DeleteFailureException("공지사항 삭제 실패 (이미 삭제되었거나 존재하지 않음)");
        }
        // 첨부파일도 Soft Delete
        adminNoticeMapper.deleteAttachments(boardNo);
    }

    // [내부 메서드] 실제 파일을 저장하고 DB에 정보를 넣는 로직
    private void saveFiles(List<MultipartFile> files, Long boardNo) {
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            // 파일명 중복 방지를 위한 UUID 사용
            String changeName = UUID.randomUUID().toString() + "_" + originalName;
            
            // 주의: DTO에는 웹 접근 경로(/uploads/...)를 담아야 함
            // 실제 저장 경로: C:/.../uploads/파일명
            // DB 저장 경로: /uploads/파일명
            
            try {
                // 01. 실제 파일 저장 (절대 경로 사용)
                file.transferTo(new File(folder.getAbsolutePath() + File.separator + changeName));
                
                // 02. DB Insert용 DTO 생성
                AttachmentDTO attachment = new AttachmentDTO();
                attachment.setRefBno(boardNo);
                
                // 수정된 DTO 필드명에 맞춰 Setter 호출
                attachment.setOriginalFileName(originalName);
                attachment.setModifiedFileName(changeName);
                attachment.setAttachmentPath("/uploads/" + changeName);

                adminNoticeMapper.insertAttachment(attachment);

            } catch (IOException e) {
                log.error("파일 업로드 중 에러 발생: {}", e.getMessage());
                throw new RuntimeException("파일 업로드 실패");
            }
        }
    }
}
