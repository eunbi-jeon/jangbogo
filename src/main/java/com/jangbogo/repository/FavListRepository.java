package com.jangbogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.FavList;
import com.jangbogo.domain.Product;
import com.jangbogo.domain.member.entity.Member;

public interface FavListRepository extends JpaRepository<FavList, Long> {

	Optional<Member> findByMemberAndId(Member user, Long favListId);

	FavList findByUser(Member user);

}
