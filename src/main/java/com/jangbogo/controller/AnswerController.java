package com.jangbogo.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jangbogo.domain.Board.Answer;
import com.jangbogo.domain.Board.Board;
import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.AnswerDto;
import com.jangbogo.service.AnswerService;
import com.jangbogo.service.MemberService;
import com.jangbogo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {
	private final QuestionService questionService;
	private final AnswerService answerService; 
	private final MemberService memberService;

	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Long id, @Valid
 			AnswerDto answerDto, BindingResult bindingResult, Principal principal,
 			@RequestParam(value = "parentId") Long parentId){
 		
	    Question question = questionService.getQuestion(id);
	    Member member = memberService.getMember(principal.getName());
	    
	    if (parentId == null) { // 댓글 생성
	        Answer answer = answerService.create(question, answerDto.getContent(), member);
	        return String.format("redirect:/board/detail/%s#answer%s", answer.getQuestion().getId(), answer.getId());
	    } else { 
	    	// 대댓글 생성
	    }   Answer parentReply = answerService.getAnswer(parentId);
	        answerService.createChildReply(question, parentId, answerDto.getContent(), member);
	        return String.format("redirect:/board/detail/%s#answer%s", parentReply.getQuestion().getId(), parentReply.getId());
	    }
		
	//답변수정 
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerDto answerDto, @PathVariable("id") Long id, Principal principal) {
        
    	Answer answer = this.answerService.getAnswer(id);
    	
        if (!answer.getName().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerDto.setContent(answer.getContent());
        return "answer_form";
    }
    
    
    //답변 수정
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerDto answerDto, BindingResult bindingResult,
            @PathVariable("id") Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getName().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerDto.getContent());

        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }
    
    //답변 삭제 
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Long id) {
    	
        Answer answer = this.answerService.getAnswer(id);
        
        if (!answer.getName().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        
        this.answerService.delete(answer);
        
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswer(id);
        Member member = this.memberService.getMember(principal.getName());
        this.answerService.vote(answer, member);

        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }
    
    @GetMapping("/report/{id}")
    public String answerReport(Principal principal, @PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswer(id);
        Member member = this.memberService.getMember(principal.getName());
        this.answerService.report(answer, member);

        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }

}
