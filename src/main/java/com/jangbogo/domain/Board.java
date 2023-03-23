package com.jangbogo.domain;

import com.jangbogo.domain.baseEntity.BaseBoard;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name="board")
public class Board extends BaseBoard {


    private String nickname;
}