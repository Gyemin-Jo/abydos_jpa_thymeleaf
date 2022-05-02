package com.jgm.learning.board.model;

import com.jgm.learning.board.dto.BoardRequestDto;
import com.jgm.learning.board.dto.BoardResponseDto;
import com.jgm.learning.board.entity.Board;
import com.jgm.learning.board.entity.BoardRepository;
import com.jgm.learning.exception.CustomException;
import com.jgm.learning.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //클래스 내에 final로 선언된 모든 멤버에 대한 생성자를 만들어줌 lombok
public class BoardService {

    /* 스프링은 생성자로 빈을 주입하는 방식을 권장함.
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    */

    private final BoardRepository boardRepository;

    /**
     * 게시글 생성
     * @param params
     * @return
     */
    @Transactional
    public void save(final BoardRequestDto params){
        boardRepository.save(params.toEntity());
    }

    /**
     * 게시글 조회
     */
    public List<BoardResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createDate");
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void update(final long id, final BoardRequestDto params){
        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.getTitle(), params.getContent(), params.getWriter());
    }

}
