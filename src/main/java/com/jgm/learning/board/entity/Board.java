package com.jgm.learning.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity //테이블 그 자체
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    private int hits;

    private char deleteYn;

    private LocalDateTime createDate = LocalDateTime.now();

    private LocalDateTime modifiedDate;

    @Builder //롬복 생성자
    public Board(String title, String content, String writer, int hits, char deleteYn){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }

    public void update(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
