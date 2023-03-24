package com.jangbogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Member;

@Repository
public interface MemberMSRepository extends JpaRepository<Member, Long> {

//	Optional<Member> findByid(Long id);
	Member findByid(Long id);
	Member findByNickName(String nickName);

}
