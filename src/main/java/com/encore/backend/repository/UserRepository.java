package com.encore.backend.repository;

import java.util.List;

import com.encore.backend.vo.UserVO;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserVO, String>, UserCustomRepository {
    UserVO findByUserId(String id);

    UserVO findByEmail(String email);

    @Query(value = "{email:?0}", fields = "{scraps:{$slice:[?1,?2]}}")
    List<String> findScrapsByEmail(String email, int start, int end);
}
