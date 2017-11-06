package com.learn.mongo.demo.friendComments;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.learn.mongo.springboot.service.MongoBaseService;

@Service
public class FriendCommentService extends MongoBaseService<FriendCommentsBean>{

	/*@Resource(name="primaryMongoTemplate")
    private MongoTemplate primaryMongoTemplate;*/
	@Resource(name="secondaryMongoTemplate")
    private MongoTemplate secondaryMongoTemplate;
	
	@Override
	public void Save(FriendCommentsBean object,String collectionName ) {
		secondaryMongoTemplate.save(object, collectionName);
	}
	/**
	 * 根据用户id获取他的点赞及评论列表
	 * @param uid
	 * @param collection
	 * @return
	 */
	public List<FriendCommentsBean> listCommentByUserId(String uid,String collection){
		return secondaryMongoTemplate.find(new Query(Criteria.where("uid").is(uid)), FriendCommentsBean.class,collection);
	}
	
}
