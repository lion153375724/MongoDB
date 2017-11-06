package com.learn.mongo.springboot;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, Integer> {

	public Users findUserByName(String name);

	public void delete(Users user);

	public void updateUser(String name, String key, String value);

}
