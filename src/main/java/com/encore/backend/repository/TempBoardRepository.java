package com.encore.backend.repository;

import com.encore.backend.vo.TempBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface TempBoardRepository extends MongoRepository<TempBoard, String> {

    Page<TempBoard> findByUserId(String userId, Pageable pageable);
}
