package com.encore.backend.repository;

import java.util.List;
import java.util.Map;

import com.encore.backend.vo.Board;
import com.encore.backend.vo.Comment;
import com.encore.backend.vo.Content;
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

public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final MongoOperations operations;

    @Autowired
    public BoardCustomRepositoryImpl(MongoOperations operations) {
        Assert.notNull(operations, "operation not null!");
        this.operations = operations;
    }

    @Override
    public boolean updateBoard(String boardId, List<Content> contents) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("_id").is(boardId)),
                new Update().set("contents", contents), Board.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean insertComment(String boardId, Comment comment) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("_id").is(boardId)),
                new Update().push("comments", comment), Board.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean updateComment(String boardId, Comment commentData) {
        removeComment(boardId, commentData.getId());
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("_id").is(boardId)),
                new Update().push("comments", commentData), Board.class);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean removeComment(String boardId, String commentId) {
        UpdateResult result = operations.updateFirst(Query.query(Criteria.where("_id").is(boardId)),
                new Update().pull("comments", new Comment(commentId)), Board.class);
        return result.getModifiedCount() > 0;
    }

}
