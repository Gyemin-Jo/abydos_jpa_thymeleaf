package com.jgm.learning.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * JPA 레파지토리(Repository) 인터페이스,  CRUD 쿼리를 자동으로 생성
 * Mybatis Mapper와 유사한 개념
 * Entity 클래스와 Repository 인터페이스는 같은 패키지에 위치
 * <Board, Long> 클래스의 타입 Board와 PK에 해당하는 데이터 타입 long을 선언하면 매핑되는 테이블 Board 테이블의 CURD 사용 가능
 */
public interface BoardRepository extends JpaRepository<Board, Long> { 
}
