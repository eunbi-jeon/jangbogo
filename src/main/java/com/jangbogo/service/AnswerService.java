package com.jangbogo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	
	// 답변 달기
	 public Answer reply(Board board, String content, Member nickName) {
	  
	 Answer a = new Answer(); 
	 a.setBoard(board); 
	 a.setContent(content);
	 a.setNickName(nickName); 
	 a.setCreateAt(LocalDateTime.now());
	  
	 this.answerRepository.save(a);
	  
	 return a;
	  
	 }

	
	// 답변 수정
	public void answerModify(Answer a, String content) {
		
		a.setContent(content);
		a.setModifiedAt(LocalDateTime.now());
		this.answerRepository.save(a);
	}
	
	// 답변 삭제
	public void answerDelete(Answer a) {
		this.answerRepository.delete(a);
	}
	
	// 답변 신고
	public void report(Answer a, Member m) {
		a.getReport().add(m);
		this.answerRepository.save(a);
	}

}
