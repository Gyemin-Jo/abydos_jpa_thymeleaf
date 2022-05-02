package com.jgm.learning.board.controller;

import com.jgm.learning.board.dto.BoardRequestDto;
import com.jgm.learning.board.dto.BoardResponseDto;
import com.jgm.learning.board.entity.Board;
import com.jgm.learning.board.entity.BoardRepository;
import com.jgm.learning.board.model.BoardService;
import com.jgm.learning.exception.CustomException;
import com.jgm.learning.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 저장
     */
    @PostMapping("/insertboard")
    public String save(@RequestBody final BoardRequestDto params){
        boardService.save(params);

        return "sendMessage";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody final BoardRequestDto params){

        boardService.update(id, params);

        return "sendMessage";
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/boardList")
    public List<BoardResponseDto> findAll(){
        return boardService.findAll();
    }
}
