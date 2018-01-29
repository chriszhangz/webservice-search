package com.ai.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.entity.CategoryForShow;
import com.ai.entity.GoodsInCat;
import com.ai.service.GoodsService;
@Service
public class NewtonDownhill { 
	public CategoryTreeNode categoryTreeNode;
	@Autowired
	private GoodsService goodsService;
	
	public NewtonDownhill(List<GoodsInCat> temp)
	{
		//categoryTreeNode = new CategoryTreeNode();
		//List<CategoryForShow> tempList = goodsService.selectShowCategory();
		//this.NewtonDownhillTreeCatShowForGoods(categoryTreeNode, temp, tempList);

	}
	
	public void NewtonDownhilltest(List<GoodsInCat> temp)
	{
		categoryTreeNode = new CategoryTreeNode();
		System.out.println("test1");
		List<CategoryForShow> tempList = goodsService.selectShowCategory();
		System.out.println("test2");
		this.NewtonDownhillTreeCatShowForGoods(categoryTreeNode, temp, tempList);
		System.out.println("test3");

	}
	/*
	public static void main(String[]argv)
	{
		List<GoodsInCat> t=new ArrayList<GoodsInCat>();
		
		GoodsInCat tt= new GoodsInCat();
		tt.setCat_second_id(16);
		tt.setCat_third_id(101);
		t.add(tt);
		NewtonDownhill az= new NewtonDownhill(t);
		az.NewtonDownhillTest(t);
		System.out.println("finish");
	}
	*/
	public void NewtonDownhillTreeCatShowForGoods(CategoryTreeNode categoryTreeNode,List<GoodsInCat>temp, List<CategoryForShow> tempList)
	{
		        //templist delete temp
		        List<CategoryForShow> newTempList= new ArrayList<CategoryForShow>();
		        _STOP:
		        for(int i=0;i<temp.size();i++)
		        {
		        	 for(int j=0;j<tempList.size();j++)
				     {
		        		  if((temp.get(i).getCat_second_id()==tempList.get(j).getCat_id()))//+2级别分类
		        		  {
		        			 // newTempList.add(tempList.get(i));//+2级别分类
		        			  check(newTempList,tempList.get(j));
		        			  
		        			  for(int k=0;k<tempList.size();k++)
						      {
		        				  if(tempList.get(k).getCat_id()==tempList.get(j).getParent_id())
		        				  {
		        					 // newTempList.add(tempList.get(k));//加1级别分类
		        					  check(newTempList,tempList.get(k));
		        					  //加三级别分类
		        					  for(int l=0;l<tempList.size();l++)
								      {
		        						  if((temp.get(i).getCat_third_id()==tempList.get(l).getCat_id()))//+3级别分类
		        		        		  {
		        							  //newTempList.add(tempList.get(l));//加3级别分类
		        							  check(newTempList,tempList.get(l));	
		        							  //i+=1;
		        							  j=0;
						        			  continue _STOP;	
		        		        		  }
								      }
		        					 // i+=1;
		        					  j=0;
				        			  continue _STOP;		  
		        				  }
						      }  
		        		  }		  
				     }
		        }	
				//创建树头.
				categoryTreeNode.setParent_id(0);
				categoryTreeNode.setCat_id(0);
				categoryTreeNode.setCat_ename(YamiConstant.NODE_HEAD_ENGLISH);
				categoryTreeNode.setCat_name(YamiConstant.NODE_HEAD_CHINESE);
				categoryTreeNode.children = new LinkedList<CategoryTreeNode>();
				//察看是否有list
				if(newTempList != null)
				{
					if(newTempList.size()>0)
					{
						//遍历一次树头
						for(int j=0;j<newTempList.size();j++)
						{
							//是否是树头的叶子节点
							if(newTempList.get(j).getParent_id() == 0)
							{ 
								//temp赋值
								//check is_show
								if(newTempList.get(j).getIs_show() == 1)
								{
									CategoryTreeNode temp1 = new CategoryTreeNode();
									temp1.setCat_id(newTempList.get(j).getCat_id());
									temp1.setParent_id(newTempList.get(j).getParent_id());
									temp1.setCat_name(newTempList.get(j).getCat_name());
									temp1.setCat_ename(newTempList.get(j).getCat_ename());
									//添加temp
									categoryTreeNode.children.add(temp1);
								}
							}
						}
					}
				
					//获取children，线性代数,递归
					if(categoryTreeNode.children != null)
					{
						if(categoryTreeNode.children.size() != 0)
						{
							//递归查询子层.
							NewtonDownhillRecursionCheck(categoryTreeNode.children,newTempList);
						}
					}
				}
				//qu wuyong de zhi good_id
	}
	
	
	private void check(List<CategoryForShow> newTempList,CategoryForShow tempList) {
		for(int t=0;t<newTempList.size();t++)
		{
			if(newTempList.get(t).getCat_id()==tempList.getCat_id())
			{
				return;
			}
		}
		 newTempList.add(tempList);//+2级别分类
	}

	private void NewtonDownhillRecursionCheck(List<CategoryTreeNode> categoryTreeNode, List<CategoryForShow> tempList)
	{	
		//遍历线性代数.
		for(int i=0;i<categoryTreeNode.size();i++)
		{
			categoryTreeNode.get(i).children=new LinkedList<CategoryTreeNode>();
			//队列遍历
			for(int j=0;j<tempList.size();j++)
			{
				//牛顿下山法.循环便利数组,察看是否属于相同父节点.
				if(tempList.get(j).getParent_id() == categoryTreeNode.get(i).getCat_id())
				{
					//temp赋值
					//check is_show
					if(tempList.get(j).getIs_show() == 1)
					{
						CategoryTreeNode temp = new CategoryTreeNode();
						temp.setCat_id(tempList.get(j).getCat_id());
						temp.setParent_id(tempList.get(j).getParent_id());
						temp.setCat_name(tempList.get(j).getCat_name());
						temp.setCat_ename(tempList.get(j).getCat_ename());
						//牛顿下山法递减序列.减少2/3运算量.删除已经遍历过的list
						tempList.remove(tempList.get(j--));

						//添加符合匹配的list.
						categoryTreeNode.get(i).children.add(temp);
					}
				}
			}
			//QA检查是否创建节点.
			if(categoryTreeNode.get(i).children != null)
			{
				//QA检查是否有子节点
				if(categoryTreeNode.get(i).children.size() != 0)
				{
					//递归查询子层.
					NewtonDownhillRecursionCheck(categoryTreeNode.get(i).children,tempList);
				}
			}
		}
		//遍历
	}
	
} 
