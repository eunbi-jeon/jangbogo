package com.jangbogo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.repository.BoardRepository;
import com.jangbogo.repository.ReplyRepository;
import com.jangbogo.service.AnswerServiceImpl;
import com.jangbogo.service.ReplyServiceImpl;

@SpringBootTest
class JangbogoApplicationTests {

	@Autowired
	AnswerServiceImpl answerServiceImpl;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	ReplyServiceImpl replyServiceImpl;
	
	@Autowired
	ReplyRepository replyRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	
	

//	@Test
//	void testCreateReply() {
//		 
//		Reply reply = new Reply();
//		
//		reply.setId(1L);
//
//		reply.setContent("test 내용");
//		reply.setCreateAt(LocalDateTime.now());
//		reply.setReport(0);
//		reply.setDepth(0);
//		
//		replyRepository.save(reply);
//	 }
	
//	@Test
//	void testCreateAnswer() {
//		
//		Answer answer = new Answer();
//		
//		answer.setId(1L);
//		
//		answer.setContent("test 내용");
//		answer.setCreateAt(LocalDateTime.now());
//		
//		answerRepository.save(answer);
//	}
	
	// 자식 댓글 생성
//	  @Test
//	    public void createChildReplyTest() {
//		  
//	        // Given
//	        Member author = new Member("John");
//	        Board board = new Board("Test Board");
//	        Reply parentReply = new Reply("Parent Reply Content", author, board);
//	        parentReply = entityManager.persist(parentReply);
//	        entityManager.flush();
//	        
//	        // When
//	        Reply childReply = replyService.createChildReply(board, parentReply.getId(), "Child Reply Content", author);
//	        
//	        // Then
//	        assertThat(childReply.getParent()).isEqualTo(parentReply);
//	        assertThat(childReply.getDepth()).isEqualTo(parentReply.getDepth() + 1);
//	        assertThat(replyRepository.count()).isEqualTo(2);
//	    }

}
