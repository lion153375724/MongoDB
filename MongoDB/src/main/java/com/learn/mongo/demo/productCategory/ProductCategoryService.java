package com.learn.mongo.demo.productCategory;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Update.Position;
import org.springframework.stereotype.Service;

import com.learn.mongo.springboot.service.MongoBaseService;

@Service
public class ProductCategoryService extends MongoBaseService<ProductCategoryBean>{

	@Resource(name="threeMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	/**
	 * 修改title,shipping的dimensions的height,pricing的retail,以及details的tracks
	 */
	public void update(String collectionName){
		mongoTemplate.updateMulti(new Query(Criteria.where("_id").is("59f6e211badc62420c3fa969")), 
									new Update().set("title", "吴1京新电影2修改")
												.set("shipping.dimensions.height", 3)
												.addToSet("testinsert", 9) //没有该key新增加,有的话,更新值
												//.push("testpush", "bbb") //没有新新增加,有的话累加，数组形式
												.pop("details.genre",Position.FIRST ) //删除第一个
												//.pull("testpush","bbb")  //删除满足条件的值
												.set("details.tracks", new String[]{"update","test"})
								, "productCate");
	}
	
	/**
	 * 根据标题删除记录
	 * @param title
	 * @param collectionName
	 */
	public void deleteByTitle(String title,String collectionName){
		mongoTemplate.remove(new Query(Criteria.where("title").is("吴京新电影1")), collectionName);
	}
	
	/**
	 * 根据演员的名称查找参演的所有电影,按发行日期排序
	 * @param params
	 * @param collectionName
	 */
	public List<ProductCategoryBean> findByActor(Map<String,Object> params,String collectionName){
		return mongoTemplate.find(new Query(Criteria.where("details.artist").is(params.get("artist"))).limit(50).with(new Sort(Direction.ASC,"details.issue_date")), ProductCategoryBean.class, collectionName);
	}
	
	/**
	 * 查询标题里包含特定信息的所有电影,按发行日期排序
	 * @param params
	 * @param collectionName
	 */
	public List<ProductCategoryBean> findByTitle(Map<String,Object> params,String collectionName){
		return mongoTemplate.find(new Query(Criteria.where("title").regex(Pattern.compile("^("+ params.get("title") +")+"))).with(new Sort(Direction.ASC,"details.issue_date")), ProductCategoryBean.class, collectionName);
	}
	
	/**
	 * 根据商品类型及商品风格标签查询,按发行日期排序
	 * @param params
	 * @param collectionName
	 */
	public List<ProductCategoryBean> findByType(Map<String,Object> params,String collectionName){
		return mongoTemplate.find(new Query(Criteria.where("type").is(params.get("type")).
														and("details.genre").all(params.get("genre"))).
								with(new Sort(Direction.ASC,"details.issue_date")), ProductCategoryBean.class, collectionName);
	}
}
