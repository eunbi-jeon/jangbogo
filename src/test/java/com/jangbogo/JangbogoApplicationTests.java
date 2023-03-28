package com.jangbogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jangbogo.domain.Answer;
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
	
//	@Test
//	void getAnswer() {
//		
//		Answer answer = new Answer();
//		
//        answer.setId(1L);
//        answer.setContent("test content");
//        answer.setCreateAt(LocalDateTime.now());
//        answerRepository.save(answer);
//        
//        Answer result = answerServiceImpl.getAnswer(1L);
//	}
	
//	@Test
//	void getReply() {
//		
//		Reply reply = new Reply();
//		
//		reply.setId(1L);
//		reply.setContent("test content");
//		reply.setCreateAt(LocalDateTime.now());
//        replyRepository.save(reply);
//        
//        Reply result = replyServiceImpl.getAnswer(1L);
//	}
	

//	@Test
//	void modify() {
//
//		Answer answer = answerRepository.findById(2L).orElseThrow();
//
//		String modifyContent = "하하호호";
//		
//		answer.setContent(modifyContent);
//	    answer.setCreateAt(LocalDateTime.now());
//	    answerRepository.save(answer);
//
//	}
	
//	@Test
//	void modify() {
//
//		Reply reply = replyRepository.findById(2L).orElse(null);
//		
//		String modifyContent = "히히흐흐";
//		
//		reply.setContent(modifyContent);
//		reply.setModifiedAt(LocalDateTime.now());
//		replyRepository.save(reply);
//	}
	

//	@Test
//	 void delete() {
//		
//		Answer answer = answerRepository.findById(2L).orElseThrow();
//		
//		answerRepository.deleteById(2L);
//	}
	
//	@Test
//	 void delete() {
//		
//		Reply reply = replyRepository.findById(2L).orElseThrow();
//		
//		replyRepository.deleteById(2L);
//	}
	
//	@Test
//	void createChildReply() {
//		
//		Reply parentReply = replyRepository.findById(10L).orElseThrow();
//		
//		Reply childReply = new Reply();
//		
//		childReply.setBoard(parentReply.getBoard());  // 부모 댓글이 속한 게시판을 가져옵니다.
//	    childReply.setContent("14 - 1");  // 새로운 자식 댓글의 내용을 지정합니다.
//	    childReply.setCreateAt(LocalDateTime.now());  // 작성 시간을 현재 시간으로 지정합니다.
//	    childReply.setReport(0);  // 신고 횟수를 0으로 지정합니다.
//	    childReply.setDepth(parentReply.getDepth() + 1);  // 부모 댓글의 depth에 +1을 합니다.
//	    childReply.setParent(parentReply);  // 부모 댓글을 자식 댓글의 부모로 지정합니다.
//	    
//	    replyRepository.save(childReply);
//	}
	
    @Test
    public void testFindAllByOrderByParentIdDescDepthAscCreatedAtDesc() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createAt").descending());
        
        // When
        Page<Reply> replyPage = replyRepository.findAllByOrderByParentIdDescDepthAscCreateAtDesc(pageable);
        
        // Then
        Assertions.assertNotNull(replyPage);
        Assertions.assertEquals(10, replyPage.getSize());
        Assertions.assertEquals(0, replyPage.getNumber());
        Assertions.assertTrue(replyPage.isFirst());
        Assertions.assertFalse(replyPage.isLast());
        // TODO: Add more assertions to check the content of the page
    }
    	
}
