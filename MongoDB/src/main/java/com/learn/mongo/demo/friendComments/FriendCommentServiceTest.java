package com.learn.mongo.demo.friendComments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.mongo.MongoDBApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class FriendCommentServiceTest {

	@Autowired
	private FriendCommentService fcService;
	
	@Test
	public void testSave(){
		List<PraiseBean> praiseList;
		PraiseBean pBean;
		List<CommentBean> commentList;
		CommentBean cBean;
		for(int i=0;i<2;i++){
			praiseList = new ArrayList<PraiseBean> ();
			pBean = new PraiseBean(i+1+"","/imags/a"+i+1+".jpg");
			praiseList.add(pBean);
			pBean = new PraiseBean(i+2+"","/imags/a"+i+2+".jpg");
			praiseList.add(pBean);
			pBean = new PraiseBean(i+3+"","/imags/a"+i+3+".jpg");
			praiseList.add(pBean);
			
			commentList = new ArrayList<CommentBean> ();
			cBean = new CommentBean(i+1+"","/imags/a"+i+1+".jpg","msg:"+i+1);
			commentList.add(cBean);
			cBean = new CommentBean(i+2+"","/imags/a"+i+2+".jpg","msg:"+i+2);
			commentList.add(cBean);
			cBean = new CommentBean(i+3+"","/imags/a"+i+3+".jpg","msg:"+i+3);
			commentList.add(cBean);
			
			FriendCommentsBean fcBean = new FriendCommentsBean("1",praiseList,commentList);
			
			fcService.Save(fcBean, "friendComment");
		}
	}
	
	
	
	@Test
	public void listCommentByUserIdTest(){
		List<FriendCommentsBean> datas = fcService.listCommentByUserId("1", "friendComment");
		List<CommentBean> cbList;
		List<PraiseBean> pbList;
		System.out.println("datas size:"+datas.size());
		for(FriendCommentsBean fc : datas){
			System.out.println(/*fc.getId() + */":::::::::::::::::::" + fc.getUid());
			cbList = fc.getCommentList();
			pbList = fc.getPraiseList();
			System.out.println("点赞列表*********************");
			for(PraiseBean pb: pbList){
				System.out.println(pb.getUid() + ":" + pb.getHeadUrl());
			}
			System.out.println("点赞列表#######################");
			System.out.println("评论列表*********************");
			for(CommentBean cb: cbList){
				System.out.println(cb.getUid() + ":" + cb.getHeadUrl()+":"+cb.getMsg());
			}
			System.out.println("评论列表#######################");
			System.out.println("##############################################");
		}
	}
}
