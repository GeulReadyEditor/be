package com.encore.backend.repository;

import java.util.List;

import com.encore.backend.vo.UserVO;
import com.mongodb.client.result.DeleteResult;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Override
    public boolean deleteLikes(String userEmail, String boardId) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(userEmail)),
                new Update().pull("likes", boardId), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean addLikes(String userEmail, String boardId) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(userEmail)),
                new Update().push("likes", boardId), UserVO.class);
        return result.getModifiedCount() > 0;
    }
}
