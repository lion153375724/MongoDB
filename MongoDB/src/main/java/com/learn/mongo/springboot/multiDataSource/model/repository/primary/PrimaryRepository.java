package com.learn.mongo.springboot.multiDataSource.model.repository.primary;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
