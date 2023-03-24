package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Reply;
import com.jangbogo.repository.generic.GenericAnswerRepository;

public interface ReplyRepository extends GenericAnswerRepository<Reply> {
	
	
}
