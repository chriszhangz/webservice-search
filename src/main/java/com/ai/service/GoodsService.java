package com.ai.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.parser.BrandInfo;
import com.ai.parser.CatInfo;
import com.ai.entity.GoodsOfCatItems;
import com.ai.entity.CategoryForShow;
import com.ai.entity.Goods;
import com.ai.entity.Hotel;
import com.ai.entity.Scenic;
import com.ai.entity.Suggestion;
import com.ai.entity.Destination_new;

public interface GoodsService {

	public Suggestion searchSuggestion(String k, String v);
	public void insertSuggestion(Suggestion temp);
	public void updateSuggestion(Suggestion temp);
	public List<Suggestion> loadSuggestion();
	public List<Goods> getAllName();
	
	public List<com.ai.parser.Goods> selectAllName(Integer start,Integer leng);
	public int selectGoodsNum();
	public List<Hotel> getAllHotel(Integer start,Integer leng);
	public int selectHotelNum();
	public List<Scenic> getAllScenic(Integer start,Integer leng);
	public int selectScenicNum();
	
	public CatInfo getCatInfo(Short catId);
	public BrandInfo getBrandInfo(Short brandId);
	public List<CategoryForShow> selectShowCategory();
	public List<BrandInfo> getBrands();
	public List<CatInfo> getCats();
	List<Destination_new> selectAllDestinations(Integer start,Integer leng);
	public int selectDestNum();
}
