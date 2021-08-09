package com.encore.backend.repository.board;

import java.util.List;

import com.encore.backend.vo.Comment;
import com.encore.backend.vo.Content;

public interface BoardCustomRepository {
    boolean insertComment(String boardId, Comment comment);

    boolean updateComment(String boardId, Comment commentData);

    boolean removeComment(String boardId, String commentId);

    boolean updateBoard(String boardId, List<Content> contents);

    boolean updateLikes(String boardId, Integer quantity);
}
