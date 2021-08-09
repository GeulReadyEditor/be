package com.encore.backend.repository.user;

import com.encore.backend.vo.UserVO;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {
    private final MongoOperations operations;

    @Autowired
    public ScrapCustomRepositoryImpl(MongoOperations operations) {
        Assert.notNull(operations, "operation not null!");
        this.operations = operations;
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
}
