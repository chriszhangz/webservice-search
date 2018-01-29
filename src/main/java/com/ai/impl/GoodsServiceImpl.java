package com.ai.impl;

import java.util.List;













import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.common.YamiConstant;
import com.ai.dao.DestinationNewDao;
import com.ai.dao.GoodsDao;
import com.ai.dao.SuggestionDao;
import com.ai.entity.Destination_new;
import com.ai.entity.GoodsOfCatItems;
import com.ai.entity.CategoryForShow;
import com.ai.entity.Goods;
import com.ai.entity.Hotel;
import com.ai.entity.Scenic;
import com.ai.entity.Suggestion;
import com.ai.parser.BrandInfo;
import com.ai.parser.CatInfo;
import com.ai.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService{
	@Autowired
	private DestinationNewDao destinationNewDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private SuggestionDao suggestionDao;
		@Override
		public Suggestion searchSuggestion(String k, String v) {
			// TODO Auto-generated method stub
			return suggestionDao.searchSuggestion(k,v);
		}

		@Override
		public void insertSuggestion(Suggestion temp) {
			// TODO Auto-generated method stub
			suggestionDao.insertSuggestion(temp);
		}

		@Override
		public void updateSuggestion(Suggestion temp) {
			// TODO Auto-generated method stub
			suggestionDao.updateSuggestion(temp);
		}

		@Override
		public List<Suggestion> loadSuggestion() {
			// TODO Auto-generated method stub
			//return goodsDao.loadSuggestion();	
			return suggestionDao.loadSuggestion();
		}

		@Override
		public List<Goods> getAllName() {
			// TODO Auto-generated method stub
			return goodsDao.getAllName();
		}

		@Override
		public List<com.ai.parser.Goods> selectAllName(Integer start,Integer leng) {
			// TODO Auto-generated method stub
			return goodsDao.selectAllName(start,leng);
		}
		
		@Override
		public CatInfo getCatInfo(Short catId) {
			// TODO Auto-generated method stub
			return goodsDao.getCatInfo(catId);
		}

		@Override
		public BrandInfo getBrandInfo(Short brandId) {
			// TODO Auto-generated method stub
			return goodsDao.getBrandInfo(brandId);
		}

		@Override
		public List<CategoryForShow> selectShowCategory() {
			// TODO Auto-generated method stub
			List<CategoryForShow> ShowCategory = goodsDao.selectShowCategory();
			return ShowCategory;
		}

		@Override
		public List<BrandInfo> getBrands() {
			// TODO Auto-generated method stub
			return goodsDao.getBrands();
		}

		@Override
		public List<CatInfo> getCats() {
			// TODO Auto-generated method stub
			return goodsDao.getCats();
		}

		@Override
		public int selectGoodsNum() {
			// TODO Auto-generated method stub
			return goodsDao.selectGoodsNum();
		}

		@Override
		public List<Destination_new> selectAllDestinations(Integer start,Integer leng) {
			return destinationNewDao.selectAllDestinations(start,leng);
		}

		@Override
		public int selectDestNum() {
			return destinationNewDao.selectDestNum();
		}

		@Override
		public List<Hotel> getAllHotel(Integer start, Integer leng) {
			return goodsDao.getAllHotel(start, leng);
		}

		@Override
		public int selectHotelNum() {
			return goodsDao.selectHotelNum();
		}

		@Override
		public List<Scenic> getAllScenic(Integer start, Integer leng) {
			return goodsDao.getAllScenic(start, leng);
		}

		@Override
		public int selectScenicNum() {
			return goodsDao.selectScenicNum();
		}
		
		
		
		
}
