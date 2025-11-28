package com.kh.eco.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.dto.FeedBoardDTO;


@Mapper
public interface BoardMapper {
	// 피드 게시판 (Feed Board)
    /**
     * 피드 목록을 조회합니다.
     * @param category 카테고리 (LIKE 검색 패턴 포함)
     * @param fetchOffset 페이징을 위한 기준 게시글 번호
     * @param limit 가져올 개수
     * @return 피드 목록
     */

	
    /**
     * 피드 게시글을 저장합니다.
     * @param feed 피드 데이터 객체
     */

	
    /**
     * 오늘의 참여자 수를 카운트합니다.
     * @param category 카테고리
     * @return 참여자 수
     */

	
    /**
     * 오늘의 게시글 수를 카운트합니다.
     * @param category 카테고리
     * @return 게시글 수
     */


	// 일반 게시판 (General Board)

    /**
     * 참여형 게시판의 전체 글 수를 조회합니다.
     * @return 전체 글 수
     */
	long getBoardCountForParticipation();

    /**
     * 모든 게시글을 조회합니다.
     * @return 게시글 리스트
     */
	List<BoardDTO> selectBoardAll();

    /**
     * 페이징 처리를 위해 전체 게시글 수를 조회합니다.
     * @return 전체 게시글 수
     */
	int selectListCount();

    /**
     * 페이징 범위에 해당하는 게시글 목록을 조회합니다.
     * @param paramMap startRow, endRow가 담긴 맵
     * @return 게시글 리스트
     */
	List<BoardDTO> selectBoardList(Map<String, Object> paramMap);

    /**
     * 조회수가 높은 상위 게시글(인기글)을 조회합니다.
     * @return 인기 게시글 리스트
     */
	List<BoardDTO> selectTopBoardList();

    /**
     * 게시글의 조회수를 1 증가시킵니다.
     * @param boardNo 게시글 번호
     * @return 업데이트된 행의 수
     */
	int increaseViewCount(int boardNo);

    /**
     * 게시글 상세 정보를 조회합니다.
     * @param boardNo 게시글 번호
     * @return 게시글 상세 정보 DTO
     */
	BoardDetailDTO selectBoardDetail(int boardNo);

    /**
     * 새로운 게시글을 DB에 삽입합니다.
     * @param board 게시글 정보 DTO (제목, 내용, 작성자 등)
     * @param userId 
     * @param file 
     * @return 삽입된 행의 수
     */
	int insertBoard(BoardDTO board, MultipartFile file, String userId);

}