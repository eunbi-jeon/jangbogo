package com.jangbogo.repository;

import com.jangbogo.domain.Board;
import com.jangbogo.repository.generic.GenericBoardRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends GenericBoardRepository<Board> {
	
}