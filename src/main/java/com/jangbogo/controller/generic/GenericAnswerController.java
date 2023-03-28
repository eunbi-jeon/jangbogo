package com.jangbogo.controller.generic;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.jangbogo.domain.baseEntity.BaseAnswer;
import com.jangbogo.dto.AnswerDto;
import com.jangbogo.service.generic.GenericAnswerService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class GenericAnswerController <T extends BaseAnswer> {
	
	private final GenericAnswerService<T> genericAnswerService;
	
	//댓글수정 댓글 수정 form 으로 보냄
    @GetMapping("/answer/modify/{id}")
    public String modifyAnswer(AnswerDto answerDto, @PathVariable("id") Long id, Principal principal) {
        
    	T answer = this.genericAnswerService.getAnswer(id);
    	
        if (!answer.getNickName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerDto.setContent(answer.getContent());
        return "reply_form";
    }
    
    // 댓글수정 처리
    @PostMapping("/answer/modify/{id}")
    	public String answerModify(@Valid AnswerDto answerDto, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
    		if(bindingResult.hasErrors()) {
    			return "answer_form";
    		}
    		T answer = this.genericAnswerService.getAnswer(id);
    		if(!answer.getNickName().equals(principal.getName())) {
    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    		}
    		this.genericAnswerService.modify(answer, answerDto.getContent());
    		
    		return String.format("redirect:/board/detail/%s#answer_%s", answer.getBoard().getId(), answer.getId());
    	}
    
    //댓글 삭제 
	 @GetMapping("/answer/delete/{id}") 
	 public String answerDelete(Principal principal, @PathVariable("id") Long id) {
	 
	 T answer = this.genericAnswerService.getAnswer(id);
	 
	 if (!answer.getNickName().equals(principal.getName())) { throw
	 new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다."); }
	 
	 this.genericAnswerService.delete(id);
	 
	 return String.format("redirect:/board/detail/%s", answer.getBoard().getId());
	 
	 }
    
	
}
