package com.learn.mongo.springboot.multiDataSource.config.props;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mongodb")
public class MultipleMongoProperties {

	MongoProperties primary = new MongoProperties();
	MongoProperties secondary = new MongoProperties();
	MongoProperties threeary = new MongoProperties();
	
	public MongoProperties getPrimary() {
		return primary;
	}
	public void setPrimary(MongoProperties primary) {
		this.primary = primary;
	}
	public MongoProperties getSecondary() {
		return secondary;
	}
	public void setSecondary(MongoProperties secondary) {
		this.secondary = secondary;
	}
	public MongoProperties getThreeary() {
		return threeary;
	}
	public void setThreeary(MongoProperties threeary) {
		this.threeary = threeary;
	}
	
	
}
