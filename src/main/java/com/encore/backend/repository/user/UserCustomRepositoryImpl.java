package com.encore.backend.repository.user;

import java.util.Map;
import java.util.Objects;

import com.encore.backend.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
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
    public int findScrapsCountByEmail(String email) {
        MatchOperation match = new MatchOperation(Criteria.where("email").is(email));
        ProjectionOperation group = Aggregation.project("users").and("scraps").size().as("size");

        Aggregation aggregate = Aggregation.newAggregation(match, group);

        AggregationResults<Map> orderAggregate = operations.aggregate(aggregate, "users", Map.class);
        int size = (Integer) orderAggregate.getMappedResults().get(0).get("size");
        return size;
    }

    @Override
    public boolean updateUserByEmail(String email, UserVO user) {
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> dataMap = oMapper.convertValue(user, Map.class);
        dataMap.values().removeIf(Objects::isNull);
        Update update = new Update();
        dataMap.forEach(update::set);
        Query query = new Query().addCriteria(Criteria.where("email").is(email));
        UpdateResult updateResult = operations.updateFirst(query, update, UserVO.class);
        return updateResult.getMatchedCount() > 0;
    }

}
