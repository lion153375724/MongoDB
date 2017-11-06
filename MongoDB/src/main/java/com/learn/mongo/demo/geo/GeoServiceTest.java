package com.learn.mongo.demo.geo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.GeoResults;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.mongo.MongoDBApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class GeoServiceTest {

	@Autowired
	private GeoService geoService;
	
	@Test
	public void saveTest(){
        List<AddressPosition> list = new ArrayList<AddressPosition>();
        AddressPosition address = new AddressPosition("少年宫",new AddressPoint(114.067343,22.553864),"少年宫");
        /*list.add(address);
        address = new AddressPosition("少儿美术培训基地",new AddressPoint(114.068466,22.553972),"少儿美术培训基地");
        list.add(address);
        address = new AddressPosition("深圳书城",new AddressPoint(114.065645,22.552053),"深圳书城");
        list.add(address);
        address = new AddressPosition("深圳音乐厅",new AddressPoint(114.063651,22.553096),"深圳音乐厅");
        list.add(address);
        address = new AddressPosition("关山乐美术馆",new AddressPoint(114.070792,22.555199),"关山乐美术馆");
        list.add(address);*/
        
        address = new AddressPosition("莲花一村",new AddressPoint(114.067352,22.553972),"莲花一村");
        list.add(address);
        
        geoService.Bath_Save(list, "geo_address");
	}
	
	@Test
	public void getPositionTest(){
		AddressPoint position = new AddressPoint(114.067289,22.554139);
		List<AddressPosition> results = geoService.getPosition(position, 0.3, "geo_address");
		for(AddressPosition ap : results){
			System.out.println(ap.getAddress() + ":" + ap.getAddressPoint().getLng()+":"+ap.getAddressPoint().getLat() + "::::" + ap.getDis());
		}
	}
	
	@Test
	public void getPositionByCenterSphereTest(){
		AddressPoint position = new AddressPoint(114.067289,22.554139);
		geoService.getPositionByCenterSphere(position, 0.9, "geo_address");
	}
	
	public static void main(String[] args) {
		
	}
}
