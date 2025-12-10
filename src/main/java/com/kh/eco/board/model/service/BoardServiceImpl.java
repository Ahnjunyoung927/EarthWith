package com.kh.eco.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.eco.board.model.dao.BoardMapper;
import com.kh.eco.board.model.dto.BoardDTO;
import com.kh.eco.board.model.dto.BoardDetailDTO;
import com.kh.eco.common.PageInfo;
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
    
    private static final String UPLOAD_PATH = "/uploads/";

    @Override
    public Map<String, Object> selectBoardList(Map<String, Object> map) {
        // 1. 전체 개수 조회
        int listCount = boardMapper.selectListCount(map);
        
        // 2. 페이징 계산
        int currentPage = 1;
        if(map.get("currentPage") != null) {
            currentPage = Integer.parseInt(String.valueOf(map.get("currentPage")));
        }
        

        int pageLimit = 10;
        int boardLimit = 10;
        int maxPage = (int)Math.ceil((double)listCount / boardLimit);
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        int endPage = startPage + pageLimit - 1;
        if(endPage > maxPage) endPage = maxPage;
        int offset = (currentPage - 1) * boardLimit;
        
        PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage, offset);
        
        int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
        int endRow = startRow + pi.getBoardLimit() - 1;
        
        map.put("startRow", startRow);
        map.put("endRow", endRow);
        
        // 3. 목록 조회 (Map 전달)
        List<BoardDTO> list = boardMapper.selectBoardList(map);
        List<BoardDTO> topPosts = boardMapper.selectTopBoardList(map);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("topPosts", topPosts);
        result.put("pi", pi);
        
        return result;
    }

    @Override
    @Transactional
    public BoardDetailDTO selectBoardDetail(Long boardNo) {
        boardMapper.increaseViewCount(boardNo);
        return boardMapper.selectBoardDetail(boardNo);
    }

    @Override
    @Transactional
    public int insertBoard(BoardDTO board, MultipartFile file, String userId) {
        board.setBoardWriter(userId);
        int result = boardMapper.insertBoard(board);

        // 파일 처리 로직
        if(file != null && !file.isEmpty()) {
            saveAttachment(board.getBoardNo(), file);
        }
        
        return result;
    }
    
    @Transactional
    @Override
    public int updateBoard(@Valid BoardDTO board, MultipartFile file, String userId) {
        board.setBoardWriter(userId);
        int result = boardMapper.updateBoard(board);
        
        if(file != null && !file.isEmpty()) {
            String changeName = fileService.store(file);
            String originalName = file.getOriginalFilename();
            String attachmentPath = UPLOAD_PATH + changeName;
            
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

    @Override
    @Transactional
    public int deleteBoard(Long boardNo, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("boardNo", boardNo);
        map.put("userId", userId);
        return boardMapper.deleteBoard(map);
    }

    @Override
    public long getBoardCountForParticipation() {
        return boardMapper.getBoardCountForParticipation();
    }

    /**
     * 파일 첨부 저장 (공통 메서드)
     * insertBoard, updateBoard에서 사용
     */
    private void saveAttachment(Long boardNo, MultipartFile file) {
        if(file == null || file.isEmpty()) return;
        
        String changeName = fileService.store(file);
        String originalName = file.getOriginalFilename();
        String attachmentPath = UPLOAD_PATH + changeName;
        
        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("refBno", boardNo);
        fileMap.put("originName", originalName);
        fileMap.put("changeName", changeName);
        fileMap.put("attachmentPath", attachmentPath);
        
        boardMapper.insertAttachment(fileMap);
    }
}