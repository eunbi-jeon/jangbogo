package com.jangbogo.repository;

import com.jangbogo.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Board.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	Page<Answer> findAllByOrderByParentIdDescDepthAscCreateAtDesc(Pageable pageable);
	List<Answer> findByName(Member member);


}
