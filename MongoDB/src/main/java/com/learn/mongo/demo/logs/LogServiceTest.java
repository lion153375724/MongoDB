package com.learn.mongo.demo.logs;

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
import com.learn.mongo.springboot.service.PageModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class LogServiceTest {
	
	@Autowired
	private LogService logService;
	
	@Test
	public void testSave(){
		String[] paths = {"/apache_pb.gif","/nginx_pb.gif","/tomcat_pb.gif","netty.gif"};
		String[] hosts = {"127.0.0.1","10.1.0.1","27.0.0.1","37.0.0.1"}; 
		String[] dates = {"2107-10-2 10:30:20","2107-08-2 10:30:20","2107-07-2 10:30:20","2107-05-2 10:30:20"}; 
		Random rand = new Random();
		for(int i=1;i<10000;i++){
			LogBean logBean = new LogBean(hosts[rand.nextInt(4)],
					parseDate(dates[rand.nextInt(4)]),
					paths[rand.nextInt(4)],
					"[http://www.example.com/start.html](http://www.example.com/start.html)",
					"Mozilla/4.08 [en] (Win98; I ;Nav)");
			logService.Save(logBean, "log");
		}
	}
	
	public static void main(String[] args) {
		Random rand = new Random();
		for(int i=0;i<100;i++)
		System.out.println(rand.nextInt(4));
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
	
	@Test
	public void testUpdate(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("_id", "59f2f335badc62374c6674e5");
		params.put("host", "37.0.222.1");
		logService.update(params, "log");
	}
	
	@Test
	public void testfindLogs(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("path", "/apache_pb.gif");
		List<LogBean> list = logService.findLogs(params, "log");
		System.out.println("list size:" + list.size());
		for(LogBean log : list){
			System.out.println(log.getHost()+":" + log.getPath() + ":" + log.getTime());
		}
	}
	
	@Test
	public void testFindLogs_page() throws ParseException{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", "2107-08-2 10:30:20");
		params.put("endTime", "2107-10-2 10:30:20");
		PageModel<LogBean> pageModel = new PageModel<LogBean>();
		pageModel = logService.findLogs_page(pageModel,params, "log");
		System.out.println("pageModel :" + pageModel.getDatas().size()+":"+pageModel.getPageNo() +":"+pageModel.getRowCount());
		for(LogBean log : pageModel.getDatas()){
			System.out.println(log.getHost()+":" + log.getPath() + ":" + log.getTime());
		}
	}
	
	

}
