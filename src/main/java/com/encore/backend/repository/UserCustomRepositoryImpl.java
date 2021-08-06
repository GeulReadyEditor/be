package com.encore.backend.repository;

import java.util.List;

import com.encore.backend.vo.UserVO;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final MongoOperations operations;

    @Autowired
    public UserCustomRepositoryImpl(MongoOperations operations) {
        Assert.notNull(operations, "operation not null!");
        this.operations = operations;
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        DeleteResult result = operations.remove(Query.query(Criteria.where("email").is(email)), UserVO.class);
        return result.getDeletedCount() > 0;
    }
}
