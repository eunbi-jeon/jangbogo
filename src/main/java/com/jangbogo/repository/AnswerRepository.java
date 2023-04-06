package com.jangbogo.repository;

<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jangbogo.domain.Board.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
<<<<<<< HEAD
	
=======
	Page<Answer> findAllByOrderByParentIdDescDepthAscCreateAtDesc(Pageable pageable);
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333

}
