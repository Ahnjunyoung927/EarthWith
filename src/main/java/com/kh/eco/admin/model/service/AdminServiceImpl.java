package com.kh.eco.admin.model.service;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.eco.admin.model.dao.AdminMapper;
import com.kh.eco.admin.model.dto.AdminBoardDTO;
import com.kh.eco.admin.model.dto.AdminCommentDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
	private final AdminMapper adminMapper;
	private ElementChecker ec;
	
	
	@Override
	public List<AdminBoardDTO> findBoardAll(int pageNo) {
		ec.checkGreaterThenZero(pageNo);
		RowBounds rb = new RowBounds(pageNo * 5, 5);
		return adminMapper.findBoardAll(rb);
	}
	
	@Override
	public List<AdminCommentDTO> findCommentAll(int pageNo) {
		ec.checkGreaterThenZero(pageNo);
		RowBounds rb = new RowBounds(pageNo % 5, 5);
		return adminMapper.findCommentAll(rb);
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
