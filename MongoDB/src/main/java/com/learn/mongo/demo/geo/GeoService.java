package com.learn.mongo.demo.geo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.learn.mongo.springboot.service.MongoBaseService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class GeoService extends MongoBaseService<AddressPosition>{
private static Logger logger = LoggerFactory.getLogger(GeoService.class);
    
	@Resource(name="primaryMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	/**
	 * 查找出指定地址坐标  x 公里范围内的地址
	 * @return
	 */
	public List<AddressPosition> getPosition(AddressPoint position,Double maxDistance,String collectionName){
		NearQuery near = NearQuery.near(position.getLng(),position.getLat());
		near.distanceMultiplier(6371);//其实可以直接在查询时指定 distanceMultiplier ，它会将这个参数乘以距离返回，如指定为6371，返回的dis就是公里数。
		near.maxDistance(maxDistance,Metrics.KILOMETERS); //单位为千米
		near.spherical(true); //度或弧度	指定参数spherical为true则为弧度结果值：结果中的dis需要乘以6371换算为km，，否则为度:结果中的dis需要乘以111换算为km
		//near.num(1); //结果数量
		//near.skip(3); //跳过多少条，用于分页
		GeoResults<AddressPosition> results = mongoTemplate.geoNear(near, AddressPosition.class, collectionName);
		List<AddressPosition> list = new ArrayList<AddressPosition> ();
		AddressPosition ap;
		for(GeoResult<AddressPosition> address : results){
			System.out.println("result:"+address.getContent().toString()+":"+address.getDistance().getValue() + ":" + address.getDistance().getMetric());
			ap = address.getContent();
			ap.setDis(address.getDistance().getValue());
			list.add(ap);
		}
		
		return list;
	}
	
	/**
	 * 圆形区域里的点
	 * @param position
	 * @param maxDistance
	 * @param collectionName
	 * @return
	 */
	public List<DBObject> getPositionByCenterSphere(AddressPoint position,Double maxDistance,String collectionName){
		 	/*LinkedList<Object> circle = new LinkedList<>();
	        //Set the center coordinate
	        circle.addLast(new double[]{position.getLng(),position.getLat()});
	        //Set the radius. unit:meter
	        circle.addLast(maxDistance/6371);*/
			List<Object> circle = new ArrayList<>();
	        circle.add(new double[]{position.getLng(),position.getLat()});
	        circle.add(maxDistance/6371);
	        DBObject query = new BasicDBObject();
	        query.put("addressPoint", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", circle)));
	        logger.info("withinCircle:{}",query.toString());
	        
	        Query query1 = new Query();
	        query1.addCriteria(Criteria.where("addressPoint").is(new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", circle))));
	        List<DBObject> list = mongoTemplate.find(query1, DBObject.class, collectionName);
	        for(DBObject obj :list){
	        	System.out.println("list:"+ obj.toString());
	        }
	        List<DBObject> list2 = mongoTemplate.getCollection(collectionName).find(query).limit(10).toArray();
	        for(DBObject obj :list2){
	        	System.out.println("list2:"+ obj.toString());
	        }
	        return null;
	}
	
 
    
	//以下老版本spring实现，非springboot
 
   /* *//** 
     * 聚合查询，查询一个点附近的点，并返回每一个点到该中心点的距离，在坐标表分片的情况下$nearSphere不支持， 
     * 可以使用该方法进行查询 
     * @param collection    集合名称 
     * @param query         查询条件 
     * @param point         中心点坐标 
     * @param limit         返回记录数量限制 
     * @param maxDistance   最大距离 
     * @return              非NULL的list 
     *//*  
    public List<DBObject> geoNear(String collection, DBObject query, Point point, int limit, long maxDistance) {
        if(query==null)
            query = new BasicDBObject();
 
        List<DBObject> pipeLine = new ArrayList<>();
        BasicDBObject aggregate = new BasicDBObject("$geoNear",
	    		new BasicDBObject("near",new BasicDBObject("type","Point").append("coordinates",new double[]{118.783799, 31.979234}))
				        .append("distanceField","dist.calculated")
				        .append("query", new BasicDBObject())
				        .append("num", 5)
				        .append("maxDistance", 5000)
				        .append("spherical",true)
	    		);
        pipeLine.add(aggregate);
        Cursor cursor=mongoTemplate.getCollection(collection).aggregate(pipeLine, AggregationOptions.builder().build());
        List<DBObject> list = new LinkedList<>();
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        return list;
    }
 
    *//** 
     * 查询在圆形区域内的坐标点，需要指定中心点坐标和半径，半径单位是米 
     * 
     * @param collection    集合名称 
     * @param locationField 坐标字段 
     * @param center        中心点坐标[经度，纬度] 
     * @param radius        半径 单位:米 
     * @param fields        查询字段 
     * @param query         查询条件 
     * @param limit         返回记录限制数量 
     * @return              非NULL的list 
     *//*  
    public List<DBObject> withinCircle(String collection,String locationField, Point center,
                                       long radius, DBObject fields, DBObject query, int limit) {
        LinkedList<Object> circle = new LinkedList<>();
        //Set the center coordinate
        circle.addLast(new double[]{center.getLng(),center.getLat()});
        //Set the radius. unit:meter
        circle.addLast(radius/6378137.0);
 
        if(query==null)
            query = new BasicDBObject();
        query.put(locationField, new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", circle)));
        logger.info("withinCircle:{}",query.toString());
        return mongoTemplate.getCollection(collection).find(query, fields).limit(limit).toArray();
    }
 
    *//** 
     * 指定一个点，返回该点附近的坐标点且是由近到远,$nearSphere 需要建立索引2dsphere 或者2d,并且支持GeoJSON和一般坐标对 
     * 注意: $nearSphere在分片的集群中无效，使用geoNear 
     * 
     * @param collection    集合名称 
     * @param locationField 坐标字段 
     * @param center        中心点坐标[经度，纬度] 
     * @param minDistance   最近距离 
     * @param maxDistance   最远距离 
     * @param query         查询条件 
     * @param fields        查询字段 
     * @param limit         返回记录限制数量 
     * @return              非NULL的list 
     *//*  
    public List<DBObject> nearSphere(String collection, String locationField, Point center,
                                     long minDistance, long maxDistance, DBObject query, DBObject fields, int limit) {
        if(query==null)
            query = new BasicDBObject();
 
        query.put(locationField,
                new BasicDBObject("$nearSphere",
                    new BasicDBObject("$geometry",
                            new BasicDBObject("type","Point")
                                    .append("coordinates",new double[]{center.getLng(),center.getLat()}))
                            .append("$minDistance",minDistance)
                            .append("$maxDistance",maxDistance)
        ));
        logger.info("nearSphere:{}",query.toString());
        return mongoTemplate.getCollection(collection).find(query, fields).limit(limit).toArray();
    }
 
    *//** 
     * 查询位于指定一个封闭多边形内的所有坐标点，给定的多边形坐标点必须首位相接形成封闭的多边形 
     * 如三角形 
     *       final LinkedList<double[]> polygon = new LinkedList<>(); 
     *       polygon.addLast(new double[] {  121.36, 31.18 }); 
     *       polygon.addLast(new double[] {  121.35, 31.36 }); 
     *       polygon.addLast(new double[] {  121.39, 31.17 }); 
     *       polygon.addLast(new double[] {  121.36, 31.18 }); 
     * 
     * MongoDB将多边形的边界也作为查询形状的一部分 
     * @param collection    集合名称 
     * @param locationField 坐标字段 
     * @param polygon       多边形坐标 
     * @param fields        查询字段 
     * @param query         查询条件 
     * @param limit         返回记录限制数量 
     * @return              非NULL的list 
     *//*  
    public List<DBObject> withinPolygon(String collection, String locationField,
                                        List<double[]> polygon, DBObject fields, DBObject query, int limit) {
        if(query==null)
            query = new BasicDBObject();
 
        List<List<double[]>> polygons = new LinkedList<>();
        polygons.add(polygon);
        query.put(locationField, new BasicDBObject("$geoWithin",
                new BasicDBObject("$geometry",
                        new BasicDBObject("type","Polygon")
                        .append("coordinates",polygons))));
        logger.info("withinPolygon:{}",query.toString());
        return mongoTemplate.getCollection(collection).find(query, fields).limit(limit).toArray();
    }
 
    *//** 
     * 查询位于指定多个封闭多边形内的所有坐标点，给定的多边形坐标点必须首位相接形成封闭的多边形 
     * @param collection    集合名称 
     * @param locationField 坐标字段 
     * @param polygons      多边形坐标 数组 
     * @param fields        查询字段 
     * @param query         查询条件 
     * @param limit         返回记录限制数量 
     * @return              非NULL的list 
     *//*  
    public List<DBObject> withinMultiPolygon(String collection, String locationField, List<List<double[]>> polygons, DBObject fields, DBObject query, int limit) {
        if(query==null)
            query = new BasicDBObject();
 
        List<List<List<double[]>>> list = new LinkedList<>();
        for (List<double[]> polygon : polygons) {
            List<List<double[]>> temp = new LinkedList<>();
            temp.add(polygon);
            list.add(temp);
        }
        query.put(locationField, new BasicDBObject("$geoWithin",
                new BasicDBObject("$geometry",
                        new BasicDBObject("type","MultiPolygon")
                                .append("coordinates",list))));
        logger.info("withinMultiPolygon:{}",query.toString());
        return mongoTemplate.getCollection(collection).find(query, fields).limit(limit).toArray();
    }
 
    *//** 
     * 在矩形区域内查找坐标点，该方法仅仅在2d索引是支持，在2dsphere中不支持 
     * @param collection    集合名称 
     * @param locationField 坐标字段 
     * @param bottomLeft    左下角 
     * @param upperRight    右上角 
     * @param fields        查询字段 
     * @param query         查询条件 
     * @param limit         返回记录限制数量 
     * @return              非NULL的list 
     *//*  
    public List<DBObject> withinBox(String collection, String locationField, Point bottomLeft, Point upperRight, DBObject fields, DBObject query, int limit) {
        if(query==null)
            query = new BasicDBObject();
 
        LinkedList<double[]> box = new LinkedList<>();
        box.add(new double[]{bottomLeft.getLng(), bottomLeft.getLat()});
        box.add(new double[]{upperRight.getLng(), upperRight.getLat()});
 
        query.put(locationField, new BasicDBObject("$geoWithin", new BasicDBObject("$box", box)));
        logger.info("withinBox:{}",query.toString());
        return mongoTemplate.getCollection(collection).find(query, fields).limit(limit).toArray();
    }*/
}
