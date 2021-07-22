package com.encore.backend.repository;

import com.encore.backend.vo.TempBoard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TempBoardRepository extends MongoRepository<TempBoard, String> {

}
