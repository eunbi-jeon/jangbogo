package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	
}
