package com.encore.backend.repository;

import com.encore.backend.vo.TempBoard;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TempBoardCustomRespositoryImpl implements TempBoardCustomRepository {

    private final MongoOperations operations;

    @Autowired
    public TempBoardCustomRespositoryImpl(MongoOperations operations) {
        this.operations = operations;
    }


}
