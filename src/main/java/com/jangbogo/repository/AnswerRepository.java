package com.jangbogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Answer;
import com.jangbogo.repository.generic.GenericAnswerRepository;

public interface AnswerRepository extends GenericAnswerRepository<Answer> {

}
