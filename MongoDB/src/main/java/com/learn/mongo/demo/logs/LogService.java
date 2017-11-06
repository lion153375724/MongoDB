package com.learn.mongo.demo.logs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.learn.mongo.springboot.service.MongoBaseService;
import com.learn.mongo.springboot.service.PageModel;

@Service
public class LogService extends MongoBaseService<LogBean>{
	
	@Resource(name="primaryMongoTemplate")
    private MongoTemplate mongoTemplate;
	
	/**
	 * 根据id修改host主机信息(这个方法没用，只是为了测试update写法)
	 * @param params
	 * @param collectionName
	 */
	public void update(Map<String,Object> params,String collectionName) {
		/*mongoTemplate.upsert(new Query(Criteria.where("_id").is(new ObjectId(params.get("_id").toString()))), 
							new Update().set("host", params.get("host").toString()),LogBean.class, collectionName);*/
		mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(new ObjectId(params.get("_id").toString()))), 
				new Update().set("host", params.get("host").toString()),LogBean.class, collectionName);
	}
	
	private Date parseDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询所有访问 该path 的请求
	 * @return
	 */
	public List<LogBean> findLogs(Map<String,Object> params,String collectionName){
		return mongoTemplate.find(new Query(Criteria.where("path").is(params.get("path"))), LogBean.class, collectionName);
	}
	
	/**
	 * 分页根据条件查找所有记录
	 * @return
	 */
	/*public List<LogBean> findAll_Page(Map<String,Object> params,String collectionName,int pageNo,int pageSize){
		PageModel p;
	}*/
	
	/**
	 * 分页查询某一时间段的所有请求所有记录
	 * @return
	 * @throws ParseException 
	 */
	public PageModel<LogBean> findLogs_page(PageModel<LogBean> page, Map<String,Object> params,String collectionName) throws ParseException {  
		Date startTime = parseDate(params.get("startTime").toString());
		Date endTime = parseDate(params.get("endTime").toString());
		System.out.println("StartTime:"+startTime + ":endTime:" + endTime);
		Query query=new Query(Criteria.where("time").gte(startTime).lte(endTime));  
		//查询总数  
		int count=(int) mongoTemplate.count(query,LogBean.class);  
		page.setRowCount(count);  
		//排序 :按time及_id 
		query.with(new Sort(Direction.ASC, "time","_id"));
		query.skip(page.getSkip()).limit(page.getPageSize());  
		List<LogBean>datas=mongoTemplate.find(query,LogBean.class,collectionName);  
		page.setDatas(datas);  
		return page;  
	}  
}
