package com.eltropy.bank.mongo.utils;

import com.eltropy.bank.mongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
