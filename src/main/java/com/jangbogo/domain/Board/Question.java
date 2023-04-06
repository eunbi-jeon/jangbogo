package com.jangbogo.domain.Board;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(length = 4000)
    private String content;
    
 //   private String region;
    
//    private int readCount;
    
<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
=======
    @ManyToOne(fetch = FetchType.EAGER)
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
	@JoinColumn(name = "board_id")
    private Board board;
    
    @ManyToOne
    private Member name;
    
    @ManyToMany
    Set<Member> voter;
    
	@OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)
<<<<<<< HEAD
=======
	@JsonManagedReference
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
	private List<Answer> answerList; 
	
	// 신고
	@ManyToMany
	Set<Member> report;
    
    
}
