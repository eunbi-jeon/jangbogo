package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jangbogo.exeption.DataNotFoundException;
import com.jangbogo.domain.Board.Answer;
import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository; 

	
	// 댓글 생성
	public Answer create(Question question, String content, Member name) {	
		 
		Answer answer = new Answer(); 
		answer.setQuestion(question);
		answer.setContent(content);
		answer.setCreateAt(LocalDateTime.now());
		answer.setName(name);
		answer.setDepth(0);
		answer.setParentId(null);
		
		
		this.answerRepository.save(answer); 
		
		return answer; 
	}
	
	// 대댓글 생성
	public Answer createChildReply(Question question, Long parentId, String content, Member name) {
		
		Answer parentReply = answerRepository.findById(parentId).orElseThrow(
	            () -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
		
		Answer childReply = new Answer();
		childReply.setQuestion(question);
		childReply.setContent(content);
		childReply.setName(name);
		childReply.setCreateAt(LocalDateTime.now());
		childReply.setDepth(parentReply.getDepth() + 1);
		childReply.setParentId(parentReply);
		
		return answerRepository.save(childReply);
	}
	
	
	//2월 16일 수정 (답변 조회 , 답변 수정 )
    public Answer getAnswer(Long id) {
    	
        Optional<Answer> answer = this.answerRepository.findById(id);
        
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    
    //답변수정 
    public void modify(Answer answer, String content) {
    	
    	//
    	System.out.println("기존의 답변을 수정함 ");
    	
        answer.setContent(content);
        answer.setModifiedAt(LocalDateTime.now());
        this.answerRepository.save(answer);
        
    }
	
    
    //답변 삭제 
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    
    //답변의 추천기능
    public void vote(Answer answer, Member name) {
        answer.getVoter().add(name);
        this.answerRepository.save(answer);
    }
    
    //답변의 신고기능
    public void report(Answer answer, Member name) {
        answer.getReport().add(name);
        this.answerRepository.save(answer);
    }
    
    public Page<Answer> getList(int page){
    	List<Sort.Order> sorts = new ArrayList();
    	sorts.add(Sort.Order.desc("createAt"));
    	
    	Pageable pageable =PageRequest.of(page, 10, Sort.by(sorts));
    	
    	return this.answerRepository.findAllByOrderByParentIdDescDepthAscCreateAtDesc(pageable);
    }

}
