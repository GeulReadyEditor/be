package com.encore.backend.repository;

import com.encore.backend.vo.UserVO;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserVO, String>, UserCustomRepository {
    UserVO findByUserId(String id);

    UserVO findByEmail(String email);

    @Query(value = "{email:?0}", fields = "{_id:0,email:0,name:0,nickName:0,intro:0,profileImage:0,scraps:{$slice:[?1,?2]}},followers:0,followings:0,tags:0}")
    UserVO findScrapsByEmail(String email, int start, int end);

    @Query(value = "{email:?0}", fields = "{_id:0,email:0,name:0,nickName:0,intro:0,profileImage:0,scraps:0,followings:0,tags:0}")
    UserVO findFollowersByEmail(String email);

}
