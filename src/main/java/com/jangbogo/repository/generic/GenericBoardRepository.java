package com.jangbogo.repository.generic;

import com.jangbogo.domain.baseEntity.BaseBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericBoardRepository<T extends BaseBoard> extends JpaRepository<T,Long> {
	
}