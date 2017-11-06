package com.learn.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.learn.mongo.springboot.multiDataSource.config.props.MultipleMongoProperties;

@EnableConfigurationProperties(MultipleMongoProperties.class) //多数据源配置 
@SpringBootApplication
public class MongoDBApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDBApplication.class, args);
	}
}
