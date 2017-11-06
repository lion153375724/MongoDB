package com.learn.mongo.springboot.multiDataSource.model.repository.second;

import org.springframework.data.annotation.Id;

public class SecondaryMongoObject {

	@Id
	private String id;

	private String value;

	public SecondaryMongoObject(String id,String value){
		this.id = id;
		this.value = value;
	}
	
	@Override
	public String toString() {
        return "SecondaryMongoObject{" + "id='" + id + '\'' + ", value='" + value + '\''
				+ '}';
	}
}
