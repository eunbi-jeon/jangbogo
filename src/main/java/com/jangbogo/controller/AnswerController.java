//package com.jangbogo.controller;
//
//import java.security.Principal;
//
//import javax.validation.Valid;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.jangbogo.controller.generic.GenericAnswerController;
//import com.jangbogo.domain.Answer;
//import com.jangbogo.domain.Board;
//import com.jangbogo.domain.Member;
//import com.jangbogo.dto.AnswerDto;
//import com.jangbogo.service.AnswerService;
//import com.jangbogo.service.BoardService;
//import com.jangbogo.service.MemberService;
//import com.jangbogo.service.generic.GenericAnswerService;
//
//@Controller
//public class AnswerController extends GenericAnswerController {
//
//	private final AnswerService answerService;
//	private final BoardService boardService;
//	private final MemberService memberService;
//	
//	
//    public AnswerController(AnswerService answerService,
//            BoardService boardService,
//            MemberService memberService,
//            GenericAnswerService<Answer> genericAnswerService) {
//				super(genericAnswerService);
//				this.answerService = answerService;
//				this.boardService = boardService;
//				this.memberService = memberService;
//    }
//
//	// 답변 달기
//	@PostMapping("/answer/create/{id}")
// 	public String createReply(Model model, @PathVariable("id") Long id, @Valid
// 			AnswerDto answerDto, BindingResult bindingResult, Principal principal){
// 		
// 		Board board = this.boardService.getBoard(id);
// 		Member member = this.memberService.getMember(principal.getName());
// 		
// 		if(bindingResult.hasErrors()) {
// 			model.addAttribute("board", board);
// 			return "board_detail";
// 		}
//		
//		Answer answer = this.answerService.createAnswer(board, answerDto.getContent(), member);
//		return String.format("redirect:/board/detail/%s#reply_%s", answer.getBoard().getId(), answer.getId());
// 	}
// 
//    
//}
