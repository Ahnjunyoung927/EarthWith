package com.kh.eco.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.admin.model.dao.AdminNoticeMapper;
import com.kh.eco.admin.model.dto.AdminBoardDetailDTO;
import com.kh.eco.admin.model.dto.NoticeRequestDTO;
import com.kh.eco.admin.model.dto.PageResponse;
import com.kh.eco.admin.model.service.AdminNoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("eco/admin/notices")
@RequiredArgsConstructor
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;
    private final AdminNoticeMapper adminNoticeMapper;

    // 01. 공지사항 목록 조회 (GET /admin/notices?page=0)
    @GetMapping
    public ResponseEntity<PageResponse> getNoticeList(@RequestParam(name="page", defaultValue="0") int pageNo) {
        PageResponse result = adminNoticeService.selectNoticeList(pageNo);
        return ResponseEntity.ok(result);
    }

    // 02. 공지사항 상세 조회 (GET /admin/notices/{boardNo})
    @GetMapping("/{boardNo}")
    public ResponseEntity<AdminBoardDetailDTO> getNoticeDetail(@PathVariable("boardNo") Long boardNo) {
        AdminBoardDetailDTO detail = adminNoticeService.selectNoticeDetail(boardNo);
        return ResponseEntity.ok(detail);
    }

    // 03. 공지사항 등록 (POST /admin/notices) - 관리자 전용
    // React: FormData 사용 필수. formData.append("notice", JSON.stringify(data)); formData.append("files", file);
    @PostMapping
    public ResponseEntity<String> registNotice(
            @RequestPart("notice") NoticeRequestDTO notice,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        
        // 작성자(관리자) ID 설정 (임시로 1번 관리자, 실제로는 SecurityContextHolder에서 가져와야 함)
        if (notice.getRefMno() == null) notice.setRefMno(1L); 
        
        adminNoticeService.insertNotice(notice, files);
        return ResponseEntity.ok("공지사항이 등록되었습니다.");
    }

    // 04. 공지사항 수정 (PUT /admin/notices/{boardNo})
    @PutMapping("/{boardNo}")
    public ResponseEntity<String> modifyNotice(
            @PathVariable("boardNo") Long boardNo,
            @RequestPart("notice") NoticeRequestDTO notice,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        
        notice.setBoardNo(boardNo);
        adminNoticeService.updateNotice(notice, files);
        return ResponseEntity.ok("공지사항이 수정되었습니다.");
    }

    // 05. 공지사항 삭제 (DELETE /admin/notices/{boardNo})
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<String> removeNotice(@PathVariable("boardNo") Long boardNo) {
        adminNoticeService.deleteNotice(boardNo);
        return ResponseEntity.ok("공지사항이 삭제되었습니다.");
    }
    
 // ... 기존 코드 아래에 추가 ...

    @GetMapping("/test-connection")
    public ResponseEntity<String> testConnection() {
        // 1. 공지사항(A%) 개수 조회
        int count = adminNoticeMapper.countNotices(); // Service에 이 메서드가 없으면 Mapper를 직접 호출해도 됨
        // 만약 Service에 countNotices가 public이 아니라면, 
        // 임시로 AdminNoticeMapper를 주입받아 adminNoticeMapper.countNotices()를 호출하세요.
        
        return ResponseEntity.ok("현재 서버가 조회한 공지사항 개수: " + count + "개");
    }
    
    @GetMapping("/test-list")
    public ResponseEntity<java.util.List<com.kh.eco.admin.model.dto.AdminBoardDTO>> testList() {
        // 페이징 없이 앞부분 10개만 가져오라고 요청
        org.apache.ibatis.session.RowBounds rb = new org.apache.ibatis.session.RowBounds(0, 10);
        java.util.List<com.kh.eco.admin.model.dto.AdminBoardDTO> list = adminNoticeMapper.selectNoticeList(rb);
        
        return ResponseEntity.ok(list);
    }
}
