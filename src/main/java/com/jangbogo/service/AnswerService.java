package com.jangbogo.service;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.service.generic.GenericAnswerService;

public interface AnswerService extends GenericAnswerService<Answer> {
	
	// 답변 생성
	Answer createAnswer(Board board, String content, Member nickName);
	
}
