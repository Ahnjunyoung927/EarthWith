package com.kh.eco.board.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.board.model.service.BoardService;
import com.kh.eco.comment.model.dto.CommentDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("eco/boards")
public class BoardController {

	private final BoardService boardService;

    /**
     * selectBoardList
     * 보드 전체조회 메소드
     * @param currentPage
     * @return
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> selectBoardList(
            @RequestParam(value = "page", defaultValue = "1") int currentPage) {
        
        Map<String, Object> responseData = boardService.selectBoardList(currentPage);
        return ResponseEntity.ok(responseData);
    }
    
    /**
     * 게시물 상세조회 
     * @param boardNo
     * @return
     */
    @GetMapping("/{boardNo}")
    public ResponseEntity<?> selectBoardDetail(@PathVariable("boardNo") int boardNo) {
        
        BoardDetailDTO board = boardService.selectBoardDetail(boardNo);
        
        if(board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.status(404).body("게시글을 찾을 수 없습니다.");
        }				
    }
    
    
    /**
     * 게시물 작성 
     * @param board
     * @param file
     * @param user
     * @return 성공/실패 메시지
     */
    @PostMapping
    public ResponseEntity<?> insertBoard(@RequestPart("board") @Valid BoardDTO board,
    								     @RequestPart(value="file", required = false) MultipartFile file,
    								     @AuthenticationPrincipal CustomUserDetails user){
    	// 1. 로그인 유저 아이디 추출
    	String userId = user.getUsername();
    	
    	// 2. 서비스 호출
    	int result = boardService.insertBoard(board, file, userId);
    	
    	// 3. 결과 응답 반환 (수정된 부분)
    	if(result > 0) {
            return ResponseEntity.ok("게시글 등록 성공");
        } else {
            return ResponseEntity.status(500).body("게시글 등록 실패");
        }
    }
    

    /**
     * 게시물 수정 
     * @param boardNo
     * @param board
     * @param file
     * @param user
     * @return 성공/실패 메시지
     */
    @PutMapping("/{boardNo}")
    public ResponseEntity<?> updateBoard(@PathVariable ("boardNo") Long boardNo,
    				  					 @RequestPart("board") @Valid BoardDTO board,
    				  					 @RequestPart(value = "file", required = false) MultipartFile file,
    				  					 @AuthenticationPrincipal CustomUserDetails user){
        
        // 1. 수정할 게시글 번호 세팅
    	board.setBoardNo(boardNo);
    	
        // 2. 로그인 유저 아이디 추출
    	String userId = user.getUsername();
    	
        // 3. 서비스 호출
    	int result = boardService.updateBoard(board, file, userId);
    	
        // 4. 결과 응답 반환 (수정된 부분)
        if(result > 0) {
            return ResponseEntity.ok("게시글 수정 성공");
        } else {
            return ResponseEntity.status(500).body("게시글 수정 실패");
        }
    }
    
    /**
     * 게시물 삭제
     * @param boardNo 삭제할 게시글 번호
     * @param user 로그인한 유저 정보 (본인 확인용)
     * @return 성공/실패 여부 메시지
     */
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardNo") Long boardNo, // 메서드명 오타 수정 (delet -> delete)
                                         @AuthenticationPrincipal CustomUserDetails user) {
        
        // 1. 로그인 유저 아이디 추출
        String userId = user.getUsername();
        
        // 2. 서비스 호출 (게시글 번호와 작성자 ID만 넘김)
        int result = boardService.deleteBoard(boardNo, userId);
        
        // 3. 결과에 따른 명확한 응답 반환
        if (result > 0) {
            return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(500).body("게시글 삭제에 실패했습니다.");
        }
    }
    													
}
