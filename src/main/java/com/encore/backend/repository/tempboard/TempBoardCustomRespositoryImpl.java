package com.encore.backend.repository.tempboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

public class TempBoardCustomRespositoryImpl implements TempBoardCustomRepository {

    private final MongoOperations operations;

    @Autowired
    public TempBoardCustomRespositoryImpl(MongoOperations operations) {
        this.operations = operations;
    }

}
