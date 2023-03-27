package com.jangbogo.repository;

import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Answer;
import com.jangbogo.repository.generic.GenericAnswerRepository;

@Repository
public interface AnswerRepository extends GenericAnswerRepository<Answer> {

}
