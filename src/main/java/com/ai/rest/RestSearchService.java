package com.ai.rest;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.cache.timeCheck;
import com.ai.delegate.SearchServiceDelegate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@Path("/search")
public class RestSearchService {

	@Autowired
	private SearchServiceDelegate searchServicedelegate;
	private static Logger logger = LogManager.getLogger(RestSearchService.class.getName());
	
	@GET
	@Path("/importOldData")
	@Produces("application/json;charset=utf-8")
	public Response importOldKeywordData() throws Exception {
		List<String> result;
		searchServicedelegate.importOldKeywordData();
		return Response.status(Status.OK).entity("OK").build();
	}
	
	@GET
	@Path("/reindex")
	@Produces("application/json;charset=utf-8")
	public Response reindex(@QueryParam("from") String from_index,@QueryParam("to") String to_index) throws Exception {
		List<String> result;
		searchServicedelegate.reindex(from_index,to_index);
		return Response.status(Status.OK).entity("OK").build();
	}
	
	@GET
	@Path("/searchSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response searchSuggestion(@DefaultValue("") @QueryParam("input") String input) throws Exception {
		
		List<String> result = searchServicedelegate.searchSuggestion(input);
		return Response.status(Status.OK).entity(result).build();
	}
	@GET
	@Path("/makeHotSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response makeHotSuggestion() throws Exception {
		List<String> result;
		result = searchServicedelegate.makeHotSuggestion();
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/importGoodsInfo")
	@Produces("application/json;charset=utf-8")
	public Response testJestBulk() throws Exception {
		Map<String, Object> result;
		searchServicedelegate.importGoodsInfo();
		return Response.status(Status.OK).entity("OK").build();
	}	
	
	@GET
	@Path("/importDestInfo")
	@Produces("application/json;charset=utf-8")
	public Response importDestInfo() throws Exception {
		Map<String, Object> result;
		searchServicedelegate.importDestInfo();
		return Response.status(Status.OK).entity("OK").build();
	}	
	
	@GET
	@Path("/importHotelInfo")
	@Produces("application/json;charset=utf-8")
	public Response importHotelInfo() throws Exception {
		Map<String, Object> result;
		searchServicedelegate.importHotelInfo();
		return Response.status(Status.OK).entity("OK").build();
	}
	
	@GET
	@Path("/importScenicInfo")
	@Produces("application/json;charset=utf-8")
	public Response importScenicInfo() throws Exception {
		Map<String, Object> result;
		searchServicedelegate.importScenicInfo();
		return Response.status(Status.OK).entity("OK").build();
	}
	
	@GET
	@Path("/importKeyword")
	@Produces("application/json;charset=utf-8")
	public Response importKeyword() throws Exception {
		Map<String, Object> result;
		searchServicedelegate.importKeyword();
		return Response.status(Status.OK).entity("OK").build();
	}	
	
	@GET
	@Path("/createKeyWordsIndex")
	@Produces("application/json;charset=utf-8")
	public Response createKeyWordsIndex(@QueryParam("indexName") String indexName) throws Exception {
		Map<String, Object> result;
		searchServicedelegate.createKeyWordsIndex(indexName);
		return Response.status(Status.OK).entity("OK").build();
	}	
	
	@GET
	@Path("/createResultIndex")
	@Produces("application/json;charset=utf-8")
	public Response createResultIndex(@QueryParam("indexName") String indexName) throws Exception {
		Map<String, Object> result;
		searchServicedelegate.createResultIndex(indexName);
		return Response.status(Status.OK).entity("OK").build();
	}
	
	@GET
	@Path("/deleteIndex")
	@Produces("application/json;charset=utf-8")
	public Response deleteIndex(@QueryParam("indexName") String indexName) throws Exception {
		Map<String, Object> result = searchServicedelegate.deleteYamiIndex(indexName);
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/getSearchHistory")
	@Produces("application/json;charset=utf-8")
	public Response getSearchHistory(@QueryParam("token") String token,@QueryParam("input") String input) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.getSearchHistory(token,input);
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/clearSearchHistory")
	@Produces("application/json;charset=utf-8")
	public Response clearSearchHistory(@QueryParam("token") String token) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.clearSearchHistory(token);
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/searchResults")
	@Produces("application/json;charset=utf-8")
	public Response searchResults(
			@QueryParam("platform_id") String platform_id,@QueryParam("language") String language,@QueryParam("input") String input,
			@QueryParam("is_init") int is_init,
			@DefaultValue("0") @QueryParam("cat_id") String cat_id_string,
			@QueryParam("sort_by") int sort_by,
			@QueryParam("sort_order") int sort_order,
			@QueryParam("brands") String brands,
			@QueryParam("is_promote") int is_promote,
			@QueryParam("page") int page,
			@QueryParam("type") int type, //0 normal search;1 brand;2 vendor;3 category;4 exclude books;5 only books;6 tag
			@HeaderParam("User-Agent") String agent) throws Exception {
		Map<String, Object> result;
		if(platform_id == null || platform_id==""){
			platform_id="0";
		}			
		if(language == null || language==""){
			language="cn";
		}		
		if(page==0)
		{
			page=1;
		}
		if(cat_id_string.equals(""))
		{
			cat_id_string="0";
		}
		//sort_by=0;
		
		if(sort_by==3)
		{
			sort_by=0;
		}
		int cat_id= Integer.parseInt(cat_id_string);
		result = searchServicedelegate.searchResults("",platform_id,language,input,is_init,cat_id,sort_by,sort_order,brands,is_promote,page,agent,type);
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/searchResultsV2")
	@Produces("application/json;charset=utf-8")
	public Response searchResultsV2(@DefaultValue("") @QueryParam("token") String token,
			@QueryParam("platform_id") String platform_id,@QueryParam("language") String language,@QueryParam("input") String input,
			@QueryParam("is_init") int is_init,
			@DefaultValue("0") @QueryParam("cat_id") String cat_id_string,
			@QueryParam("sort_by") int sort_by,
			@QueryParam("sort_order") int sort_order,
			@QueryParam("brands") String brands,
			@QueryParam("is_promote") int is_promote,
			@QueryParam("page") int page,
			@QueryParam("type") int type, //0 normal search;1 brand;2 vendor;3 category;4 exclude books;5 only books;6 tag
			@HeaderParam("User-Agent") String agent) throws Exception {
		Map<String, Object> result;
		if(platform_id == null || platform_id==""){
			platform_id="0";
		}			
		if(language == null || language==""){
			language="cn";
		}
		if(page==0)
		{
			page=1;
		}
		if(cat_id_string.equals(""))
		{
			cat_id_string="0";
		}
		//sort_by=0;
		
		if(sort_by==3)
		{
			sort_by=0;
		}
		int cat_id= Integer.parseInt(cat_id_string);
		result = searchServicedelegate.searchResults(token,platform_id,language,input,is_init,cat_id,sort_by,sort_order,brands,is_promote,page,agent,type);
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/searchResultsV3")
	@Produces("application/json;charset=utf-8")
	public Response searchResultsV3(@DefaultValue("") @QueryParam("token") String token,
			@QueryParam("platform_id") String platform_id,@QueryParam("language") String language,@QueryParam("input") String input,
			@QueryParam("is_init") int is_init,
			@DefaultValue("0") @QueryParam("cat_id") String cat_id_string,
			@QueryParam("sort_by") int sort_by,
			@QueryParam("sort_order") int sort_order,
			@QueryParam("brands") String brands,
			@QueryParam("is_promote") int is_promote,
			@QueryParam("page") int page,
			@QueryParam("type") int type, //0 normal search;1 brand;2 vendor;3 category;4 exclude books;5 only books;6 tag
			@HeaderParam("User-Agent") String agent) throws Exception {
		Map<String, Object> result;
		if(platform_id == null || platform_id==""){
			platform_id="0";
		}			
		if(language == null || language==""){
			language="cn";
		}
		if(page==0)
		{
			page=1;
		}
		if(cat_id_string.equals(""))
		{
			cat_id_string="0";
		}
		//sort_by=0;
		
		if(sort_by==3)
		{
			sort_by=0;
		}
		int cat_id= Integer.parseInt(cat_id_string);
		result = searchServicedelegate.searchResultsV3(token,platform_id,language,input,is_init,cat_id,sort_by,sort_order,brands,is_promote,page,agent,type);
		return Response.status(Status.OK).entity(result).build();
	}		
	
	@GET
	@Path("/searchNameResults")
	@Produces("application/json;charset=utf-8")
	public Response searchNameResults(@QueryParam("input") String input) throws Exception {
		Map<String, Object> result;

		result = searchServicedelegate.searchNameResults(input);
		return Response.status(Status.OK).entity(result).build();
	}		
	/*@GET
	@Path("/makeSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response makeSuggestion(@DefaultValue("") @QueryParam("k") String k,
			                       @DefaultValue("") @QueryParam("v") String v,
			                       @DefaultValue("") @QueryParam("text") String text,
			                       @QueryParam("kdemo") BigDecimal kdemo,
			                       @QueryParam("vdemo") BigDecimal vdemo) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.makeSuggestion(k,v,text,kdemo,vdemo);
		return Response.status(Status.OK).entity(result).build();
	}
	@GET
	@Path("/startSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response startSuggestion(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.startSuggestion(text);
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/backupSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response backupSuggestion(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.backupSuggestion(text);
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/editVvalue")
	@Produces("application/json;charset=utf-8")
	public Response editVvalue(@DefaultValue("") @QueryParam("text") String text,@QueryParam("vrank") BigDecimal vrank,@QueryParam("value") BigDecimal value) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.editVvalue(text, vrank, value);
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/updateSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response updateSuggestion(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.updateSuggestion(text);
		return Response.status(Status.OK).entity(result).build();
	}
	@GET
	@Path("/updateEnglishSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response updateEnglishSuggestion(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.updateEnglishSuggestion(text);
		return Response.status(Status.OK).entity(result).build();
	}
	@GET
	@Path("/searchSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response searchSuggestion(@DefaultValue("") @QueryParam("input") String input) throws IOException {
		List<String> result;
		result = searchServicedelegate.searchSuggestion(input);
		return Response.status(Status.OK).entity(result).build();
	}
	@GET
	@Path("/makeHotSuggestions")
	@Produces("application/json;charset=utf-8")
	public Response makeHotSuggestion() throws IOException {
		List<String> result;
		result = searchServicedelegate.makeHotSuggestion();
		return Response.status(Status.OK).entity(result).build();
	}
	@GET
	@Path("/startResults")
	@Produces("application/json;charset=utf-8")
	public Response startResults(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.startResults(text);
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/flushResults")
	@Produces("application/json;charset=utf-8")
	public Response flashResults(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.flashResults(text);
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/gc")
	@Produces("application/json;charset=utf-8")
	public Response startGC(@DefaultValue("") @QueryParam("text") String text) throws IOException {
		Map<String, Object> result=new HashMap<String,Object>();;
		System.gc();
		result.put("result", "started GC");
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/searchResults")
	@Produces("application/json;charset=utf-8")
	public Response searchResults(@DefaultValue("") @QueryParam("input") String input,
			 @QueryParam("is_init") int is_init,
			@DefaultValue("0") @QueryParam("cat_id") String cat_id_string,
			@QueryParam("sort_by") int sort_by,
			@QueryParam("sort_order") int sort_order,
			@QueryParam("brands") String brands,
			@QueryParam("is_promote") int is_promote,
			@QueryParam("page") int page
			) throws IOException {
		
		//timeCheck global=new timeCheck();
		//global.begin();
		Map<String, Object> result;	
		if(page==0)
		{
			page=1;
		}
		if(cat_id_string.equals(""))
		{
			cat_id_string="0";
		}
		//sort_by=0;
		
		if(sort_by==3)
		{
			sort_by=0;
		}
		int cat_id= Integer.parseInt(cat_id_string);
		result = searchServicedelegate.searchResults(input,is_init,cat_id,sort_by,sort_order,brands,is_promote,page);
		//global.end();
		//logger.info("-----------------global="+global.duration());
		Runtime runtime = Runtime.getRuntime(); 
		int mb = 1024*1024; 
		if(runtime.freeMemory() / mb <1500)
		{
			//CacheManager.freeGC(runtime,mb);
			System.gc();
		}
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/searchResultsV2")
	@Produces("application/json;charset=utf-8")
	public Response searchResultsV2(@DefaultValue("") @QueryParam("token") String token,@QueryParam("input") String input,
			 @QueryParam("is_init") int is_init,
			@DefaultValue("0") @QueryParam("cat_id") String cat_id_string,
			@QueryParam("sort_by") int sort_by,
			@QueryParam("sort_order") int sort_order,
			@QueryParam("brands") String brands,
			@QueryParam("is_promote") int is_promote,
			@QueryParam("page") int page
			) throws IOException {
		
		//timeCheck global=new timeCheck();
		//global.begin();
		Map<String, Object> result;	
		if(page==0)
		{
			page=1;
		}
		if(cat_id_string.equals(""))
		{
			cat_id_string="0";
		}
		//sort_by=0;
		
		if(sort_by==3)
		{
			sort_by=0;
		}
		int cat_id= Integer.parseInt(cat_id_string);
		result = searchServicedelegate.searchResultsV2(token,input,is_init,cat_id,sort_by,sort_order,brands,is_promote,page);
		//global.end();
		//logger.info("-----------------global="+global.duration());
		Runtime runtime = Runtime.getRuntime(); 
		int mb = 1024*1024; 
		if(runtime.freeMemory() / mb <1500)
		{
			//CacheManager.freeGC(runtime,mb);
			System.gc();
		}
		return Response.status(Status.OK).entity(result).build();
	}
	
	@GET
	@Path("/searchGoods")
	@Produces("application/json;charset=utf-8")
	public Response searchGoods(@DefaultValue("") @QueryParam("input") String input,
			 @QueryParam("is_init") int is_init,
			@DefaultValue("0") @QueryParam("cat_id") String cat_id_string,
			@QueryParam("sort_by") int sort_by,
			@QueryParam("sort_order") int sort_order,
			@QueryParam("brands") String brands,
			@QueryParam("is_promote") int is_promote
			) throws IOException {
		
		//timeCheck global=new timeCheck();
		//global.begin();
		Map<String, Object> result;	
		if(cat_id_string.equals(""))
		{
			cat_id_string="0";
		}
		//sort_by=0;
		
		if(sort_by==3)
		{
			sort_by=0;
		}
		int cat_id= Integer.parseInt(cat_id_string);
		result = searchServicedelegate.searchGoods(input,is_init,cat_id,sort_by,sort_order,brands,is_promote);
		//global.end();
		//logger.info("-----------------global="+global.duration());
		Runtime runtime = Runtime.getRuntime(); 
		int mb = 1024*1024; 
		if(runtime.freeMemory() / mb <1500)
		{
			//CacheManager.freeGC(runtime,mb);
			System.gc();
		}
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/clearSearchHistory")
	@Produces("application/json;charset=utf-8")
	public Response clearSearchHistory(@QueryParam("token") String token) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.clearSearchHistory(token);
		return Response.status(Status.OK).entity(result).build();
	}	
	
	@GET
	@Path("/getSearchHistory")
	@Produces("application/json;charset=utf-8")
	public Response getSearchHistory(@QueryParam("token") String token,@QueryParam("input") String input) throws IOException {
		Map<String, Object> result;
		result = searchServicedelegate.getSearchHistory(token,input);
		return Response.status(Status.OK).entity(result).build();
	}	*/
		
}