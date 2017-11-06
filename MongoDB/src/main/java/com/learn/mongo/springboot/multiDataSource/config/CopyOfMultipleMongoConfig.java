package com.learn.mongo.springboot.multiDataSource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.learn.mongo.springboot.multiDataSource.config.props.MultipleMongoProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


@Configuration
public class CopyOfMultipleMongoConfig {

	@Autowired
	private MultipleMongoProperties mongoProperties;

	@Bean(name = "primaryMongoTemplate")
	@Primary
	public MongoTemplate primaryMongoTemplate(@Qualifier("primaryMongoFactory") MongoDbFactory  mongoDbFactory) throws Exception {
		return new MongoTemplate(mongoDbFactory);
	}

	@Bean(name = "secondaryMongoTemplate")
	public MongoTemplate secondaryMongoTemplate(@Qualifier("secondaryMongoFactory") MongoDbFactory  mongoDbFactory) throws Exception {
        return new MongoTemplate(mongoDbFactory);
	}
	
	@Bean(name="threeMongoTemplate")
	public MongoTemplate ThreeMongoTemplate(@Qualifier("threeMongoFactory") MongoDbFactory  mongoDbFactory) throws Exception {
        return new MongoTemplate(mongoDbFactory);
	}

	@Bean(name = "primaryMongoFactory")
	@Qualifier("primaryMongoFactory")
    @Primary
	public MongoDbFactory primaryFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClientURI(this.mongoProperties.getPrimary().getUri()));
	}

	@Bean(name = "secondaryMongoFactory")
	@Qualifier("secondaryMongoFactory")
	public MongoDbFactory secondaryFactory() throws Exception {
		MongoProperties mongo = this.mongoProperties.getSecondary();
		return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
				mongo.getDatabase());
	}
	
	@Bean(name = "threeMongoFactory")
	@Qualifier("threeMongoFactory")
	public MongoDbFactory threearyFactory() throws Exception {
		MongoProperties mongo = this.mongoProperties.getThreeary();
		return new SimpleMongoDbFactory(new MongoClientURI(mongo.getUri()));
	}
}
