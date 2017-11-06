package com.learn.mongo.springboot.multiDataSource.model.repository.primary;

import org.springframework.data.annotation.Id;


public class PrimaryMongoObject {

	@Id
	private String id;

	private String value;

	public PrimaryMongoObject(String id,String value){
		this.id = id;
		this.value = value;
	}
	
	@Override
	public String toString() {
        return "PrimaryMongoObject{" + "id='" + id + '\'' + ", value='" + value + '\''
				+ '}';
	}
}
