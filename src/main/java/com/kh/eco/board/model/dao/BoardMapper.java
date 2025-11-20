package com.kh.eco.board.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	long getBoardCountForParticipation ();

}
