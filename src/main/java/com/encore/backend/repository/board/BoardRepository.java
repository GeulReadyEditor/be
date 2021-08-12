package com.encore.backend.repository.board;

import java.util.List;

import com.encore.backend.vo.BoardVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BoardRepository extends MongoRepository<BoardVO, String>, BoardCustomRepository {
    @Query(fields = "{comments:0}")
    Page<BoardVO> findById(String boardId, PageRequest of);

    @Query(fields = "{comments:0}")
    Page<BoardVO> findByTitleLikeOrderByModDatetimeDesc(Object object, PageRequest of);

    @Query(fields = "{comments:0}")
    Page<BoardVO> findByNickNameLikeOrderByModDatetimeDesc(Object object, PageRequest of);

    @Query(fields = "{comments:0}")
    Page<BoardVO> findByTagsLikeOrderByModDatetimeDesc(Object object, PageRequest of);

    @Query(value = "{_id:?0}", fields = "{_id:0,userId:0,nickName:0,title:0,subtitle:0,titleImage:0,likes:0,modDatetime:0,contents:0,tags:0 comments:{$slice:[?1,?2]}}")
    List<BoardVO> findCommentsById(String boardId, int start, int end);

    @Query(fields = "{comments:0}")
    Page<BoardVO> findAllByOrderByModDatetimeDesc(PageRequest of);

    Long countByTitleLikeOrNickNameOrTagsIn(String title, String nickName, String tag);

    Long countByTitleLike(String title);

    Long countByNickNameLike(String nickName);

    Long countByTagsIn(String tag);
}
