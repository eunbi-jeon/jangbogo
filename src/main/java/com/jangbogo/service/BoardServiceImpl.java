package com.jangbogo.service;

import com.jangbogo.domain.Board;
import com.jangbogo.service.generic.GenericBoardService;
import com.jangbogo.service.generic.GenericBoardServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl extends GenericBoardServiceImpl<Board> implements BoardService{
	
}