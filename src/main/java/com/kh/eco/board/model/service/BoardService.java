package com.kh.eco.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.dto.FeedBoardDTO;

import jakarta.validation.Valid;

/**
 * 게시판(Board) 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * 일반 게시글 및 피드형 게시글의 조회, 작성, 통계 기능을 정의합니다.
 */
public interface BoardService {

    /**
     * 피드형 게시글 목록을 무한 스크롤 방식으로 조회합니다.
     *
     * @param category    게시판 카테고리 코드 (예: "C" - 챌린지/피드)
     * @param fetchOffset 이전에 조회한 마지막 게시글의 번호 (이 번호보다 작은 글을 조회, 첫 조회 시 null)
     * @param limit       한 번의 요청으로 가져올 게시글의 개수
     * @return 조건에 맞는 피드 게시글 목록 (FeedBoardDTO 리스트)
     */
	List<FeedBoardDTO> getFeedList(String category, Long fetchOffset, int limit);
	
    /**
     * 새로운 피드 게시글을 저장합니다.
     *
     * @param feed     저장할 피드 게시글 정보 (내용, 태그 등)
     * @param file     함께 업로드할 첨부 파일 (이미지 등)
     * @param username 작성자의 사용자명(ID)
     */
	void saveFeed(FeedBoardDTO feed, MultipartFile file, String username);
	
    /**
     * 특정 카테고리의 오늘의 참여자 수를 조회합니다.
     *
     * @param category 카테고리 코드
     * @return 오늘의 참여자 수
     */
	int todayParticipants(String category);
	
    /**
     * 특정 카테고리의 오늘의 게시글 작성 수를 조회합니다.
     *
     * @param category 카테고리 코드
     * @return 오늘의 게시글 수
     */
	int todayPost(String category);

    /**
     * 참여형 게시판의 전체 게시글 수를 조회합니다.
     *
     * @return 전체 게시글 수
     */
	long getBoardCountForParticipation();
    
    /**
     * 게시판의 모든 게시글을 조건 없이 조회합니다.
     *
     * @return 전체 게시글 목록 (BoardDTO 리스트)
     */
    List<BoardDTO> selectBoardAll();
    
    /**
     * 페이징 처리된 일반 게시글 목록을 조회합니다.
     * 인기글(topPosts)과 페이징 정보(pi)를 포함하여 반환합니다.
     *
     * @param currentPage 현재 조회 요청한 페이지 번호
     * @return 게시글 목록, 인기글, 페이징 정보를 포함한 Map 객체
     */
    Map<String, Object> selectBoardList(int currentPage);
   
    /**
     * 특정 게시글의 상세 정보를 조회합니다.
     * 조회수 증가 로직이 포함될 수 있습니다.
     *
     * @param boardNo 조회할 게시글 번호
     * @return 게시글 상세 정보 (BoardDetailDTO), 존재하지 않으면 null
     */
    BoardDetailDTO selectBoardDetail(int boardNo);
    
    /**
     * 새로운 일반 게시글을 등록합니다.
     * * @param board  등록할 게시글 정보 (제목, 내용, 카테고리 등)
     * @param file   첨부 파일 (선택 사항)
     * @param userId 작성자 ID
     * @return 등록 성공 시 1, 실패 시 0
     */
	int insertBoard(@Valid BoardDTO board, MultipartFile file, String userId);

}