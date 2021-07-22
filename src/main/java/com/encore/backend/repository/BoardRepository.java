package com.encore.backend.repository;

import com.encore.backend.vo.Board;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {

}
