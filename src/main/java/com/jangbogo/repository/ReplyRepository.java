package com.jangbogo.repository;

import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Reply;
import com.jangbogo.repository.generic.GenericAnswerRepository;

@Repository
public interface ReplyRepository extends GenericAnswerRepository<Reply> {
	
	
}
