package com.encore.backend.repository;

import com.encore.backend.vo.UserVO;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserVO, String> {
    UserVO findByUserId(String id);

    UserVO findByEmail(String email);
}
