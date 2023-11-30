package com.eungyeong.board.controller;

import com.eungyeong.board.dto.BoardDto;
import com.eungyeong.board.dto.CommentDto;
import com.eungyeong.board.service.BoardService;
import com.eungyeong.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/board/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/";
    }

    @GetMapping("/")
    public String findAll(@PageableDefault(page=1) Pageable pageable, Model model) {
        Page<BoardDto> boardList = boardService.findAll(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "list";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        /* 댓글 목록 가져오기 */
        List<CommentDto> commentDtoList = commentService.findAll(id);
        model.addAttribute("commentList", commentDtoList);

        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "update";
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model, @PageableDefault(page=1) Pageable pageable) {
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/";
    }
}
