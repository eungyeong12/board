package com.eungyeong.board;

import com.eungyeong.board.dto.BoardDto;
import com.eungyeong.board.entity.BoardEntity;
import com.eungyeong.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void save() {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardPass("1");
        boardEntity.setBoardTitle("1");
        boardEntity.setBoardWriter("1");
        boardEntity.setBoardContents("1");
        BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
        boardService.save(boardDto);

    }
}
