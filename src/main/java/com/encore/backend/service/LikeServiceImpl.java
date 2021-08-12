package com.encore.backend.service;

import com.encore.backend.repository.board.BoardRepository;
import com.encore.backend.repository.user.UserRepository;
import com.encore.backend.vo.BoardVO;
import com.encore.backend.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    private BoardRepository boardRepository;
    private UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int getBoardLikes(String boardId) {
        Optional<BoardVO> boardDto = boardRepository.findById(boardId);
        if (boardDto == null)
            throw new IllegalArgumentException();

        BoardVO board = boardDto.get();
        return board.getLikes();
    }

    @Override
    public int updateBoardLikes(String boardId, String userEmail) {

        boolean existence = checkExistence(userEmail, boardId);

        int boardLikes = getBoardLikes(boardId);
        if (existence)
            boardLikes -= 1;
        else
            boardLikes += 1;

        boolean result = boardRepository.updateLikes(boardId, Integer.valueOf(boardLikes));
        if (!result)
            throw new IllegalArgumentException("잘못된 boardId 입력");

        updateUserLikes(userEmail, boardId, existence);

        return boardLikes;
    }

    private boolean checkExistence(String userEmail, String boardId) {

        log.info("email={}", userEmail);
        UserVO userVO = userRepository.findByEmail(userEmail);
        List<String> likes = userVO.getLikes();
        if (likes == null)
            return false;
        for (String like : likes) {
            if (like.equals(boardId))
                return true;
        }
        return false;
    }

    @Override
    public void updateUserLikes(String userEmail, String boardId, boolean exist) {
        if (exist) {
            if (!userRepository.deleteLikes(userEmail, boardId))
                throw new IllegalArgumentException();
        } else {
            if (!userRepository.addLikes(userEmail, boardId))
                throw new IllegalArgumentException();
        }
    }
}
