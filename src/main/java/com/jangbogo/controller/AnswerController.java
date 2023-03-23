package com.jangbogo.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import com.jangbogo.domain.AnswerForm;
import com.jangbogo.domain.Board;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.service.BoardService;
import com.jangbogo.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AnswerController {
	
private final BoardService boardservice;
private final AnswerRepository answerrepository;
private final MemberService memberservice;
	
//	public String reply(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal){
		
//		Board b = this.boardservice.
		
		
	
	}	
