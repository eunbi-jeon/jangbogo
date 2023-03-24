package com.jangbogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Member;
import com.jangbogo.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	/* member의 값을 넣으면 Message엔티티의 필드에서 일치하는 Message들만 List로 묶어주는 메소드 */
	List<Message> findAllByReceiver(Member member);

	List<Message> findAllBySender(Member member);
}
