package com.learn.mongo.springboot.multiDataSource.model.repository.second;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface SecondaryRepository extends MongoRepository<SecondaryMongoObject, String> {
}
