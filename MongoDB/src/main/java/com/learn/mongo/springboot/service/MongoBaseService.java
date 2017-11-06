package com.learn.mongo.springboot.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public abstract class MongoBaseService<T> {
	@Resource(name="primaryMongoTemplate")
    private MongoTemplate mongoTemplate;
	
	private Class<T> getT_Class(){
		Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
        return entityClass;
	}
	
	/**
	 * 新增
	 * @param object
	 * @param collectionName
	 */
	public void Save(T object,String collectionName ) {
		mongoTemplate.save(object, collectionName);
	}
	
	/**
	 * 批量新增
	 * @param list
	 * @param collectionName
	 */
	public void Bath_Save(List<T> list,String collectionName){
		mongoTemplate.insert(list, collectionName);
	}
	
	/**
	 * 根据id删除
	 * @param params
	 * @param collectionName
	 */
	public void delete(String _id,String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(new ObjectId(_id))), collectionName);
	}
	
	/**
	 * 根据id查找一条记录
	 * @param _id
	 * @param collectionName
	 * @return
	 */
	public T findOneById(String _id,String collectionName) {
		return mongoTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(_id))),getT_Class(),collectionName);
	}
	
	
	/**
	 * 创建集合
	 * @param collectionName
	 */
	public void CreateCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}
}
