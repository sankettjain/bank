package com.eltropy.bank.managers;

import com.eltropy.bank.mongo.entity.AbstractMongoEntity;
import com.eltropy.bank.mongo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class UserManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User getUserByEmailId(String userEmailId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.Constants.EMAIL).is(userEmailId));
        return mongoTemplate.findOne(query, User.class);
    }
}