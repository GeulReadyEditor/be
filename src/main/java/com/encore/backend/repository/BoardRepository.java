package com.encore.backend.repository;

import java.util.List;

import com.encore.backend.vo.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BoardRepository extends MongoRepository<Board, String>, BoardCustomRepository {
    @Query(fields = "{comments:0}")
    Page<Board> findById(String boardId, PageRequest of);

    @Query(fields = "{contents:0, comments:0}")
    Page<Board> findByTitleLike(Object object, PageRequest of);

    @Query(fields = "{contents:0, comments:0}")
    Page<Board> findByNickNameLike(Object object, PageRequest of);

    @Query(fields = "{contents:0, comments:0}")
    Page<Board> findByTagsLike(Object object, PageRequest of);

    @Query(value = "{_id:?0}", fields = "{id:0,userId:0,nickName:0,title:0,subtitle:0,titleImage:0,likes:0,modDatetime:0,contents:0,tags:0 comments:{$slice:[?1,?2]}}")
    List<Board> findCommentsById(String boardId, int start, int end);

}
