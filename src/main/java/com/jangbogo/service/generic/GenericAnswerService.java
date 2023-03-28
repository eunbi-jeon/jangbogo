package com.jangbogo.service.generic;

import com.jangbogo.domain.baseEntity.BaseAnswer;

public interface GenericAnswerService <T extends BaseAnswer>{
	
	
	// 상세 조회
	T getAnswer(Long id);
	
	// 수정
	void modify(T answer, String content);
	
	// 삭제
	void delete(Long id);

}
