package com.jangbogo.domain.member;

import com.jangbogo.domain.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MemberImg extends BaseEntity {

    @Id @Column(name = "MemberImg_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;
    private String oriImaName;
    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //FK

    public void updateImg(String oriImaName, String imgName, String imgUrl) {
        this.oriImaName = oriImaName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
