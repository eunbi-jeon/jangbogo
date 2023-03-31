package com.jangbogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.SCMember;

@Repository
public interface SCMemberRepository extends JpaRepository<SCMember, Long> {
	
	SCMember findByEmail(String email);
}
