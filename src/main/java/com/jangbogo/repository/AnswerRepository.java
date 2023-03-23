package com.jangbogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
}
