package com.kh.eco.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.dto.BoardReportDTO;


import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;


@Mapper
public interface BoardMapper {
    
	// 조회
    /**
     * 페이징 처리를 위해 전체 게시글 수를 조회합니다.
     * @return 전체 게시글 수
     */
    int selectListCount();
    
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
     * 게시글 상세 정보를 조회합니다.
     * @param boardNo 게시글 번호
     * @return 게시글 상세 정보 DTO
     */
    BoardDetailDTO selectBoardDetail(int boardNo);


    //CUD
    /**
     * 게시글의 조회수를 1 증가시킵니다.
     * @param boardNo 게시글 번호
     * @return 업데이트된 행의 수
     */
    int increaseViewCount(int boardNo);

    /**
     * 새로운 게시글을 DB에 삽입합니다.
     * @param board 게시글 정보 DTO
     * @return 삽입된 행의 수
     */

	int insertBoard(BoardDTO board, MultipartFile file, String userId);
	
	/**
	 * 게시글을 신고합니다.
	 */
	int boardReport(BoardReportDTO reportDTO);
	
	/**
	 * 신고된 게시글 존재 여부
	 */
	BoardDTO selectBoardOne(int boardNo);

    int insertBoard(BoardDTO board);

    /**
     * 게시글을 수정합니다.
     * @param board 수정할 게시글 정보
     * @return 업데이트된 행의 수
     */
    int updateBoard(BoardDTO board);

    /**
     * 게시글을 삭제합니다.
     * @param map 삭제 조건 (게시글 번호 등)
     * @return 삭제된 행의 수
     */
    int deleteBoard(Map<String, Object> map);


    //첨부파일 
    int insertAttachment(Map<String, Object> fileMap);
    
    int updateAttachment(Map<String, Object> fileMap);
    
}