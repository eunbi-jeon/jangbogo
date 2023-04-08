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

import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.repository.MemberRepository;

import com.jangbogo.repository.BoardRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

	private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    
    
    //내가 쓴 글 조회
    public ResponseEntity<List<Question>> getMyBoard(UserPrincipal userPrincipal){
        Member member = memberRepository.findByEmail(userPrincipal.getEmail()).orElse(null);
        List<Question> myboard = questionRepository.findByName(member);
        return ResponseEntity.ok(myboard);
    }

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
    public Page<Question> getList(Long board_id,String region,int page) {
        List<Sort.Order> sorts = new ArrayList();
        sorts.add(Sort.Order.desc("createAt"));

        Pageable pageable = PageRequest.of(page, 10 , Sort.by(sorts));

        //Specification<Question> spec = search(kw);

        //return this.questionRepository.findAll(spec, pageable);
        if(!region.equals("undefined")){
            return this.questionRepository.findByBoardIdAndRegion(board_id,region, pageable);
        }else {
            return this.questionRepository.findByBoardId(board_id, pageable);
        }

    }
	
	// 한개 조회
	public Question getQuestion(Long id) {
		
		Optional<Question> op = this.questionRepository.findById(id) ; 
		if ( op.isPresent()) {
			return op.get();
		}else {
			throw new DataNotFoundException("요청한 파일을 찾지 못했습니다. "); 
		}		 
	}
	
	public void create(Long board_id, String region,String subject, String content, Member name) {
        Board board;
        Optional<Board> ob= boardRepository.findById(board_id);
        if(ob.isPresent()){
            board=ob.get();
        }else{
            throw new DataNotFoundException("요청한 파일을 찾지 못했습니다. ");
        }
        System.out.println("서비스에서 board_id확인:"+board_id);
        System.out.println("서비스에서 board_id확인:"+board.getId());
		Question q = new Question();
		q.setBoard(board);
		q.setSubject(subject);
		q.setContent(content);
		q.setRegion(region);
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
    	if(question.getVoter().contains(name)) {
    		question.getVoter().remove(name);
    	}else {
    		question.getVoter().add(name);
    	}
        this.questionRepository.save(question);
    }
    
    // 신고
    public void report(Question question, Member name) {
    	question.getReport().add(name);
    	this.questionRepository.save(question);
    }
    
    // 조회수
    public void incrementReadCount(Long id) {
        Question question = findById(id);
        
        question.setReadCount(question.getReadCount() + 1);
        save(question);
    }
    
    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
    public Question save(Question question) {
        return questionRepository.save(question);
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
