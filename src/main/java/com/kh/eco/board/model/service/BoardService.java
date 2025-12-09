package com.kh.eco.board.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;

import jakarta.validation.Valid;

/**
 * 게시판(Board) 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * 일반 게시글 및 피드형 게시글의 조회, 작성, 통계 기능을 정의합니다.
 */
public interface BoardService {
	
    
    /**
     * 페이징 처리된 일반 게시글 목록을 조회합니다.
     * 인기글(topPosts)과 페이징 정보(pi)를 포함하여 반환합니다.
     *
     * @param currentPage 현재 조회 요청한 페이지 번호
     * @return 게시글 목록, 인기글, 페이징 정보를 포함한 Map 객체
     */
    Map<String, Object> selectBoardList(int currentPage);
   
    /**
     * 특정 게시글의 상세 정보를 조회
     * 조회수 증가 로직이 포함될 수 있습니다.
     *
     * @param boardNo 조회할 게시글 번호
     * @return 게시글 상세 정보 (BoardDetailDTO), 존재하지 않으면 null
     */
    BoardDetailDTO selectBoardDetail(int boardNo);
    
    /**
     * 새로운 일반 게시글을 등록
     * * @param board  등록할 게시글 정보 (제목, 내용, 카테고리 등)
     * @param file   첨부 파일 (선택 사항)
     * @param userId 작성자 ID
     * @return 등록 성공 시 1, 실패 시 0
     */
	int insertBoard(@Valid BoardDTO board, MultipartFile file, String userId);

	int updateBoard(@Valid BoardDTO board, MultipartFile file, String userId);

	int deleteBoard(Long boardNo, String userId);

	long getBoardCountForParticipation();
}