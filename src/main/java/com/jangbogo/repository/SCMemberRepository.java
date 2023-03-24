package com.jangbogo.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.Member;

public interface SCMemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findById(Long id);
}
