package com.learn.mongo.springboot;

import java.util.List;

public interface IUserService {
	public void saveUser(Users users);

	public Users findUserByName(String name);

	public void removeUser(String name);

	public void updateUser(String name, String key, String value);

	public List<Users> listUser();
}
