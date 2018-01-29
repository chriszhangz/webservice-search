package com.ai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.entity.Destination_new;

public interface DestinationNewDao 
{
	
	Destination_new selectDestinationByDesId(@Param("desId")int desId);
	int selectIdByDesName(@Param("desName")String desName);
	int countByCountryCity(@Param("originalRegion")String originalRegion,@Param("originalDest")String originalDest);
	int countByCountryStateCity(@Param("originalRegion")String originalRegion,@Param("originalState")String originalState,@Param("originalDest")String originalDest);
	int selectIdByCountryStateCity(@Param("originalRegion")String originalRegion,@Param("originalState")String originalState,@Param("originalDest")String originalDest);
	
	void insert(@Param("destList")List<Destination_new> destList);
	void insertSingle(Destination_new dest);
	Destination_new selectDestinationByDesPId(@Param("destinationPId")String destinationPId,@Param("zoneCode")int zoneCode,@Param("provider")String provider);
	void updateRegionIdBydesId(@Param("desUpdateList")List<Destination_new> desUpdateList);
	List<Destination_new> selectAllDestinations(@Param("start") Integer start,@Param("leng") Integer leng);
	public int selectDestNum();
}

