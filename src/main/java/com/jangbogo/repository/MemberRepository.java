package com.jangbogo.repository;

import com.jangbogo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNameAndPassword(String name, String pass);
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);

    boolean existsByName(String Name);
    boolean existsByEmail(String email);
}
