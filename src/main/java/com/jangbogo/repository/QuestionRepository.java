package com.jangbogo.repository;

import java.util.List;

import com.jangbogo.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Board.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	Question findBySubject(String subject);    

	Question findByContent(String content);


	List<Question> findByName(Member member);

	List<Question> findByContentLike(String content); 
	
	List<Question> findBySubjectLikeOrContentLike (String subject, String content); 
	
	List<Question> findBySubjectLikeOrderByCreateAtAsc(String subject);
	List<Question> findBySubjectLikeOrderByCreateAtDesc(String subject);

	List <Question> findAllByOrderByCreateAtAsc(); 
	List <Question> findAllByOrderByCreateAtDesc();
	
//    List<Question> findBySubjectLikeOrderByReadCountDesc(String subject);
//
//    Page<Question> findAllByOrderByReadCountDesc(Pageable pageable);
//
//    Page<Question> findAllByKeywordOrderByReadCountDesc(@Param("kw") String kw, Pageable pageable);
	
	Page<Question> findAll(Pageable pageable); 
	
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    @Query("select "
            + "distinct q "
            + "from Question q " 
            + "left outer join Member u1 on q.name=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join Member u2 on a.name=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.name like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.name like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

}
