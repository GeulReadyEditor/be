package com.encore.backend.repository;

import java.util.Map;

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
    public boolean addUserScraps(String email, String boardId) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().push("scraps", boardId), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeUserScraps(String email, String boardId) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().pull("scraps", boardId), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean addUserFollwers(String email, String followerEmail) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().push("followers", followerEmail), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeUserFollwers(String email, String follower) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().pull("followers", follower), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public int findScrapsCountByEmail(String email) {
        Map<String, Object> result = operations.findOne(Query.query(Criteria.where("email").is(email)), Map.class);

        return 0;
    }
}
