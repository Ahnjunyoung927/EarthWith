package com.kh.eco.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.auth.model.vo.CustomUserDetails;
import com.kh.eco.board.model.dao.BoardMapper;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.comment.model.dto.CommentDTO;
import com.kh.eco.comment.model.dto.CommentReportDTO;
import com.kh.eco.common.PageInfo;
import com.kh.eco.common.Pagination;
import com.kh.eco.file.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;
    private final Pagination pagination;

    // 1. 게시글 목록 조회
	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		int listCount = boardMapper.selectListCount();
		
		int pageLimit = 10;
		int boardLimit = 10;
		
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		int offset = (currentPage - 1) * boardLimit;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		int startRow = (currentPage - 1) * boardLimit + 1;
		int endRow = startRow + boardLimit - 1;

		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage, offset); 
		
		List<BoardDTO> topPosts = boardMapper.selectTopBoardList();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);
		
		List<BoardDTO> listPosts = boardMapper.selectBoardList(paramMap);

		Map<String, Object> map = new HashMap<>();
		map.put("topPosts", topPosts);
		map.put("list", listPosts);
		map.put("pi", pi);
		
		return map;
	}

    // 2. 상세 조회
    @Transactional
    @Override
    public BoardDetailDTO selectBoardDetail(int boardNo) {
        int result = boardMapper.increaseViewCount(boardNo);
        if(result > 0) {
            return boardMapper.selectBoardDetail(boardNo);
        } else {
            return null;
        }
    }
    
    // 3. 게시글 작성
    @Transactional
    @Override
    public int insertBoard(@Valid BoardDTO board, MultipartFile file, String userId) {
        
        board.setBoardWriter(userId);
        
        int result = boardMapper.insertBoard(board);
        
        // 파일 처리 로직
        if(file != null && !file.isEmpty()) {
            
            String changeName = fileService.store(file); 
            
            String originalName = file.getOriginalFilename();
            
            String attachmentPath = "http://localhost:8081/uploads/" + changeName;
            
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("refBno", board.getBoardNo());
            fileMap.put("originName", originalName); 
            fileMap.put("changeName", changeName);   
            fileMap.put("attachmentPath", attachmentPath); 
            
            boardMapper.insertAttachment(fileMap);
        }
        
        return result;
    }
    
    // 4. 게시글 수정 
    @Transactional
    @Override
    public int updateBoard(@Valid BoardDTO board, MultipartFile file, String userId) {
        
        board.setBoardWriter(userId);
        
        int result = boardMapper.updateBoard(board);
        
        if(file != null && !file.isEmpty()) {
            
            String changeName = fileService.store(file);
            String originalName = file.getOriginalFilename();
            String attachmentPath = "/uploads/" + changeName;
            
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("refBno", board.getBoardNo());
            fileMap.put("originName", originalName);
            fileMap.put("changeName", changeName);
            fileMap.put("attachmentPath", attachmentPath);
            
            int updateCount = boardMapper.updateAttachment(fileMap);
            
            if(updateCount == 0) {
                boardMapper.insertAttachment(fileMap);
            }
        }
        
        return result;
    }

    // 5. 게시글 삭제
    @Transactional
    @Override
    public int deleteBoard(Long boardNo, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("boardNo", boardNo);
        map.put("userId", userId);
        
        return boardMapper.deleteBoard(map);
    }

	@Override
	public long getBoardCountForParticipation() {
		// TODO Auto-generated method stub
		return boardMapper.getBoardCountForParticipation();
	}

}

