package com.eungyeong.board.service;

import com.eungyeong.board.dto.CommentDto;
import com.eungyeong.board.entity.BoardEntity;
import com.eungyeong.board.entity.CommentEntity;
import com.eungyeong.board.repository.BoardRepository;
import com.eungyeong.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if(optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }

    }

    public List<CommentDto> findAll(Long boardId) {
        // select * from comment_table where board_id=? order by id desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        // EntityList -> DTOList
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList) {
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
