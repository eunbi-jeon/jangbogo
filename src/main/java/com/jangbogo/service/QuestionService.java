package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
<<<<<<< HEAD
=======
import org.springframework.http.ResponseEntity;
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
import org.springframework.stereotype.Service;

import com.jangbogo.exeption.DataNotFoundException;
import com.jangbogo.domain.Board.Answer;
import com.jangbogo.domain.Board.Board;
import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

<<<<<<< HEAD
	private final QuestionRepository questionRepository; 
=======
	private final QuestionRepository questionRepository;

    //내가 쓴 글 조회
    public ResponseEntity<List<Question>> getMyBoard(Member member){
        List<Question> myboard = questionRepository.findByName(member);
        return ResponseEntity.ok(myboard);
    }
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
	
	 // 금지어 리스트
    private static final List<String> PROFANITY_LIST = Arrays.asList("욕설1", "욕설2", "욕설3");
    
    // 욕설 필터링 메서드
    private boolean isProfanity(String text) {
        for (String profanity : PROFANITY_LIST) {
            if (text.contains(profanity)) {
                return true;
            }
        }
        return false;
    }
	
	// 페이징
	//public Page<Question> getList(int page, String kw) {	
    public Page<Question> getList(int page) {	
		List<Sort.Order> sorts = new ArrayList(); 
		sorts.add(Sort.Order.desc("createAt")); 
	
		Pageable pageable = PageRequest.of(page, 10 , Sort.by(sorts)); 
		
		//Specification<Question> spec = search(kw);
		 
		//return this.questionRepository.findAll(spec, pageable); 
		return this.questionRepository.findAll( pageable); 
		
	}
	
	// 한개 조회
	public Question getQuestion(Long id) {
		
		Optional<Question> op = this.questionRepository.findById(id) ; 
		if ( op.isPresent()) {
			return op.get();
		}else {
			throw new DataNotFoundException("요청한 파일을 찾지 못했습니다. "); 
<<<<<<< HEAD
		}		
=======
		}		 
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
	}
	
	// 생성
	public void create(Board board, String subject, String content, Member name) {
		
<<<<<<< HEAD
		// 욕설 필터링
        if (isProfanity(subject) || isProfanity(content)) {
            // 욕설이 포함된 제목이나 내용을 입력한 경우 예외를 던지거나 다른 처리를 할 수 있습니다.
            throw new IllegalArgumentException("금지어가 포함된 제목이나 내용입니다.");
        }
=======
//		// 욕설 필터링
//        if (isProfanity(subject) || isProfanity(content)) {
//            // 욕설이 포함된 제목이나 내용을 입력한 경우 예외를 던지거나 다른 처리를 할 수 있습니다.
//            throw new IllegalArgumentException("금지어가 포함된 제목이나 내용입니다.");
//        }
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333

		Question q = new Question();
		q.setBoard(board);
		q.setSubject(subject);
		q.setContent(content);
	//	q.setReadCount(0);
		q.setCreateAt(LocalDateTime.now());
		q.setName(name);

		this.questionRepository.save(q);     
		
	}
	
	// 수정
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifiedAt(LocalDateTime.now());

        this.questionRepository.save(question);
    }
    
    // 삭제
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    
    // 추천
    public void vote(Question question, Member name) {
    	question.getVoter().add(name);
        this.questionRepository.save(question);
    }
    
    // 신고
    public void report(Question question, Member name) {
    	question.getReport().add(name);
    	this.questionRepository.save(question);
    }
	
    // 검색기능
    private Specification<Question> search(final String kw) {
    	
        return new Specification<>() {
        	
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
            	
                query.distinct(true);  // 중복을 제거 
                Join<Question, Member> u1 = q.join("name", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, Member> u2 = a.join("name", JoinType.LEFT);

                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
//                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"));      // 답변 내용 
//                        cb.like(u2.get("username"), "%" + kw + "%"))   // 답변 작성자 
            }
        };
    }
	
}
