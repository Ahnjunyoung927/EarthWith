package com.kh.eco.admin.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.eco.admin.model.dao.AdminMapper;
import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminCommentDTO;
import com.kh.eco.admin.model.dto.CommentPageResponse;
import com.kh.eco.admin.model.dto.PageResponse;
import com.kh.eco.exception.PageNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	private final AdminMapper adminMapper;
	private final ElementChecker ec;
	
	/*
	@Override
	public PageResponse<AdminBoardDTO> findBoardAll(int pageNo) {
		ec.checkGreaterThenZero(pageNo);
		RowBounds rb = new RowBounds(pageNo * 5, 5);
		List<AdminBoardDTO> result = adminMapper.findBoardAll(rb);
		int totalCount = adminMapper.countBoardAll();
		if(result == null) {
			throw new PageNotFoundException("유효하지 않은 요청입니다.");
		} else {
			return PageResponse<>(result, totalCount);
		}
	}
	*/

    @Override
    public PageResponse findBoardAll(int pageNo) {
    	ec.checkGreaterThenZero(pageNo);
        RowBounds rb = new RowBounds(pageNo * 5, 5);

        List<AdminBoardDTO> boardList = adminMapper.findBoardAll(rb);
        int totalCount = adminMapper.countBoards();
        if(boardList == null) {
        	throw new PageNotFoundException("조회된 정보가 없습니다.");
        } else {
        	return new PageResponse(boardList, totalCount);
        }
    }
    
    @Override
    public PageResponse findReportedBoard(int pageNo) {
    	ec.checkGreaterThenZero(pageNo);
        RowBounds rb = new RowBounds(pageNo * 5, 5);

        List<AdminBoardDTO> boardList = adminMapper.findReportedBoard(rb);
        int totalCount = adminMapper.countReportedBoards();
        if(boardList == null) {
        	throw new PageNotFoundException("조회된 정보가 없습니다.");
        } else {
        	return new PageResponse(boardList, totalCount);
        }
    }
	
	
	
	@Override
	public CommentPageResponse findCommentAll(int pageNo) {
		ec.checkGreaterThenZero(pageNo);
		RowBounds rb = new RowBounds(pageNo * 5, 5);
		
		List<AdminCommentDTO> commentList = adminMapper.findCommentAll(rb);
		int totalCount = adminMapper.countComments();
		
		if(commentList == null) {
        	throw new PageNotFoundException("조회된 정보가 없습니다.");
        } else {
        	return new CommentPageResponse(commentList, totalCount);
        }
	}
	
    @Override
    public CommentPageResponse findReportedComment(int pageNo) {
    	ec.checkGreaterThenZero(pageNo);
        RowBounds rb = new RowBounds(pageNo * 5, 5);

        List<AdminCommentDTO> commentList = adminMapper.findReportedComment(rb);
        int totalCount = adminMapper.countReportedComments();
        if(commentList == null) {
        	throw new PageNotFoundException("조회된 정보가 없습니다.");
        } else {
        	return new CommentPageResponse(commentList, totalCount);
        }
    }

	@Override
	public AdminBoardDTO findByBoardNo(Long boardNo) {
		return null;
	}

	@Override
	public AdminCommentDTO findByCommentNo(Long commentNo) {
		return null;
	}

	@Override
	public void deleteBoard(Long boardNo) {
		ec.checkGreaterThenZero(boardNo.intValue());
		
	}

	
	

}
