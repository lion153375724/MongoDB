package com.learn.mongo.demo.productCategory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.mongo.MongoDBApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class ProductCategoryServiceTest {
	
	@Autowired
	private ProductCategoryService pcService;
	
	@Test
	public void testSave(){
		String[] types = {"1","2","3","4","5","6","7","8","9","10"};
		Random rand = new Random();
		for(int i=0;i<1000000;i++){
			
			Shipping shipping = new Shipping(rand.nextInt(10)+2,new Dimensions(rand.nextInt(10)+1, rand.nextInt(10)+1, rand.nextInt(1)+1));
			Pricing pricing = new Pricing(1200.00+rand.nextInt(10),1100.00+rand.nextInt(10),100+rand.nextInt(10),9+rand.nextInt(10));
			Details details = new Details("吴京新电影"+i,"吴京",new String[]{"战争","历史","动作"},new String[]{"吴京1"},new String[]{"吴京1"},new String[]{"吴京","袁珊珊","xxx"},"1111",new Date());////电影
			ProductCategoryBean pcBean = new ProductCategoryBean("sku00"+i,types[rand.nextInt(10)],"吴京新电影"+i,"吴京新电影"+i,""+i,shipping,pricing,details);
			/*Details details = new Details("刘德华新专辑"+i,"刘德华",new String[]{"流行","戏曲"},null,new String[]{"刘德华1"},new String[]{"刘德华"},"1111",parseDate(dates[rand.nextInt(4)]));//音乐
			ProductCategoryBean pcBean = new ProductCategoryBean("sku_zxx_00"+i,types[rand.nextInt(7)],"刘德华新专辑"+i,"刘德华新专辑"+i,""+i,shipping,pricing,details);
			*/
			pcService.Save(pcBean, "productCategor");
		}
	}
	
	@Test
	public void testUpdate(){
		pcService.update("productCategor");
	}
	
	@Test
	public void testDelete(){
		pcService.deleteByTitle("吴京新电影1", "productCategor");
	}
	
	@Test
	public void testfindByActor(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("artist", "吴京");
		List<ProductCategoryBean> datas = pcService.findByActor(params, "productCategor");
		System.out.println("data size:"+datas.size());
		for(ProductCategoryBean pc : datas){
			System.out.println(pc.getTitle()+":"+pc.getSku() + ":" + pc.getDetails().getArtist() +":" + pc.getDetails().getIssue_date() + ":" + pc.getPricing().getRetail());
		}
	}
	
	@Test
	public void testfindByTitle(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("title", "吴京");
		List<ProductCategoryBean> datas = pcService.findByTitle(params, "productCategor");
		System.out.println("data size:"+datas.size());
		for(ProductCategoryBean pc : datas){
			System.out.println(pc.getTitle()+":"+pc.getSku() + ":" + pc.getDetails().getArtist() +":" + pc.getDetails().getIssue_date() + ":" + pc.getPricing().getRetail());
		}
	}
	
	@Test
	public void testfindByType(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", "1");
		params.put("genre", "战争");
		List<ProductCategoryBean> datas = pcService.findByType(params, "productCategor");
		System.out.println("data size:"+datas.size());
		for(ProductCategoryBean pc : datas){
			System.out.println(pc.getTitle()+":"+pc.getSku() + ":" + pc.getDetails().getArtist() +":" + pc.getDetails().getIssue_date() + ":" + pc.getPricing().getRetail());
		}
	}
	
	public static void main(String[] args) {
		Random rand = new Random();
		for(int i=0;i<100;i++)
		System.out.println(rand.nextInt(4));
	}
	private Date parseDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
