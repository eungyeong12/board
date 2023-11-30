package com.eungyeong.board.service;

import com.eungyeong.board.dto.BoardDto;
import com.eungyeong.board.entity.BoardEntity;
import com.eungyeong.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
        boardRepository.save(boardEntity);
    }

    @Transactional
    public Page<BoardDto> findAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDto> boardDTOS = boardEntities.map(board -> new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDto findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
            return boardDto;
        } else {
            return null;
        }
    }

    @Transactional
    public BoardDto update(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);
        return findById(boardDto.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
