package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Board;
import com.jangbogo.domain.Reply;
import com.jangbogo.repository.generic.GenericAnswerRepository;

@Repository
public interface ReplyRepository extends GenericAnswerRepository<Reply> {
		
	Page<Reply> findAllByOrderByParentIdDescDepthAscCreateAtDesc(Pageable pageable);
	
}
