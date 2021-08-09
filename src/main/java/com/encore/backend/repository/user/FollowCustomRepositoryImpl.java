package com.encore.backend.repository.user;

import com.encore.backend.vo.UserVO;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

public class FollowCustomRepositoryImpl implements FollowCustomRepository {
    private final MongoOperations operations;

    @Autowired
    public FollowCustomRepositoryImpl(MongoOperations operations) {
        Assert.notNull(operations, "operation not null!");
        this.operations = operations;
    }

    @Override
    public boolean addUserFollowers(String email, String followerEmail) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().push("followers", followerEmail), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeUserFollowers(String email, String followEmail) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().pull("followers", followEmail), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean addUserFollowings(String email, String followEmail) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().push("followings", followEmail), UserVO.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeUserFollowings(String email, String followEmail) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("email").is(email)),
                new Update().pull("followings", followEmail), UserVO.class);
        return result.getModifiedCount() > 0;
    }
}
