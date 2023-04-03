package com.jangbogo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jangbogo.domain.Board.Answer;
import com.jangbogo.domain.Board.Board;
import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.repository.BoardRepository;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.QuestionRepository;
import com.jangbogo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
class JangbogoApplicationTests {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
//	
//	@Test
//	void createWithProfanity() {
//	    // Given
//	    Board board = new Board();
//	    board.setId(1L);
//
//	    String subject = "욕설 포함 제목";
//	    String content = "욕설을 포함한 내용입니다.";
//
//	    Member member = new Member();
//	    member.setId(8L);
//
//	    // When & Then
//	    assertThrows(IllegalArgumentException.class, () -> questionService.create(board, subject, content, member));
//	}

//	@Test
//	void createWithoutProfanity() {
//	    // given
//	    Board board = new Board();
//	    board.setName("test");
//
//	    String subject = "우아한테크코스 지원합니다.";
//	    String content = "학력/경력 사항 등 구체적으로 작성합니다.";
//
//	    Member name = new Member();
//	    name.setUsername("testuser");
//
//	    // when
//	    questionService.create(board, subject, content, name);
//
//	    // then
//	    // 테스트 코드에서 별도의 검증이 필요하지 않다면 해당 부분은 생략해도 됩니다.
//	    // 예를 들어, 해당 메서드가 RuntimeException 등을 던지지 않고 정상적으로 실행될 경우에는,
//	    // 별도의 검증이 필요하지 않기 때문입니다.
//	}
	

	
//	@Test
//	void deleteQuestion() {
//
//	    Question question = questionRepository.findById(5L).orElse(null);
//	    questionRepository.deleteById(5L);
//
//	}
	
//	@Test
//	void modifyQuestion() {
//
//	    Question question = questionRepository.findById(3L).orElse(null);
//	    question.setSubject("modify");
//	    question.setContent("modify");
//	    question = questionRepository.save(question);
//
//	}
	
//	@Test
//	void getQuestion() {
//		
//		Question question = new Question();
//		
//		question.setId(3L);
//		question.setContent("test content");
//		question.setCreateAt(LocalDateTime.now());
//        
//        Question result = questionService.getQuestion(3L);
//	}
	
//    @Test
//    public void createQuestionTest() {
//
//        Board board = boardRepository.findById(1L).orElse(null);
//        Member member = memberReposotory.findById(2L).orElse(null);
//
//        Question question = new Question();
//        question.setBoard(board);
//        question.setName(member);
//        question.setSubject("테스트입니당.");
//        question.setContent("테스트입니다.");
//        question.setName(member);
//
//        questionService.create(question.getBoard(), question.getSubject(), question.getContent(), question.getName());
//
//        // TODO: add assertion statements to verify the result of the create method
//    }

//	@Test
//	void testCreateReply() {
//		
//		Question question = questionRepository.findById(3L).orElse(null);
//		 
//		Answer answer = new Answer();
//		
//		answer.setQuestion(question);
//		answer.setContent("부모 댓글");
//		answer.setCreateAt(LocalDateTime.now());
//		answer.setReport(null);
//		answer.setDepth(0);
//		
//		answerRepository.save(answer);
//	 }
	
//	@Test
//	void createChildReply() {
//		
//		Answer parentReply = answerRepository.findById(1L).orElseThrow();
//		
//		Answer childReply = new Answer();
//		
//		childReply.setQuestion(parentReply.getQuestion());  // 부모 댓글이 속한 게시판을 가져옵니다.
//	    childReply.setContent("1 - 2 - 1");  // 새로운 자식 댓글의 내용을 지정합니다.
//	    childReply.setCreateAt(LocalDateTime.now());  // 작성 시간을 현재 시간으로 지정합니다.
//	    childReply.setReport(null);  // 신고 횟수를 0으로 지정합니다.
//	    childReply.setDepth(parentReply.getDepth() + 2);  // 부모 댓글의 depth에 +1을 합니다.
//	    childReply.setParent(parentReply);  // 부모 댓글을 자식 댓글의 부모로 지정합니다.
//	    
//	    answerRepository.save(childReply);
//	}
	
//	@Test
//	void deleteAnswer() {
//
//	    Answer answer = answerRepository.findById(4L).orElse(null);
//	    answerRepository.deleteById(4L);
//
//	}
	
//	@Test
//	void modifyAnswer() {
//
//		Answer answer = answerRepository.findById(3L).orElse(null);
//		answer.setContent("modify");
//		answer = answerRepository.save(answer);
//
//	}
	

    	
}
