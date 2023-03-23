package com.jangbogo.service;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;

public interface AnswerService {
	
	// 답변 생성
	Answer createAnswer(Board board, String content, Member nickName);
	
	// 답변 삭제
	void deleteAnswer(Answer answer);
	
	// 답변 조회
	Answer getAnswer(Long id);
	
	// 답변 수정
	void modifyAnswer(Answer answer, String content);
	
}
