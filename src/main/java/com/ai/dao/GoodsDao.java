
package com.ai.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.entity.CategoryForShow;
import com.ai.entity.Goods;
import com.ai.entity.GoodsOfCatItems;
import com.ai.entity.Hotel;
import com.ai.entity.Scenic;
import com.ai.entity.Suggestion;
import com.ai.parser.BrandInfo;
import com.ai.parser.CatInfo;

public interface GoodsDao {  
	

	
	public Suggestion searchSuggestion(@Param("k") String k, @Param("v") String v);


	public void insertSuggestion(Suggestion temp);


	public void updateSuggestion(Suggestion temp);


	public List<Suggestion> loadSuggestion();


	public List<Goods> getAllName();
	public List<Hotel> getAllHotel(@Param("start") Integer start,@Param("leng") Integer leng);

	public int selectHotelNum();

	public List<Scenic> getAllScenic(@Param("start") Integer start,@Param("leng") Integer leng);

	public int selectScenicNum();
	
	public List<com.ai.parser.Goods> selectAllName(@Param("start") Integer start,@Param("leng") Integer leng);

	public int selectGoodsNum();
	
	public CatInfo getCatInfo(@Param("cat_id") int cat_id);


	public BrandInfo getBrandInfo(@Param("brand_id") Short brand_id);


	public List<CategoryForShow> selectShowCategory();


	public List<BrandInfo> getBrands();


	public List<CatInfo> getCats();
	
    
}