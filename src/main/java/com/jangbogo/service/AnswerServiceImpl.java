package com.jangbogo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.service.generic.GenericAnswerServiceImpl;

@Service
public class AnswerServiceImpl extends GenericAnswerServiceImpl<Answer> implements AnswerService {
	
	private final AnswerRepository answerRepository;
	 
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        super(answerRepository);
        this.answerRepository = answerRepository;
    }
	
	// 답변 달기
	@Override
	 public Answer createAnswer(Board board, String content, Member nickName) {
	  
	 Answer answer = new Answer(); 
	 answer.setBoard(board); 
	 answer.setContent(content);
	 answer.setNickName(nickName); 
	 answer.setCreateAt(LocalDateTime.now());
	  
	 this.answerRepository.save(answer);
	  
	 return answer;
	  
	 }
}
	


