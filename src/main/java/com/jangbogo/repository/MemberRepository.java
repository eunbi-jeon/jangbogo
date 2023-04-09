package com.jangbogo.repository;

import com.jangbogo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNameAndPassword(String name, String pass);
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);

    boolean existsByName(String Name);
    boolean existsByEmail(String email);

    //회원 닉네임 검색
    @Query("SELECT m FROM Member m WHERE m.name LIKE %:name%")
    List<Member> findByNameContaining(@Param("name") String name);

}
