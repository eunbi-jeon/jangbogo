package com.jangbogo.service.generic;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.jangbogo.DataNotFoundException;
import com.jangbogo.domain.baseEntity.BaseAnswer;
import com.jangbogo.repository.ReplyRepository;
import com.jangbogo.repository.generic.GenericAnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenericAnswerServiceImpl <T extends BaseAnswer> implements GenericAnswerService<T> {
	
	private final GenericAnswerRepository<T> genericAnswerRepository;
	
	// 조회
	@Override
	public T getAnswer(Long id) {
		Optional<T> answer = genericAnswerRepository.findById(id);
		
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("파일이 없습니다.");
		}
	}
	
	// 수정
	@Override
	public void modify(T answer, String content) {
		answer.setContent(content);
		answer.setModifiedAt(LocalDateTime.now());
		genericAnswerRepository.save(answer);
	}
	
	// 삭제
	@Override
	public void delete(Long id) {
		genericAnswerRepository.deleteById(id);
	}

}
