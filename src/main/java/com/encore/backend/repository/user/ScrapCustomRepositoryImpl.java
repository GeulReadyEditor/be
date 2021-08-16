package com.encore.backend.repository.user;

import java.util.Map;

import com.encore.backend.vo.UserVO;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
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

    @Override
    public int findScrapsCountByEmail(String email) {
        MatchOperation match = new MatchOperation(Criteria.where("email").is(email));
        ProjectionOperation group = Aggregation.project().and("scraps").size().as("size");

        Aggregation aggregate = Aggregation.newAggregation(match, group);

        AggregationResults<Map> orderAggregate = operations.aggregate(aggregate, "user", Map.class);
        int size = (Integer) orderAggregate.getMappedResults().get(0).get("size");
        return size;
    }
}
