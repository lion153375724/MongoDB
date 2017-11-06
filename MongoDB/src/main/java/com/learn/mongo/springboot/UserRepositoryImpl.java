package com.learn.mongo.springboot;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserRepositoryImpl implements IUserService {
	@Autowired
	MongoTemplate mongoTemplate;

	public void saveUser(Users users) {
		mongoTemplate.save(users);
	}

	public Users findUserByName(String name) {
		return mongoTemplate.findOne(
				new Query(Criteria.where("name").is(name)), Users.class);
	}

	public void removeUser(String name) {
		mongoTemplate.remove(new Query(Criteria.where("name").is(name)),
				Users.class);
	}

	public void updateUser(String name, String key, String value) {
		mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)),
				Update.update(key, value), Users.class);

	}

	public List<Users> listUser() {
		return mongoTemplate.findAll(Users.class);
	}
}
