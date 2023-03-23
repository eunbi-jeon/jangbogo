package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {
	
	private final AnswerRepository answerRepository;
	
	// 답변 달기
	@Override
	 public Answer createAnswer(Board board, String content, Member nickName) {
	  
	 Answer answer = new Answer(); 
	 answer.setBoard(board); 
	 answer.setContent(content);
	 answer.setAuthor(nickName); 
	 answer.setCreateAt(LocalDateTime.now());
	  
	 this.answerRepository.save(answer);
	  
	 return answer;
	  
	 }
	
	// 답변 삭제
	@Override
	public void deleteAnswer(Answer answer) {
		this.answerRepository.delete(answer);
	}
	 
	 // 댓글 조회
	@Override
	public Answer getAnswer(Long id) {
		 
		Optional<Answer> answer = this.answerRepository.findById(id);
		 
		return answer.get();
		
	}

	
	// 댓글 수정
	@Override
	public void modifyAnswer(Answer answer, String content) {
		
		answer.setContent(content);
		answer.setModifiedAt(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	// 댓글 삭제
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
	

}
