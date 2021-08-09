package com.encore.backend.repository.tempboard;

import com.encore.backend.vo.TempBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TempBoardRepository extends MongoRepository<TempBoard, String> {

    // Page<TempBoard> findByUserId(String userId, Pageable pageable);

    long countByUserEmail(String userEmail);

    Page<TempBoard> findByUserEmail(String userEmail, PageRequest of);
}
