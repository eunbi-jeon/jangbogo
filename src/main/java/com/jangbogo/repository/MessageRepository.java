package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.DirectMessage;
import com.jangbogo.domain.member.entity.Member;

public interface MessageRepository extends JpaRepository<DirectMessage, Long> {
    List<DirectMessage> findAllByReceiverAndDeletedByReceiverFalseOrderByIdDesc(Member member);
    List<DirectMessage> findAllBySenderAndDeletedBySenderFalseOrderByIdDesc(Member member);
}