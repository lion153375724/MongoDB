package com.example.demo;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.mongo.MongoDBApplication;
import com.learn.mongo.springboot.multiDataSource.model.repository.primary.PrimaryMongoObject;
import com.learn.mongo.springboot.multiDataSource.model.repository.primary.PrimaryRepository;
import com.learn.mongo.springboot.multiDataSource.model.repository.second.SecondaryMongoObject;
import com.learn.mongo.springboot.multiDataSource.model.repository.second.SecondaryRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class MuliDatabaseTest {

    @Autowired
    private PrimaryRepository primaryRepository;
    @Autowired
    private SecondaryRepository secondaryRepository;
    
    @Resource(name="primaryMongoTemplate")
    private MongoTemplate primaryMongoTemplate;
    @Resource(name="secondaryMongoTemplate")
    private MongoTemplate secondaryMongoTemplate;
    

    @Test
    public void TestSave() {
    	
    	//template方式
        System.out.println("************************************************************");
        System.out.println("测试开始");
        System.out.println("************************************************************");

        this.primaryMongoTemplate
                .save(new PrimaryMongoObject(null, "第一个库的对象"));

        this.secondaryMongoTemplate
                .save(new SecondaryMongoObject(null, "第二个库的对象"));

        List<PrimaryMongoObject> primaries = this.primaryMongoTemplate.findAll(PrimaryMongoObject.class);
        for (PrimaryMongoObject primary : primaries) {
            System.out.println(primary.toString());
        }

        List<SecondaryMongoObject> secondaries = this.secondaryMongoTemplate.findAll(SecondaryMongoObject.class);

        for (SecondaryMongoObject secondary : secondaries) {
            System.out.println(secondary.toString());
        }

        System.out.println("************************************************************");
        System.out.println("测试完成");
        System.out.println("************************************************************");
        

    	/*//jpa方式
        System.out.println("************************************************************");
        System.out.println("测试开始");
        System.out.println("************************************************************");

        this.primaryRepository
                .save(new PrimaryMongoObject(null, "第一个库的对象"));

        this.secondaryRepository
                .save(new SecondaryMongoObject(null, "第二个库的对象"));

        List<PrimaryMongoObject> primaries = this.primaryRepository.findAll();
        for (PrimaryMongoObject primary : primaries) {
            System.out.println(primary.toString());
        }

        List<SecondaryMongoObject> secondaries = this.secondaryRepository.findAll();

        for (SecondaryMongoObject secondary : secondaries) {
            System.out.println(secondary.toString());
        }

        System.out.println("************************************************************");
        System.out.println("测试完成");
        System.out.println("************************************************************");
        */
    }

}
