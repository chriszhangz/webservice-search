
package com.ai.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.entity.CategoryForShow;
import com.ai.entity.Goods;
import com.ai.entity.GoodsOfCatItems;
import com.ai.entity.Suggestion;
import com.ai.parser.BrandInfo;
import com.ai.parser.CatInfo;

public interface SuggestionDao {  
	public Suggestion searchSuggestion(@Param("k") String k, @Param("v") String v);


	public void insertSuggestion(Suggestion temp);


	public void updateSuggestion(Suggestion temp);


	public List<Suggestion> loadSuggestion();
}