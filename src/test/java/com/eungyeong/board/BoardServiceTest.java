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
        for(int i=1; i<=100; i++) {
            BoardEntity boardEntity = new BoardEntity();
            boardEntity.setBoardPass(i+"");
            boardEntity.setBoardTitle(i+"번째 게시물 내용");
            boardEntity.setBoardWriter("테스터"+i);
            boardEntity.setBoardContents(i+"번째 게시물 내용");
            BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
            boardService.save(boardDto);
        }


    }
}
