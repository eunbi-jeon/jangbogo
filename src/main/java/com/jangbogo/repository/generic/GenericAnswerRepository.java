package com.jangbogo.repository.generic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.baseEntity.BaseAnswer;

public interface GenericAnswerRepository <T extends BaseAnswer> extends JpaRepository<T, Long> {
	
	Optional<T> findById(Long id);
	
}
