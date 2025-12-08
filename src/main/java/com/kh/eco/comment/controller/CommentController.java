package com.kh.eco.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;
import com.kh.eco.comment.model.dto.CommentUpdateDTO;
import com.kh.eco.comment.model.service.CommentService;
import com.kh.eco.comment.model.vo.CommentVO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

	private final CommentService commentService;
	
	/**
	 * 댓글 작성
	 * @param comment
	 * @param userDetails
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> insertComment(@RequestBody CommentDTO comment, @AuthenticationPrincipal CustomUserDetails userDetails) {
		comment.setRefMno(userDetails.getMemberNo());
		CommentVO c = commentService.insertComment(comment, userDetails);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(c);
	}
	
	/**
	 * 댓글 조회
	 */
	@GetMapping
	public ResponseEntity<List<CommentDTO>> findAll(@RequestParam(name="boardNo") @Min(value = 1, message = "잘못된 접근입니다.") Long BoardNo) {
		return ResponseEntity.ok(commentService.findAll(BoardNo));
	}
	
	/**
	 * 댓글 신고
	 * @param commentNo
	 * @param reportDTO
	 * @param userDetails
	 * @return
	 */
	@PostMapping("/{commentNo}/reports")
	public ResponseEntity<?> commentReport(@PathVariable("commentNo") @Min(value = 1, message = "잘못된 접근입니다.") Long commentNo, @RequestBody @NotNull(message = "잘못된 접근입니다.") CommentReportDTO reportDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		// 댓글 존재 여부
		commentService.existById(commentNo);
		
		// 댓글 신고용 DTO 댓글번호 SET
		reportDTO.setRefCno(commentNo);
		
		// 댓글 신고 요청
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.commentReport(reportDTO));
		
	}
	
	/**
	 * 댓글 삭제
	 * @param commentNo
	 * @param userDetails
	 * @return
	 */
	@DeleteMapping("/{commentNo}")
	public ResponseEntity<?> deleteComment(@PathVariable("commentNo") @Min(value = 1, message = "잘못된 접근입니다.") Long commentNo, @AuthenticationPrincipal CustomUserDetails userDetails) {
		// 댓글 존재여부
		commentService.existById(commentNo);
			
		// REF_MNO GET
		Integer mno = userDetails.getMemberNo();
		
		// 본인 여부
		commentService.isOwner(commentNo, mno);
		
		// 댓글 삭제
		return ResponseEntity.ok(commentService.deleteComment(commentNo));
	
		
	}
	
	/**
	 * 댓글 수정
	 */
	@PutMapping("/{commentNo}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable(name="commentNo") @Min(value = 1, message = "잘못된 접근입니다.") Long commentNo, @RequestBody @Valid CommentUpdateDTO updateDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		// 댓글 존재여부
		commentService.existById(commentNo);
		
		// REF_MNO GET
		Integer mno = userDetails.getMemberNo();
		
		// 본인 여부
		commentService.isOwner(commentNo, mno);
		
		// CommentNo Set
		CommentDTO comment = new CommentDTO();
		comment.setCommentNo(commentNo);
		comment.setCommentContent(updateDTO.getCommentContent());
		
		// 댓글 수정 요청
		commentService.updateComment(comment);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
}
//=======
//    private final CommentService commentService;
//
//    // 1. 댓글 작성
//    @PostMapping("/board/{boardNo}")
//    public ResponseEntity<?> insertComment(@PathVariable("boardNo") Long boardNo,
//                                           @RequestBody CommentDTO comment,
//                                           @AuthenticationPrincipal CustomUserDetails user) {
//    	log.info("가나다라마바사아자카{}", boardNo);
//        
//        // [수정] CommentDTO는 Long을 원하므로 (long) 형변환
//        comment.setRefBno(boardNo); 
//        comment.setRefMno((long) user.getMemberNo()); 
//        
//        int result = commentService.insertComment(comment);
//        return result > 0 ? ResponseEntity.ok("댓글 등록 성공") : ResponseEntity.status(500).build();
//    }
//
//    // 2. 댓글 수정
//    @PutMapping("/{commentNo}")
//    public ResponseEntity<?> updateComment(@PathVariable("commentNo") Long commentNo,
//                                           @RequestBody CommentDTO comment,
//                                           @AuthenticationPrincipal CustomUserDetails user) {
//        comment.setCommentNo(commentNo); 
//        try {
//            commentService.updateComment(comment, user);
//            return ResponseEntity.ok("댓글 수정 성공");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(403).body(e.getMessage());
//        }
//    }
//
//    // 3. 댓글 삭제
//    @DeleteMapping("/{commentNo}")
//    public ResponseEntity<?> deleteComment(@PathVariable("commentNo") Long commentNo,
//                                           @AuthenticationPrincipal CustomUserDetails user) {
//        try {
//            commentService.deleteComment(commentNo, user);
//            return ResponseEntity.ok("댓글 삭제 성공");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(403).body(e.getMessage());
//        }
//    }
//
//    // 4. 댓글 신고
//    @PostMapping("/{commentNo}/reports")
//    public ResponseEntity<?> reportComment(@PathVariable("commentNo") Long commentNo,
//                                           @RequestBody CommentReportDTO report,
//                                           @AuthenticationPrincipal CustomUserDetails user) {
//        
//        // [수정] CommentReportDTO.refCno가 Long이면 그대로, int면 .intValue()
//        // 에러 로그(image_de94a0)상 Long을 원하므로 그대로 둡니다.
//        report.setRefCno(commentNo);
//        
//        int result = commentService.reportComment(report, user);
//        return result > 0 ? ResponseEntity.ok("신고 완료") : ResponseEntity.status(500).build();
//    }
//}
//>>>>>>> c2e92f2495841b37f3624a9a533f7c6288c95541
