package com.ai.delegate;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.SearchScroll;
import io.searchbox.core.Update;
import io.searchbox.core.search.aggregation.Aggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation.Entry;
import io.searchbox.indices.Analyze;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.aliases.RemoveAliasMapping;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;
import io.searchbox.params.SearchType;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ai.common.DateUtil;
import com.ai.common.ESFactory;
import com.ai.common.MemberPriceUtil;
import com.ai.common.StringUtil;
import com.ai.common.YamiConstant;
import com.ai.entity.CategoryForShow;
import com.ai.entity.Destination_new;
import com.ai.entity.GoodsInCat;
import com.ai.entity.GoodsInfo;
import com.ai.entity.GoodsNameInfo;
import com.ai.entity.Hotel;
import com.ai.entity.KeyWords;
import com.ai.entity.Scenic;
import com.ai.entity.Suggestion;
import com.ai.entity.Token;
import com.ai.parser.BrandInfo;
import com.ai.parser.CatInfo;
import com.ai.service.GoodsService;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

//import com.yamibuy.common.BrandNameSort;
@Service
public class SearchServiceDelegate {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private TransactionDelegate transactionDelegate;

	@Value("${RESULT_SIZE}")
	private String RESULT_SIZE;
	@Value("${MIN_SCORE}")
	private String MIN_SCORE;
	@Value("${PAGE_SIZE}")
	private Integer PAGE_SIZE;
	@Value("${RESULT_INDEX}")
	private String RESULT_INDEX;
	@Value("${RESULT_TYPE}")
	private String RESULT_TYPE;
	@Value("${KEYWORD_INDEX}")
	private String KEYWORD_INDEX;
	@Value("${KEYWORD_TYPE}")
	private String KEYWORD_TYPE;
	@Value("${CLUSTER_NAME}")
	private String CLUSTER_NAME;
	@Value("${NUMBER_OF_SHARDS}")
	private String NUMBER_OF_SHARDS;
	@Value("${NUMBER_OF_REPLICAS}")
	private String NUMBER_OF_REPLICAS;
	@Value("${EXCLUDE_VENDOR}")
	private String EXCLUDE_VENDOR;

	private JestHttpClient client = ESFactory.getClient();

	private Logger logger = LogManager.getLogger(this.getClass().getName());

/*	public void addBulkIndex(String token, String input) throws Exception {

		Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();
		Client client = new PreBuiltTransportClient(Settings.EMPTY)
				.builder()
				.settings(settings)
				.build()
				.addTransportAddress(
						new TransportAddress(InetAddress.getByName("host1"), 9300))
				.addTransportAddress(
						new TransportAddress(InetAddress.getByName("host2"), 9300));
		// Add transport addresses and do something with the client...

		client.close();
	}
*/
	@Test
	public void testJest() throws Exception {
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.129:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */
		String query = "{\n" + "    \"query\": {\n" + "                \"match\" : {\n"
				+ "                    \"content\" : \"中国\"\n" + "                }\n" + "    }\n"
				+ "}";
		Search.Builder searchBuilder = new Search.Builder(query).addIndex("index").addType(
				"fulltext");
		SearchResult result = client.execute(searchBuilder.build());
		System.out.println(result.getJsonString());

	}

	public void createResultIndex(String indexName) throws Exception {
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */
		// create index
		/*
		 * CreateIndex createIndex = new CreateIndex.Builder(indexName).build();
		 * JestResult jestResult = client.execute(createIndex);
		 * System.out.println(jestResult.toString());
		 */

		Settings.Builder settingsBuilder = Settings.builder();
		settingsBuilder.put("number_of_shards", NUMBER_OF_SHARDS);
		settingsBuilder.put("number_of_replicas", NUMBER_OF_REPLICAS);
		settingsBuilder.put("max_result_window", 500000);
		JestResult jestResult = client.execute(new CreateIndex.Builder(indexName).settings(
				settingsBuilder.build()).build());
		System.out.println(jestResult.getJsonString());
		// create mapping
		/*
		 * RootObjectMapper.Builder rootObjectMapperBuilder = new
		 * RootObjectMapper.Builder("my_mapping_name").add( new
		 * StringFieldMapper.Builder("goods_name").store(true) ).add( new
		 * StringFieldMapper.Builder("brands_name").store(true) );
		 * DocumentMapper documentMapper = new
		 * DocumentMapper.Builder(rootObjectMapperBuilder,null).build(null);
		 * String expectedMappingSource =
		 * documentMapper.mappingSource().toString(); PutMapping putMapping =
		 * new PutMapping.Builder( "goods_index", "my_type",
		 * expectedMappingSource ).build();
		 */
		URL url = SearchServiceDelegate.class.getResource("destName-mapping.json");
		String mapping = Resources.toString(url, Charsets.UTF_8);

		PutMapping putMapping = new PutMapping.Builder(indexName, RESULT_TYPE, mapping).build();

		JestResult result = client.execute(putMapping);
		if (!result.isSucceeded()) {
			System.out.println(String.format("Failed to create mapping: %s",
					result.getErrorMessage()));
		} else {
			System.out.println("Created mapping for index :" + indexName);
		}
	}

	public void createResultIndexAlias(String indexName) throws Exception {
		// add alias
		ModifyAliases modifyAliases = new ModifyAliases.Builder(new AddAliasMapping.Builder(
				(indexName), RESULT_INDEX).build()).build();

		JestResult result3 = client.execute(modifyAliases);
		if (!result3.isSucceeded()) {
			logger.error(String.format("Failed to create alias: %s", result3.getErrorMessage()));
		} else {
			logger.info("Created alias( " + RESULT_INDEX + " ) for index :" + indexName);
		}
	}

	public void createToIndex(String to_index) throws Exception {
		/*
		 * String indexName = "yami_search"; String typeName = "goods_info";
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */
		// create index
		/*
		 * CreateIndex createIndex = new CreateIndex.Builder(indexName).build();
		 * JestResult jestResult = client.execute(createIndex);
		 * System.out.println(jestResult.toString());
		 */

		//Settings.Builder settingsBuilder = Settings.settingsBuilder();
		//settingsBuilder.put("number_of_shards", NUMBER_OF_SHARDS);
		//settingsBuilder.put("number_of_replicas", NUMBER_OF_REPLICAS);
		String mappingName = "";
		String typeName = "";
		String settings = "";

		if(to_index.startsWith("key_words")){
			String setting = "\"analyzer\": {"+
			        "\"autocomplete\": {"+
			          "\"tokenizer\": \"autocomplete\","+
			          "\"filter\": ["+
			            "\"lowercase\" ] }, \"autocomplete_search\": { \"tokenizer\": \"lowercase\"  }  },"+
			      "\"tokenizer\": { \"autocomplete\": { \"type\": \"edge_ngram\", \"min_gram\": 1, \"max_gram\": 10, \"token_chars\": [\"letter\"  ] } }";			
			settings = "{\n" +
	                "        \"number_of_shards\" : "+NUMBER_OF_SHARDS+",\n" +
	                "        \"number_of_replicas\" : "+NUMBER_OF_REPLICAS+",\n" +
	                "        \"max_result_window\" : 500000,\n" +
	                "\"analysis\":{"+
	                setting +"}"+
	                "    }\n";			
			mappingName ="keyWords-mapping.json";
			typeName =  KEYWORD_TYPE;
			//settingsBuilder.put("analysis", setting);	
		}else{
			settings = "{\n" +
	                "        \"number_of_shards\" : "+NUMBER_OF_SHARDS+",\n" +
	                "        \"number_of_replicas\" : "+NUMBER_OF_REPLICAS+",\n" +
	                "        \"max_result_window\" : 500000\n" +
	                "    }\n";
			mappingName = "elastic-mapping.json";
			typeName =  RESULT_TYPE;		
		}
		
		JestResult jestResult = client.execute(new CreateIndex.Builder(to_index).settings(
				Settings.builder().loadFromSource(settings,XContentType.JSON).build()).build());
		if (!jestResult.isSucceeded()) {
			logger.fatal(String.format("Failed to create index " + to_index + ": %s",
					jestResult.getErrorMessage()));
		} else {
			logger.info("Created index :" + to_index);
		}		
		// create mapping
		/*
		 * RootObjectMapper.Builder rootObjectMapperBuilder = new
		 * RootObjectMapper.Builder("my_mapping_name").add( new
		 * StringFieldMapper.Builder("goods_name").store(true) ).add( new
		 * StringFieldMapper.Builder("brands_name").store(true) );
		 * DocumentMapper documentMapper = new
		 * DocumentMapper.Builder(rootObjectMapperBuilder,null).build(null);
		 * String expectedMappingSource =
		 * documentMapper.mappingSource().toString(); PutMapping putMapping =
		 * new PutMapping.Builder( "goods_index", "my_type",
		 * expectedMappingSource ).build();
		 */

		URL url = SearchServiceDelegate.class.getResource(mappingName);	
		String mapping = Resources.toString(url, Charsets.UTF_8);

		PutMapping putMapping = new PutMapping.Builder(to_index, typeName, mapping).build();

		JestResult result = client.execute(putMapping);
		if (!result.isSucceeded()) {
			logger.error(String.format("Failed to create mapping: %s", result.getErrorMessage()));
		} else {
			logger.info("Created mapping for index :" + to_index);
		}

	}

	
	public void createKeyWordsIndex(String indexName) throws Exception {
		long start = System.currentTimeMillis();
		// String indexName = "key_words";
		// String typeName = "goods_name";
		/*
		 * long start1 = System.currentTimeMillis(); if (deleteIndex(indexName))
		 * {
		 * 
		 * //System.out.println("Created mapping for index :" + indexName);long
		 * end = System.currentTimeMillis(); long end1 =
		 * System.currentTimeMillis();
		 * logger.info("delete Index ("+indexName+") take time -->> " + (end1 -
		 * start1) + " ms"); }
		 */
		//Settings.Builder settingsBuilder = Settings.settingsBuilder();
		//settingsBuilder.put("number_of_shards", NUMBER_OF_SHARDS);
		//settingsBuilder.put("number_of_replicas", NUMBER_OF_REPLICAS);

			String setting = "\"analyzer\": {"+
			        "\"autocomplete\": {"+
			          "\"tokenizer\": \"autocomplete\","+
			          "\"filter\": ["+
			            "\"lowercase\" ] }, \"autocomplete_search\": { \"tokenizer\": \"lowercase\"  }  },"+
			      "\"tokenizer\": { \"autocomplete\": { \"type\": \"edge_ngram\", \"min_gram\": 1, \"max_gram\": 10, \"token_chars\": [\"letter\"  ] } }";			
		String	settings = "{\n" +
	                "        \"number_of_shards\" : "+NUMBER_OF_SHARDS+",\n" +
	                "        \"number_of_replicas\" : "+NUMBER_OF_REPLICAS+",\n" +
	                "        \"max_result_window\" : 500000,\n" +
	                "\"analysis\":{"+
	                setting +"}"+
	                "    }\n";			

			//settingsBuilder.put("analysis", setting);	
	
		//JestResult jestResult = client.execute(new CreateIndex.Builder(KEYWORD_INDEX).settings(
		//		settingsBuilder.build().getAsMap()).build());
		JestResult jestResult = client.execute(new CreateIndex.Builder(indexName).settings(
				Settings.builder().loadFromSource(settings,XContentType.JSON).build()).build());		
		if (!jestResult.isSucceeded()) {
			logger.error(String.format("Failed to create index " + indexName + ": %s",
					jestResult.getErrorMessage()));
		}
		URL url = SearchServiceDelegate.class.getResource("keyWords-mapping.json");
		String mapping = Resources.toString(url, Charsets.UTF_8);

		PutMapping putMapping = new PutMapping.Builder(indexName, KEYWORD_TYPE, mapping)
				.build();

		JestResult result = client.execute(putMapping);
		if (!result.isSucceeded()) {
			logger.error(String.format("Failed to create mapping: %s",
					result.getErrorMessage()));
		} else {
			// System.out.println("Created mapping for index :" +
			// indexName);long end = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			logger.info("create index " + indexName + " take time -->> " + (end - start) + " ms");
		}
	}

	public Map<String, Object> deleteYamiIndex(String indexName) throws Exception {
		long start = System.currentTimeMillis();
		Map<String, Object> model = new HashMap<String, Object>();
		// String indexName = "key_words";
		// String typeName = "goods_name";

		if (deleteIndex(indexName)) {

			// System.out.println("Created mapping for index :" +
			// indexName);long end = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			logger.info("delete Index (" + indexName + ") take time -->> " + (end - start) + " ms");
		}
		model.put("status", YamiConstant.STATUS_OK);
		return model;
	}

	public void importOldKeywordData() throws Exception {
		logger.info("load db");
		// String indexName = "key_words";
		// String typeName = "goods_name";
		List<Suggestion> suggestion = goodsService.loadSuggestion();
		// System.out.println(suggestion.size());
		Gson gson = new Gson();
		List<String[]> list = new ArrayList<String[]>();
		String script = "{" + "    \"script\" : \"ctx._source.click_count += COUNTXXX\"}";
		logger.info("analyze db");
		for (int i = 0; i < suggestion.size(); i++) {
			// 查找是否有list
			int kfind = 0;

			JestResult result = client.execute(new Update.Builder(script.replace("COUNTXXX",
					suggestion.get(i).getVrank().toString())).index(KEYWORD_INDEX)
					.type(KEYWORD_TYPE).id(suggestion.get(i).getV()).build());
			if (!result.isSucceeded()) {
				System.out.println(String.format("Failed to update bulk data: %s",
						result.getErrorMessage()));
			}
		}
	}
	@Test
	public void importDestInfo() throws Exception {
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		// 批量新增的方式,效率更高
		Bulk.Builder bulkBuilder = new Bulk.Builder();

		Integer number = goodsService.selectDestNum();


		// 写取goods info, brand info ,cate info
		// List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		int mtime = number/4000;
		for (int m = 0; m <= mtime; m++) {
			logger.info("Read (" + m + "/" + mtime + ") 4000 from DB");
			List<Destination_new> goods = goodsService.selectAllDestinations(m * 4000, 4000);
			for (int i = 0; i < goods.size(); i++) {

				Index index = new Index.Builder(goods.get(i)).index("usitrip_search_v2").type(RESULT_TYPE).build();
				bulkBuilder.addAction(index);

			}
			JestResult result = client.execute(bulkBuilder.build());
			if (!result.isSucceeded()) {
				logger.fatal(String.format("Failed to insert bulk data: %s", result.getErrorMessage()));
			} else {
				logger.info("Successed inserting 4000 bulk data to :" + RESULT_INDEX);
			}
			bulkBuilder = new Bulk.Builder();


		}
		
		// client.shutdownClient();
	}
	public void importScenicInfo() throws Exception {
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		// 批量新增的方式,效率更高
		Bulk.Builder bulkBuilder = new Bulk.Builder();

		Integer number = goodsService.selectScenicNum();

		// 写取goods info, brand info ,cate info
		// List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		int mtime = number / 2000;
		for (int m = 0; m <= mtime; m++) {
			logger.info("Read (" + m + "/" + mtime + ") 2000 from DB");
			List<Scenic> goods = goodsService.getAllScenic(m * 2000, 2000);
			for (int i = 0; i < goods.size(); i++) {

				Index index = new Index.Builder(goods.get(i)).index("scenic_search_v3").type("scenic_info").build();
				bulkBuilder.addAction(index);

			}
			JestResult result = client.execute(bulkBuilder.build());
			if (!result.isSucceeded()) {
				logger.fatal(String.format("Failed to insert bulk data: %s", result.getErrorMessage()));
			} else {
				logger.info("Successed inserting 2000 bulk data to :" + "scenic_search");
			}
			bulkBuilder = new Bulk.Builder();

		}

	}	
	public void importHotelInfo() throws Exception {
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		// 批量新增的方式,效率更高
		Bulk.Builder bulkBuilder = new Bulk.Builder();

		Integer number = goodsService.selectHotelNum();

		// 写取goods info, brand info ,cate info
		// List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		int mtime = number / 2000;
		for (int m = 0; m <= mtime; m++) {
			logger.info("Read (" + m + "/" + mtime + ") 2000 from DB");
			List<Hotel> goods = goodsService.getAllHotel(m * 2000, 2000);
			for (int i = 0; i < goods.size(); i++) {

				Index index = new Index.Builder(goods.get(i)).index("hotel_search_v2").type("hotel_info").build();
				bulkBuilder.addAction(index);

			}
			JestResult result = client.execute(bulkBuilder.build());
			if (!result.isSucceeded()) {
				logger.fatal(String.format("Failed to insert bulk data: %s", result.getErrorMessage()));
			} else {
				logger.info("Successed inserting 2000 bulk data to :" + RESULT_INDEX);
			}
			bulkBuilder = new Bulk.Builder();

		}


	}	
	@Test
	public void importGoodsInfo() throws Exception {
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		// 批量新增的方式,效率更高
		Bulk.Builder bulkBuilder = new Bulk.Builder();

		Integer number = goodsService.selectGoodsNum();
		List<CategoryForShow> tempList = goodsService.selectShowCategory();
		List<BrandInfo> brandList = goodsService.getBrands();
		List<CatInfo> catList = goodsService.getCats();
		int times = 0;
		// 写取goods info, brand info ,cate info
		// List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		int mtime = number/4000;
		for(int m = 0;m<=mtime;m++){
			logger.info("Read ("+m+"/"+mtime+") 4000 from DB");
			List<com.ai.parser.Goods> goods = goodsService.selectAllName(m*4000,4000);
		for (int i = 0; i < goods.size(); i++) {
			//System.out.println("caculating i = " + i);
			String cnName = "";
			String enName = "";
			// System.out.println(i);
			GoodsInfo temp = new GoodsInfo();
			if (goods.get(i) != null) {
				// 0
				if (goods.get(i).getGoodsId() != null) {
					temp.setGoods_id(goods.get(i).getGoodsId());
				}
				// 1
				if (goods.get(i).getCatId() != null) {
					temp.setCat_id((int) goods.get(i).getCatId());
				}

				// 加2级别
				int second = addparent(goods.get(i).getCatId().intValue(), tempList);
				temp.setScat_id(second);

				int third = addparent(second, tempList);
				temp.setFcat_id(third);
				// 加1级别

				// 23
				CatInfo catInfo;
				try {
					catInfo = getCatInfo(catList, goods.get(i).getCatId());
				} catch (Exception e) {
					catInfo = null;
				}

				if (catInfo != null) {
					if (catInfo.getCat_name() != null) {
						temp.setCat_name(catInfo.getCat_name());
					} else {
						temp.setCat_name("");
					}
					if (catInfo.getCat_ename() != null) {
						temp.setCat_ename(catInfo.getCat_ename());
					} else {
						temp.setCat_ename("");
					}
				}
				// 4
				if (goods.get(i).getBrandId() != null) {
					temp.setBrand_id((int) goods.get(i).getBrandId());
				}
				// 56
				BrandInfo bandInfo;
				try {
					// bandInfo =
					// goodsService.getBrandInfo(goods.get(i).getBrandId());
					bandInfo = getBrandInfo(brandList, goods.get(i).getBrandId());
				} catch (Exception e) {
					bandInfo = null;
				}

				if (bandInfo != null) {
					if (bandInfo.getBrand_name() != null) {
						temp.setBrand_name(bandInfo.getBrand_name());
					} else {
						temp.setBrand_name("");
					}

					if (bandInfo.getBrand_ename() != null) {
						temp.setBrand_ename(bandInfo.getBrand_ename());
					} else {
						temp.setBrand_ename("");
					}
				}
				// 7
				if (goods.get(i).getGoodsName() != null) {
					cnName = StringUtil.NullToEmpty(goods.get(i).getPrefixCn())
							+ goods.get(i).getGoodsName()
							+ StringUtil.NullToEmpty(goods.get(i).getSuffixCn());
					temp.setGoods_name(cnName);
				}
				// 8
				if (goods.get(i).getGoodsEname() != null) {
					enName = StringUtil.NullToEmpty(goods.get(i).getPrefixEn())
							+ goods.get(i).getGoodsEname()
							+ StringUtil.NullToEmpty(goods.get(i).getSuffixEn());
					temp.setGoods_ename(enName);
				}
				// 9
				if (goods.get(i).getClickCount() != null) {
					temp.setClick_count(goods.get(i).getClickCount());
				}
				// 10
				if (goods.get(i).getFirstAddTime() != null) {
					temp.setFirst_add_time(goods.get(i).getFirstAddTime());
				}
				// 11
				if (goods.get(i).getIsPromote() != null) {
					temp.setIs_promote((int) goods.get(i).getIsPromote());
				}
				if (goods.get(i).getIsDelete() != null) {
					temp.setIs_delete((int) goods.get(i).getIsDelete());
				}
				if (goods.get(i).getIsOnSale() != null) {
					temp.setIs_on_sale((int) goods.get(i).getIsOnSale());
				}	
				if (goods.get(i).getItemNumber() != null) {
					temp.setItem_number( goods.get(i).getItemNumber());
				}				
				// 12
				if (goods.get(i).getPromotePrice() != null) {
					temp.setPromote_price(goods.get(i).getPromotePrice());
				}
				// 13
				if (goods.get(i).getShopPrice() != null) {
					temp.setShop_price(goods.get(i).getShopPrice());
				}
				// 14
				if (goods.get(i).getGoodsNumber() != null) {
					temp.setGoods_number((int) goods.get(i).getGoodsNumber());
					if(goods.get(i).getGoodsNumber()>0){
						temp.setHas_number(1);
					}
				}
				// 15
				if (goods.get(i).getGoodsThumb() != null) {
					// temp.add(goods.get(i).getGoodsThumb());
					temp.setImage(goods.get(i).getGoodsImg());

				}
				// 16
				if (catInfo != null) {
					temp.setParent_id((int) catInfo.getParent_id());
				}
				// 17

				if (bandInfo != null) {
					if (null != bandInfo.getAlphabetic_index()
							&& StringUtil.IsAlphabet(bandInfo.getAlphabetic_index())) {
						temp.setAlphabetic_index(bandInfo.getAlphabetic_index());
					}
				}
				if (goods.get(i).getVendorId() != null) {
					temp.setVendor_id((int)goods.get(i).getVendorId());
					if(goods.get(i).getVendorId()==17){
						temp.setIs_book(1);
					}
				}
				if (goods.get(i).getPromoteStartDate() != null) {
					// temp.add(goods.get(i).getGoodsThumb());
					temp.setPromote_start_date(goods.get(i).getPromoteStartDate());
				}
				if (goods.get(i).getPromoteEndDate() != null) {
					// temp.add(goods.get(i).getGoodsThumb());
					temp.setPromote_end_date(goods.get(i).getPromoteEndDate());
				}	
				if (goods.get(i).getTagId() != null) {
					temp.setTag_id(goods.get(i).getTagId());
				}
				if (goods.get(i).getTag() != null) {
					temp.setTag(goods.get(i).getTag());
				}
				if (goods.get(i).getTagEng() != null) {
					temp.setTag_eng(goods.get(i).getTagEng());
				}
				if (goods.get(i).getStatus() != null) {
					temp.setStatus(goods.get(i).getStatus());
				}				
				if (goods.get(i).getUpcCode() != null&&goods.get(i).getUpcCode() !="") {
					if(isNumeric(goods.get(i).getUpcCode())){
					temp.setUpc_code(goods.get(i).getUpcCode());
					}
				}
				
				Index index = new Index.Builder(temp).index(RESULT_INDEX).type(RESULT_TYPE).build();
				bulkBuilder.addAction(index);
				// list.add(temp);
				times++;
				if(times == 4000){
					JestResult result = client.execute(bulkBuilder.build());
					if (!result.isSucceeded()) {
						logger.fatal(String.format("Failed to insert bulk data: %s",
								result.getErrorMessage()));
					} else {
						logger.info("Successed inserting 4000 bulk data to :" + RESULT_INDEX);
					}
					bulkBuilder = new Bulk.Builder();
					times = 0;
				}
			}
		}
		}
		if(times !=0){
		JestResult result = client.execute(bulkBuilder.build());
		if (!result.isSucceeded()) {
			logger.fatal(String.format("Failed to insert bulk data: %s",
					result.getErrorMessage()));
		} else {
			logger.info("Created inserting bulk data :" + RESULT_INDEX);
		}
		}
		// client.shutdownClient();
	}
	public Map<String, Object> searchNameResults(String input)
			throws Exception {
		Map<String, Object> end = new HashMap<String, Object>();
		if(input == null){
			input="";
		}else{
			input=input.trim().replace("\\", "").replace("\"", "");
		}
		if (input.length() > 40) {
			input = input.substring(0, 40);
		}
		char[] charArray = input.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			for (int j = 0; j < YamiConstant.TRAD_DIC.length(); j++) {
				if (charArray[i] == YamiConstant.TRAD_DIC.charAt(j)) {
					charArray[i] = YamiConstant.SIMP_DIC.charAt(j);
				}
			}
		}
		input = String.valueOf(charArray);	
		
		String searchJson = "";
		List<Object> result = new ArrayList<Object>();
		URL url = SearchServiceDelegate.class.getResource("elastic-searchDestResult.json");
		searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input);
		Search.Builder searchBuilder = new Search.Builder(searchJson).addIndex("usitrip_search")
				.addType("dest_info");
		SearchResult sresult = client.execute(searchBuilder.build());
		List<SearchResult.Hit<Destination_new, Void>> hits = sresult.getHits(Destination_new.class);
		for (int i1 = 0; i1 < hits.size(); i1++) {
			Destination_new strArray = hits.get(i1).source;
			// list.remove(i1);
			// i1-=1;
			Map<String, Object> output = new HashMap<String, Object>();

			output.put("name", strArray.getDesName());
			output.put("name_zh", strArray.getDesName_zh());
			// 添加括号
			if(hits.get(i1).highlight!=null){
			output.put("name_mark",
					hits.get(i1).highlight.get("desName") == null ? strArray.getDesName()
							: hits.get(i1).highlight.get("desName").get(0));
			output.put(
					"name_zh_mark",
					hits.get(i1).highlight.get("desName_zh") == null ? strArray
							.getDesName_zh() : hits.get(i1).highlight.get("desName_zh")
							.get(0));
			}else{
				output.put("name_mark",strArray.getDesName());
				output.put("name_zh_mark",strArray.getDesName_zh());
			}
			output.put("id",strArray.getDesId());
			output.put("type", "destination");
			if(strArray.getDesName_zh()!=null&&!"".equals(strArray.getDesName_zh().trim())){
				output.put("display", output.get("name_mark")+"("+output.get("name_zh_mark")+")");
			}else{
				output.put("display", output.get("name_mark"));
			}
			result.add(output);
		}

		URL url2 = SearchServiceDelegate.class.getResource("elastic-searchHotelResult.json");
		searchJson = Resources.toString(url2, Charsets.UTF_8).replace("MATCHXXX", input);
		Search.Builder searchBuilder2 = new Search.Builder(searchJson).addIndex("hotel_search")
				.addType("hotel_info");
		SearchResult sresult2 = client.execute(searchBuilder2.build());
		List<SearchResult.Hit<Hotel, Void>> hits2 = sresult2.getHits(Hotel.class);
		for (int i1 = 0; i1 < hits2.size(); i1++) {
			Hotel strArray = hits2.get(i1).source;
			// list.remove(i1);
			// i1-=1;
			Map<String, Object> output = new HashMap<String, Object>();

			output.put("name", strArray.getName());
			output.put("name_zh", strArray.getName_zh());
			// 添加括号
			if(hits2.get(i1).highlight!=null){
			output.put("name_mark",
					hits2.get(i1).highlight.get("name") == null ? strArray.getName()
							: hits2.get(i1).highlight.get("name").get(0));
			output.put(
					"name_zh_mark",
					hits2.get(i1).highlight.get("name_zh") == null ? strArray
							.getName_zh() : hits2.get(i1).highlight.get("name_zh")
							.get(0));
			}else{
				output.put("name_mark",strArray.getName());
				output.put("name_zh_mark",strArray.getName_zh());
			}
			output.put("city", strArray.getCity());
			output.put("countryCode", strArray.getCountryCode());
			output.put("id",strArray.getHotelId());
			String ms = "";
			if(strArray.getCity()!=null&&!"".equals(strArray.getCity().trim())){
				ms=", "+strArray.getCity()+", "+strArray.getCountryCode();
			}else{
				ms=", "+strArray.getCountryCode();
			}
			if(strArray.getName_zh()!=null&&!"".equals(strArray.getName_zh().trim())){
				output.put("display", output.get("name_mark")+"("+output.get("name_zh_mark")+")"+ms);
			}else{
				output.put("display", output.get("name_mark")+ms);
			}
			output.put("type", "hotel");
			result.add(output);
		}
		URL url3 = SearchServiceDelegate.class.getResource("elastic-searchScenicResult.json");
		searchJson = Resources.toString(url3, Charsets.UTF_8).replace("MATCHXXX", input);
		Search.Builder searchBuilder3 = new Search.Builder(searchJson).addIndex("scenic_search")
				.addType("scenic_info");
		SearchResult sresult3 = client.execute(searchBuilder3.build());
		List<SearchResult.Hit<Scenic, Void>> hits3 = sresult3.getHits(Scenic.class);
		for (int i1 = 0; i1 < hits3.size(); i1++) {
			Scenic strArray = hits3.get(i1).source;
			// list.remove(i1);
			// i1-=1;
			Map<String, Object> output = new HashMap<String, Object>();

			output.put("name", strArray.getName());
			output.put("name_zh", strArray.getName_zh());
			output.put("name_full", strArray.getName_full());
			output.put("scenic_type", strArray.getType());
/*			// 添加括号
			if(hits2.get(i1).highlight!=null){
			output.put("name_mark",
					hits2.get(i1).highlight.get("name") == null ? strArray.getName()
							: hits2.get(i1).highlight.get("name").get(0));
			output.put(
					"name_zh_mark",
					hits2.get(i1).highlight.get("name_zh") == null ? strArray
							.getName_zh() : hits2.get(i1).highlight.get("name_zh")
							.get(0));
			}else{
				output.put("name_mark",strArray.getName());
				output.put("name_zh_mark",strArray.getName_zh());
			}*/
			output.put("destination_id", strArray.getDestination_id());
			output.put("destination_name", strArray.getDestination_name());
			output.put("destination_name_zh", strArray.getDestination_name_zh());
			output.put("id",strArray.getId());

			output.put("type", "scenic");
			result.add(output);
		}
		URL url4 = SearchServiceDelegate.class.getResource("elastic-searchAirportResult.json");
		searchJson = Resources.toString(url4, Charsets.UTF_8).replace("MATCHXXX", input);
		Search.Builder searchBuilder4 = new Search.Builder(searchJson).addIndex("scenic_search")
				.addType("scenic_info");
		SearchResult sresult4 = client.execute(searchBuilder4.build());
		List<SearchResult.Hit<Scenic, Void>> hits4 = sresult4.getHits(Scenic.class);
		for (int i1 = 0; i1 < hits4.size(); i1++) {
			Scenic strArray = hits4.get(i1).source;
			// list.remove(i1);
			// i1-=1;
			Map<String, Object> output = new HashMap<String, Object>();

			output.put("name", strArray.getName());
			output.put("name_zh", strArray.getName_zh());
			output.put("name_short", strArray.getName_short());
			output.put("name_full", strArray.getName_full());
			output.put("scenic_type", strArray.getType());
/*			// 添加括号
			if(hits2.get(i1).highlight!=null){
			output.put("name_mark",
					hits2.get(i1).highlight.get("name") == null ? strArray.getName()
							: hits2.get(i1).highlight.get("name").get(0));
			output.put(
					"name_zh_mark",
					hits2.get(i1).highlight.get("name_zh") == null ? strArray
							.getName_zh() : hits2.get(i1).highlight.get("name_zh")
							.get(0));
			}else{
				output.put("name_mark",strArray.getName());
				output.put("name_zh_mark",strArray.getName_zh());
			}*/
			output.put("destination_id", strArray.getDestination_id());
			output.put("destination_name", strArray.getDestination_name());
			output.put("destination_name_zh", strArray.getDestination_name_zh());
			output.put("id",strArray.getId());

			output.put("type", "airport");
			result.add(output);
		}		
		end.put("result", result);
		//end.put("total", sresult.getTotal());
		return end;
	}
	public Map<String, Object> searchResults(String token,String platform_id,String language,String input, int is_init,
			int cat_id, int sort_by, int sort_order, String brands, int is_promote, int page, String agent, int type)
			throws Exception {
		/*long start = System.currentTimeMillis();*/
		// Map<String, Object> model = new HashMap<String, Object>();
		// String RESULT_SIZE = "400";
		// String MIN_SCORE = "0.35";
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		String sortWord = "";
		String sortOrder = "desc";
		int fcat_id=0;
		int scat_id=0;
		if(input == null){
			input="";
		}else{
			input=input.trim().replace("\\", "").replace("\"", "");
		}
		if (input.length() > 40) {
			input = input.substring(0, 40);
		}
		char[] charArray = input.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			for (int j = 0; j < YamiConstant.TRAD_DIC.length(); j++) {
				if (charArray[i] == YamiConstant.TRAD_DIC.charAt(j)) {
					charArray[i] = YamiConstant.SIMP_DIC.charAt(j);
				}
			}
		}
		input = String.valueOf(charArray);		
		try {
			if(type==4 && token!=null && !("").equals(token) && !("").equals(input)&&!isNumeric(input)){
				setSearchHistory(token,input);
			}
		} catch (Exception e1) {
			//logger.fatal("setSearchHistory failed:" + input+" message:"+e1.getMessage());
			//e1.printStackTrace();
		}
		
		//默认为手机配置
		int index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
		int ITEMS_PER_PAGE = YamiConstant.ITEMS_PER_PAGE_MOBILE;

		if(agent==null||agent.trim().equals(YamiConstant.STRING_EMPTY)){
			agent = "android";
		}
		//System.out.println(agent.toString());
		//识别调试器,完成调试器配置
		Pattern pattern = Pattern.compile(YamiConstant.REGEX_SOAPUI, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find soapUI ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
    		ITEMS_PER_PAGE = YamiConstant.ITEMS_PER_PAGE_MOBILE;
        }
	    //识别苹果手机
        pattern = Pattern.compile(YamiConstant.REGEX_IPHONE, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find iphone ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_MOBILE;
        }
        //识别三星系列智能设备.
        pattern = Pattern.compile(YamiConstant.REGEX_ANDROID, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find android ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_MOBILE;
        }
        //识别火狐浏览器
        pattern = Pattern.compile(YamiConstant.REGEX_FIREFOX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find firefox ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
		//识别windows平台浏览器
        pattern = Pattern.compile(YamiConstant.REGEX_IE, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find ie ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
        //识别chrome浏览器
        pattern = Pattern.compile(YamiConstant.REGEX_CHROME, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find chrome ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
        //识别ipad平台.
        pattern = Pattern.compile(YamiConstant.REGEX_IPAD, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find ipad ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
        //识别mac平台.
        pattern = Pattern.compile(YamiConstant.REGEX_MAC, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find imac ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */
        /*long end1 = System.currentTimeMillis();
		logger.info("step1 take time -->> " + (end1 - start) + " ms");*/
		switch (sort_by) {
		case 0:
		case 3:
			sortWord = "_score";
			break;
		case 2:
			sortWord = "click_count";
			break;
		case 4:
			sortWord = "shop_price";
			if (sort_order == 1) {
				sortOrder = "asc";
			}
			break;
		default:
			sortWord = "_score";
			break;
		}
		/*
		 * String query = "{\n" + "    \"size\": " + size + ",\n" +
		 * "    \"min_score\": " + min_score + ",\n" + "    \"query\": {\n" +
		 * "                \"match\" : {\n" +
		 * "                    \"goods_name\" : \"" + input + "\"\n" +
		 * "                }\n" + "    }\n" + "}";
		 */
		Long now = DateUtil.getUnixTime();
		String filter = ",\n"
				+ "    \"filter\":{"
				+ "    \"bool\": {MUNOT    \"must\":[{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
		if (platform_id.equals("1")) {
			filter = ",\n"
					+ "    \"filter\":{"
					+ "    \"bool\": {MUNOT    \"must\":[{\"term\":{\"vendor_id\": 0 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			if (is_promote == 1) {
				filter = ",\n"
						+ "    \"filter\":{"
						+ "    \"bool\": {MUNOT    \"must\":[{\"range\":{\"promote_start_date\": {\"lte\": "+now+"}}},{\"range\":{\"promote_end_date\": {\"gte\": "+now+"}}},{\"term\":{\"vendor_id\": 0 }},{\"term\":{\"is_promote\": 1 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			}
		} else {
			if (is_promote == 1) {
				filter = ",\n"
						+ "    \"filter\":{"
						+ "    \"bool\": {MUNOT    \"must\":[{\"range\":{\"promote_start_date\": {\"lte\": "+now+"}}},{\"range\":{\"promote_end_date\": {\"gte\": "+now+"}}},{\"term\":{\"is_promote\": 1 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			}
		}

		Map<String, Object> end = new HashMap<String, Object>();
		
		Map<String, Object> brandAlphaMap;
		// System.out.println(items.size());
		int page_count;
		// make category
		try {
			String searchJson = "";
			if(type == 1){
				//brand
				brands = input;
				URL url = SearchServiceDelegate.class.getResource("elastic-brand.json");
				searchJson = Resources.toString(url, Charsets.UTF_8)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);	
			}else if(type == 2){
				//vendor
				URL url = SearchServiceDelegate.class.getResource("elastic-brand.json");
				searchJson = Resources.toString(url, Charsets.UTF_8)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);
				filter = filter.replace("MSTXXX", ",{ \"term\":{ \"vendor_id\":\"" + input
						+ "\"}}]");
			}else if(type == 6){
				//tag
				URL url = SearchServiceDelegate.class.getResource("elastic-searchTag.json");
				searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);
			}else if(isNumeric(input)){
				//upc
				URL url = SearchServiceDelegate.class.getResource("elastic-searchUpc.json");
				searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);								
			}else if(input.equals("")){
				URL url = SearchServiceDelegate.class.getResource("elastic-category.json");
				searchJson = Resources.toString(url, Charsets.UTF_8)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);	
			
			} else {
				URL url = SearchServiceDelegate.class.getResource("elastic-searchResult.json");
				searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);
			}
			if (null != MIN_SCORE) {
				searchJson = searchJson.replace("MINSCOREXXX", MIN_SCORE);
			}			
			// in order to implement category.
			if(cat_id>=0 && cat_id<16){
				fcat_id = cat_id;
				cat_id = 0;
			}else if(cat_id>=16 && cat_id <=99){
				scat_id = cat_id;
				cat_id = 0;				
			}
			if (null != brands && brands.trim().length() != 0) {
				// int[] brand_id_int = new int[brands.split(",").length];
				if(language.equals("en")||type==4){
				filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
						+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
						+ "}}],");
				}else{
					filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
							+ "}},");
				}
				String[] brand_ids = brands.replace("(", "").replace(")", "").split(",");
				String brandFilter = "";
				for (int i = 0; i < brand_ids.length; i++) {
					String brand_id = brand_ids[i].trim();
					if (i == 0) {
						brandFilter += "\"should\":[";
					}
					if (i > 0) {
						brandFilter += ",";
					}
					brandFilter += "{\"term\":{\"brand_id\":\"" + brand_id + "\"}}";
					if (i == brand_ids.length - 1) {
						brandFilter += "]";
					}
				}
				filter = filter.replace("SHOULD", brandFilter);
				if (cat_id != 0) {
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"cat_id\":\"" + cat_id
							+ "\"}}],");
				} else if (fcat_id != 0) {
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"fcat_id\":\"" + fcat_id
							+ "\"}}],");
				} else if (scat_id != 0) {
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"scat_id\":\"" + scat_id
							+ "\"}}],");
				} else {
					filter = filter.replace("MSTXXX", "],");
				}
				searchJson = searchJson.replace("FILTERXXX", filter);
			} else {

				if (cat_id != 0) {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						}else{
							filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
									+ "}},");
						}
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"cat_id\":\"" + cat_id
							+ "\"}}]");
					filter = filter.replace("SHOULD", "");
					searchJson = searchJson.replace("FILTERXXX", filter);
				} else if (fcat_id != 0) {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						}else{
							filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
									+ "}},");
						}
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"fcat_id\":\"" + fcat_id
							+ "\"}}]");
					filter = filter.replace("SHOULD", "");
					searchJson = searchJson.replace("FILTERXXX", filter);
				} else if (scat_id != 0) {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						}else{
							filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
									+ "}},");
						}
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"scat_id\":\"" + scat_id
							+ "\"}}]");
					filter = filter.replace("SHOULD", "");
					searchJson = searchJson.replace("FILTERXXX", filter);
				} else {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						filter = filter.replace("SHOULD", "");
						filter = filter.replace("MSTXXX", "]");
						searchJson = searchJson.replace("FILTERXXX", filter);
					}else{
						filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
								+ "}},");
						filter = filter.replace("SHOULD", "");
						filter = filter.replace("MSTXXX", "]");
						searchJson = searchJson.replace("FILTERXXX", filter);
						//searchJson = searchJson.replace("FILTERXXX", "");
					}
				}
			}
	        /*long end2 = System.currentTimeMillis();
			logger.info("step2 take time -->> " + (end2 - end1) + " ms");*/
			Search.Builder searchBuilder = new Search.Builder(searchJson).addIndex(RESULT_INDEX)
					.addType(RESULT_TYPE).setParameter("from", index)
					.setParameter("size", ITEMS_PER_PAGE);
			SearchResult sresult = client.execute(searchBuilder.build());

			List<SearchResult.Hit<GoodsInfo, Void>> hits = sresult.getHits(GoodsInfo.class);
			if(!"".equals(input)&&hits.size()!=0&&(type==0||type==4)&&!isNumeric(input)){
				markKeyword(input);
			}
	        /*long end3 = System.currentTimeMillis();
			logger.info("step3 take time -->> " + (end3 - end2) + " ms");*/
			// client.shutdownClient();
			// List<GoodsInfo> goodsList =
			// sresult.getSourceAsObjectList(GoodsInfo.class);
			List<Object> result = new ArrayList<Object>();
			//List<Object> outWithZero = new ArrayList<Object>();
			List<Object> items = new ArrayList<Object>();
			brandAlphaMap = new LinkedHashMap<String, Object>();
			TermsAggregation terms = sresult.getAggregations().getTermsAggregation("alphabetic");
			if(terms!=null){
			List<Entry> buckets = terms.getBuckets();
			for (Entry bucket : buckets) {
				String key = bucket.getKey();
				List<Object> number = new ArrayList<Object>();
				TermsAggregation terms2 = bucket.getTermsAggregation("brandids");
				List<Entry> buckets2 = terms2.getBuckets();
				for (Entry bucket2 : buckets2) {
					Map<String, Object> tempAlpha = new LinkedHashMap<String, Object>();
					tempAlpha.put("brand_id", bucket2.getKey());
					tempAlpha.put("hits", bucket2.getCount());
					TermsAggregation terms3 = bucket2.getTermsAggregation("brandnames");
					List<Entry> buckets3 = terms3.getBuckets();
					if(buckets3.size()!=0){
					tempAlpha.put("brand_name", buckets3.get(0).getKey());
					}else{
						tempAlpha.put("brand_name", "");						
					}
					if(buckets3.get(0).getTermsAggregation("brandenames").getBuckets().size()!=0){
					Entry bucket4 = buckets3.get(0).getTermsAggregation("brandenames").getBuckets()
							.get(0);
					tempAlpha.put("brand_ename", bucket4.getKey());}
					else{
						tempAlpha.put("brand_ename", "");
					}
					
					number.add(tempAlpha);
				}
				brandAlphaMap.put(key, number);
			}
			}
	        /*long end4 = System.currentTimeMillis();
			logger.info("step4 take time -->> " + (end4 - end3) + " ms");*/
			List<CategoryForShow> tempList =  new ArrayList<CategoryForShow>();
			//List<CategoryForShow> tempList = goodsService.selectShowCategory();
			Gson gson = new Gson();  
			if(null != redisTemplate.opsForValue().get("showCategory")){
				tempList= gson.fromJson(redisTemplate.opsForValue().get("showCategory"),new TypeToken<List<CategoryForShow>>() {  
                }.getType());
			}else{
				tempList=goodsService.selectShowCategory();
				redisTemplate.opsForValue().set("showCategory", gson.toJson(tempList), 1, TimeUnit.DAYS);
			}
			
	        /*long end5 = System.currentTimeMillis();
			logger.info("step5 take time -->> " + (end5 - end4) + " ms");*/
			List<Object> fcategories = new ArrayList<Object>();
			TermsAggregation fcatids = sresult.getAggregations().getTermsAggregation("fcat_ids");
			if(fcatids!=null){
			List<Entry> fcatidsBuckets = fcatids.getBuckets();

			for (Entry fb : fcatidsBuckets) {
				String fkey = fb.getKey();
				Map<String, Object> fcats = new LinkedHashMap<String, Object>();
				Map<String, String> fcatPojo = findCatName(fkey, tempList);
				if(fcatPojo.get("is_show")!=null&&fcatPojo.get("is_show").equals("1")){
				fcats.put("cat_id", fkey);
				fcats.put("cat_name", fcatPojo.get("cat_name"));
				fcats.put("cat_ename", fcatPojo.get("cat_ename"));
				fcats.put("parent_id", fcatPojo.get("parent_id"));
				fcats.put("hits", fb.getCount());

				TermsAggregation scatids = fb.getTermsAggregation("scat_ids");
				List<Entry> scatidsBuckets = scatids.getBuckets();
				List<Object> scategories = new ArrayList<Object>();
				for (Entry sb : scatidsBuckets) {
					String skey = sb.getKey();
					Map<String, Object> scats = new LinkedHashMap<String, Object>();
					Map<String, String> scatPojo = findCatName(skey, tempList);
					if(scatPojo.get("is_show")!=null&&scatPojo.get("is_show").equals("1")){
					scats.put("cat_id", skey);
					scats.put("cat_name", scatPojo.get("cat_name"));
					scats.put("cat_ename", scatPojo.get("cat_ename"));
					scats.put("parent_id", scatPojo.get("parent_id"));
					scats.put("hits", sb.getCount());

					TermsAggregation catids = sb.getTermsAggregation("cat_ids");
					List<Entry> catidsBuckets = catids.getBuckets();
					List<Object> categories = new ArrayList<Object>();
					for (Entry b : catidsBuckets) {
						String key = b.getKey();
						Map<String, Object> cats = new LinkedHashMap<String, Object>();
						Map<String, String> catPojo = findCatName(key, tempList);
						if(catPojo.get("is_show")!=null&&catPojo.get("is_show").equals("1")){
						cats.put("cat_id", key);
						cats.put("cat_name", catPojo.get("cat_name"));
						cats.put("cat_ename", catPojo.get("cat_ename"));
						cats.put("parent_id", catPojo.get("parent_id"));
						cats.put("hits", b.getCount());
						categories.add(cats);
						}
					}
					scats.put("children", categories);
					scategories.add(scats);
					}
				}

				fcats.put("children", scategories);
				fcategories.add(fcats);
				}
			}
			}

			for (int i1 = 0; i1 < hits.size(); i1++) {
				GoodsInfo strArray = hits.get(i1).source;
				// list.remove(i1);
				// i1-=1;
				Map<String, Object> output = new HashMap<String, Object>();

				output.put("goods_id", strArray.getGoods_id());
				output.put("cat_id", strArray.getCat_id());
				output.put("scat_id", strArray.getScat_id());
				output.put("fcat_id", strArray.getFcat_id());
				output.put("cat_name", strArray.getCat_ename());
				output.put("cat_ename", strArray.getCat_ename());
				output.put("brand_id", strArray.getBrand_id());
				output.put("brand_name", strArray.getBrand_name());
				output.put("brand_ename", strArray.getBrand_ename());
				// 添加括号
				if(hits.get(i1).highlight!=null){
				output.put("goods_name_mark",
						hits.get(i1).highlight.get("goods_name") == null ? strArray.getGoods_name()
								: hits.get(i1).highlight.get("goods_name").get(0));
				output.put(
						"goods_ename_mark",
						hits.get(i1).highlight.get("goods_ename") == null ? strArray
								.getGoods_ename() : hits.get(i1).highlight.get("goods_ename")
								.get(0));
				}else{
					output.put("goods_name_mark",strArray.getGoods_name());
					output.put("goods_ename_mark",strArray.getGoods_ename());
				}
				output.put("goods_name",strArray.getGoods_name());
				output.put("goods_ename",strArray.getGoods_ename());
				output.put("shop_price", strArray.getShop_price());
				// BigDecimal price=new BigDecimal(strArray.get(15));
				// BigDecimal price_temp = price.multiply(new
				// BigDecimal(100)).divide(new BigDecimal(100)).setScale(2);
				// output.put("shop_price",price_temp);

				output.put("promote_price", strArray.getPromote_price());
				// BigDecimal pprice=new BigDecimal(strArray.get(14));
				// BigDecimal pprice_temp = pprice.multiply(new
				// BigDecimal(100)).divide(new BigDecimal(100)).setScale(2);
				// output.put("promote_price",pprice_temp);

				output.put("goods_number", strArray.getGoods_number());
				output.put("click_count", strArray.getClick_count());
				if(strArray.getIs_promote()==1&&strArray.getPromote_start_date()<=now&&strArray.getPromote_end_date()>=now){
					output.put("is_promote", 1);
				}else{
					output.put("is_promote", 0);
				}
				//output.put("is_promote", strArray.getIs_promote());
				output.put("first_add_time", strArray.getFirst_add_time());
				output.put("image", strArray.getImage());
				output.put("parent_id", strArray.getParent_id());
				output.put("alphabetic_index", strArray.getAlphabetic_index());
				output.put("score", strArray.getScore());
				output.put("vendor_id", strArray.getVendor_id());
				result.add(output);
/*				if (strArray.getGoods_number() != 0) {
					result.add(output);
				} else {
					outWithZero.add(output);
				}*/
				// total += 1;
			}
/*			for (int i = 0; i < outWithZero.size(); i++) {
				result.add(outWithZero.get(i));
			}*/

			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> item = new HashMap<String, Object>();
				Map<String, Object> temp = (Map<String, Object>) result.get(i);
				item.put("goods_id", (Integer) temp.get("goods_id"));
				item.put("goods_name", temp.get("goods_name"));
				item.put("goods_ename", temp.get("goods_ename"));
				item.put("goods_name_mark", temp.get("goods_name_mark"));
				item.put("goods_ename_mark", temp.get("goods_ename_mark"));
				// item.put("shop_price",
				// BigDecimal.valueOf(Double.valueOf((String)
				// temp.get("shop_price"))));

				BigDecimal price = (BigDecimal) temp.get("shop_price");
				BigDecimal price_temp = price.multiply(new BigDecimal(100))
						.divide(new BigDecimal(100)).setScale(2);

				BigDecimal pprice = (BigDecimal) temp.get("promote_price");
				BigDecimal pprice_temp = pprice.multiply(new BigDecimal(100))
						.divide(new BigDecimal(100)).setScale(2);
				item.put("is_promote", (Integer) temp.get("is_promote"));

				// item.put("promote_price",
				// BigDecimal.valueOf(Double.valueOf((String)
				// temp.get("promote_price"))));
				item.put("shop_price", price_temp.toString());
				item.put("promote_price", pprice_temp.toString());
				if (platform_id.equals("1")) {
					item.put("promote_price", MemberPriceUtil.getMemberPrice(price, (int)temp.get("scat_id")));
				}
				if ((Integer) temp.get("goods_number") > 0) {
					item.put("is_oos", 0);
				} else {
					item.put("is_oos", 1);
				}
				item.put("image", YamiConstant.IMAGE_URL + temp.get("image"));
				item.put("vendor_id", temp.get("vendor_id"));
				items.add(item);
			}
			page_count = 0;
			if (sresult.getTotal() % ITEMS_PER_PAGE == 0) {
				page_count = sresult.getTotal().intValue() / ITEMS_PER_PAGE;
			} else {
				page_count = sresult.getTotal().intValue() / ITEMS_PER_PAGE + 1;
			}
			/*
			 * List<Object> itemsEnd1 = new ArrayList<Object>(); for (int i =
			 * (page - 1) * 40; i < page * 40; i++) { if (i < items.size()) {
			 * itemsEnd1.add(items.get(i)); } }
			 */
			end.put("items", items);
			end.put("total", sresult.getTotal());
			// categoryTreeNode = new CategoryTreeNode();
			/*
			 * List<CategoryForShow> tempList =
			 * goodsService.selectShowCategory();
			 * 
			 * 
			 * new NewtonDownhill(catList).NewtonDownhillTreeCatShowForGoods(
			 * categoryTreeNode, catList, tempList);
			 */
			// System.out.println(input+":2");
			if(type ==1){
				logger.info("search brand:(" + input + ")");				
			}else if(type ==2){
				logger.info("search vendor:(" + input + ")");				
			}else if(type ==6){
				logger.info("search tag:(" + input + ")");				
			}else if(isNumeric(input)){
				logger.info("search upc:(" + input + ")");				
			}else if(!"".equals(input)){
				logger.info("search word:(" + input + ")");
			}else{
				logger.info("get category cat_id:["+cat_id+ "]");
			}
			//
			end.put("page_count", page_count);
			// end.put("Category", categoryTreeNode.children);
			end.put("Category", fcategories);
			end.put("Brands", brandAlphaMap);
			end.put("page", page);

	        /*long end6 = System.currentTimeMillis();
			logger.info("step6 take time -->> " + (end6 - end5) + " ms");
			logger.info("all take time -->> " + (end6 - start) + " ms");*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return end;

		// System.out.println(sresult.getJsonString());
		// model.put("itmes", sresult.getJsonString());
		// return model;

	}

	public Map<String, Object> searchResultsV3(String token,String platform_id,String language,String input, int is_init,
			int cat_id, int sort_by, int sort_order, String brands, int is_promote, int page, String agent, int type)
			throws Exception {
		/*long start = System.currentTimeMillis();*/
		// Map<String, Object> model = new HashMap<String, Object>();
		// String RESULT_SIZE = "400";
		// String MIN_SCORE = "0.35";
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		String sortWord = "";
		String sortOrder = "desc";
		int fcat_id=0;
		int scat_id=0;
		if(input == null){
			input="";
		}else{
			input=input.trim().replace("\\", "").replace("\"", "");
		}
		if (input.length() > 40) {
			input = input.substring(0, 40);
		}
		char[] charArray = input.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			for (int j = 0; j < YamiConstant.TRAD_DIC.length(); j++) {
				if (charArray[i] == YamiConstant.TRAD_DIC.charAt(j)) {
					charArray[i] = YamiConstant.SIMP_DIC.charAt(j);
				}
			}
		}
		input = String.valueOf(charArray);		
		try {
			if(type==4 && token!=null && !("").equals(token) && !("").equals(input)&&!isNumeric(input)){
				setSearchHistory(token,input);
			}
		} catch (Exception e1) {
			//logger.fatal("setSearchHistory failed:" + input+" message:"+e1.getMessage());
			//e1.printStackTrace();
		}
		
		//默认为手机配置
		int index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
		int ITEMS_PER_PAGE = YamiConstant.ITEMS_PER_PAGE_MOBILE;

		if(agent==null||agent.trim().equals(YamiConstant.STRING_EMPTY)){
			agent = "android";
		}
		//System.out.println(agent.toString());
		//识别调试器,完成调试器配置
		Pattern pattern = Pattern.compile(YamiConstant.REGEX_SOAPUI, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find soapUI ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
    		ITEMS_PER_PAGE = YamiConstant.ITEMS_PER_PAGE_MOBILE;
        }
	    //识别苹果手机
        pattern = Pattern.compile(YamiConstant.REGEX_IPHONE, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find iphone ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_MOBILE;
        }
        //识别三星系列智能设备.
        pattern = Pattern.compile(YamiConstant.REGEX_ANDROID, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find android ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_MOBILE;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_MOBILE;
        }
        //识别火狐浏览器
        pattern = Pattern.compile(YamiConstant.REGEX_FIREFOX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find firefox ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
		//识别windows平台浏览器
        pattern = Pattern.compile(YamiConstant.REGEX_IE, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find ie ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
        //识别chrome浏览器
        pattern = Pattern.compile(YamiConstant.REGEX_CHROME, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find chrome ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
        //识别ipad平台.
        pattern = Pattern.compile(YamiConstant.REGEX_IPAD, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find ipad ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
        //识别mac平台.
        pattern = Pattern.compile(YamiConstant.REGEX_MAC, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(agent);
        if (matcher.find()) 
        {
        	//System.out.println("find imac ");
    		index = (page-1)*YamiConstant.ITEMS_PER_PAGE_LABTOP;
    		ITEMS_PER_PAGE=YamiConstant.ITEMS_PER_PAGE_LABTOP;
        }
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */
        /*long end1 = System.currentTimeMillis();
		logger.info("step1 take time -->> " + (end1 - start) + " ms");*/
		switch (sort_by) {
		case 0:
		case 3:
			sortWord = "_score";
			break;
		case 2:
			sortWord = "click_count";
			break;
		case 4:
			sortWord = "shop_price";
			if (sort_order == 1) {
				sortOrder = "asc";
			}
			break;
		default:
			sortWord = "_score";
			break;
		}
		/*
		 * String query = "{\n" + "    \"size\": " + size + ",\n" +
		 * "    \"min_score\": " + min_score + ",\n" + "    \"query\": {\n" +
		 * "                \"match\" : {\n" +
		 * "                    \"goods_name\" : \"" + input + "\"\n" +
		 * "                }\n" + "    }\n" + "}";
		 */
		Long now = DateUtil.getUnixTime();
		String filter = ",\n"
				+ "    \"filter\":{"
				+ "    \"bool\": {MUNOT    \"must\":[{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
		if(type == 5){
			filter = ",\n"
					+ "    \"filter\":{"
					+ "    \"bool\": {MUNOT    \"must\":[{\"term\":{\"vendor_id\": 17 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			if (is_promote == 1) {
				filter = ",\n"
						+ "    \"filter\":{"
						+ "    \"bool\": {MUNOT    \"must\":[{\"term\":{\"vendor_id\": 17 }},{\"range\":{\"promote_start_date\": {\"lte\": "+now+"}}},{\"range\":{\"promote_end_date\": {\"gte\": "+now+"}}},{\"term\":{\"is_promote\": 1 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			}
		}else if (platform_id.equals("1")) {
			filter = ",\n"
					+ "    \"filter\":{"
					+ "    \"bool\": {MUNOT    \"must\":[{\"term\":{\"vendor_id\": 0 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			if (is_promote == 1) {
				filter = ",\n"
						+ "    \"filter\":{"
						+ "    \"bool\": {MUNOT    \"must\":[{\"range\":{\"promote_start_date\": {\"lte\": "+now+"}}},{\"range\":{\"promote_end_date\": {\"gte\": "+now+"}}},{\"term\":{\"vendor_id\": 0 }},{\"term\":{\"is_promote\": 1 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			}
		} else {
			if (is_promote == 1) {
				filter = ",\n"
						+ "    \"filter\":{"
						+ "    \"bool\": {MUNOT    \"must\":[{\"range\":{\"promote_start_date\": {\"lte\": "+now+"}}},{\"range\":{\"promote_end_date\": {\"gte\": "+now+"}}},{\"term\":{\"is_promote\": 1 }},{\"term\":{\"is_delete\": 0 }},{\"term\":{\"is_on_sale\": 1 }}MSTXXX    SHOULD}}";
			}
		}

		Map<String, Object> end = new HashMap<String, Object>();
		
		Map<String, Object> brandAlphaMap;
		// System.out.println(items.size());
		int page_count;
		// make category
		try {
			String searchJson = "";
			if(type == 1){
				//brand
				brands = input;
				URL url = SearchServiceDelegate.class.getResource("elastic-brand.json");
				searchJson = Resources.toString(url, Charsets.UTF_8)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);	
			}else if(type == 2){
				//vendor
				URL url = SearchServiceDelegate.class.getResource("elastic-brand.json");
				searchJson = Resources.toString(url, Charsets.UTF_8)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);
				filter = filter.replace("MSTXXX", ",{ \"term\":{ \"vendor_id\":\"" + input
						+ "\"}}]");
			}else if(type == 6){
				//tag
				URL url = SearchServiceDelegate.class.getResource("elastic-searchTag.json");
				searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);
			}else if(isNumeric(input)){
				//upc
				URL url = SearchServiceDelegate.class.getResource("elastic-searchUpc.json");
				searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);				
			}else if(input.equals("")){
				URL url = SearchServiceDelegate.class.getResource("elastic-category.json");
				searchJson = Resources.toString(url, Charsets.UTF_8)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);	
			
			} else {
				URL url = SearchServiceDelegate.class.getResource("elastic-searchResult.json");
				searchJson = Resources.toString(url, Charsets.UTF_8).replace("MATCHXXX", input)
						.replace("SORTXXX", sortWord).replace("ORDERXXX", sortOrder);
			}
			if (null != MIN_SCORE) {
				searchJson = searchJson.replace("MINSCOREXXX", MIN_SCORE);
			}			
			// in order to implement category.
			if(cat_id>=0 && cat_id<16){
				fcat_id = cat_id;
				cat_id = 0;
			}else if(cat_id>=16 && cat_id <=99){
				scat_id = cat_id;
				cat_id = 0;				
			}
			if (null != brands && brands.trim().length() != 0) {
				// int[] brand_id_int = new int[brands.split(",").length];
				if(language.equals("en")||type==4){
					filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
							+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
							+ "}}],");
				}else{
					filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
							+ "}},");
				}
				String[] brand_ids = brands.replace("(", "").replace(")", "").split(",");
				String brandFilter = "";
				for (int i = 0; i < brand_ids.length; i++) {
					String brand_id = brand_ids[i].trim();
					if (i == 0) {
						brandFilter += "\"should\":[";
					}
					if (i > 0) {
						brandFilter += ",";
					}
					brandFilter += "{\"term\":{\"brand_id\":\"" + brand_id + "\"}}";
					if (i == brand_ids.length - 1) {
						brandFilter += "]";
					}
				}
				filter = filter.replace("SHOULD", brandFilter);
				if (cat_id != 0) {
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"cat_id\":\"" + cat_id
							+ "\"}}],");
				} else if (fcat_id != 0) {
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"fcat_id\":\"" + fcat_id
							+ "\"}}],");
				} else if (scat_id != 0) {
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"scat_id\":\"" + scat_id
							+ "\"}}],");
				} else {
					filter = filter.replace("MSTXXX", "],");
				}
				searchJson = searchJson.replace("FILTERXXX", filter);
			} else {

				if (cat_id != 0) {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						}else{
							filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
									+ "}},");
						}
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"cat_id\":\"" + cat_id
							+ "\"}}]");
					filter = filter.replace("SHOULD", "");
					searchJson = searchJson.replace("FILTERXXX", filter);
				} else if (fcat_id != 0) {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						}else{
							filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
									+ "}},");
						}
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"fcat_id\":\"" + fcat_id
							+ "\"}}]");
					filter = filter.replace("SHOULD", "");
					searchJson = searchJson.replace("FILTERXXX", filter);
				} else if (scat_id != 0) {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						}else{
							filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
									+ "}},");
						}
					filter = filter.replace("MSTXXX", ",{ \"term\":{ \"scat_id\":\"" + scat_id
							+ "\"}}]");
					filter = filter.replace("SHOULD", "");
					searchJson = searchJson.replace("FILTERXXX", filter);
				} else {
					if(language.equals("en")||type==4){
						filter = filter.replace("MUNOT", "\"must_not\":[{ \"term\":{ \"scat_id\":" + 97
								+ "}},{\"term\":{ \"vendor_id\":" + EXCLUDE_VENDOR
								+ "}}],");
						filter = filter.replace("SHOULD", "");
						filter = filter.replace("MSTXXX", "]");
						searchJson = searchJson.replace("FILTERXXX", filter);
					}else{
						filter = filter.replace("MUNOT","\"must_not\":{ \"term\":{ \"scat_id\":" + 97
								+ "}},");
						filter = filter.replace("SHOULD", "");
						filter = filter.replace("MSTXXX", "]");
						searchJson = searchJson.replace("FILTERXXX", filter);
						//searchJson = searchJson.replace("FILTERXXX", "");
					}
				}
			}
	        /*long end2 = System.currentTimeMillis();
			logger.info("step2 take time -->> " + (end2 - end1) + " ms");*/
			Search.Builder searchBuilder = new Search.Builder(searchJson).addIndex(RESULT_INDEX)
					.addType(RESULT_TYPE).setParameter("from", index)
					.setParameter("size", ITEMS_PER_PAGE);
			SearchResult sresult = client.execute(searchBuilder.build());

			List<SearchResult.Hit<GoodsInfo, Void>> hits = sresult.getHits(GoodsInfo.class);
			if(!"".equals(input)&&hits.size()!=0&&(type==0||type==4)&&!isNumeric(input)){
				markKeyword(input);
			}
	        /*long end3 = System.currentTimeMillis();
			logger.info("step3 take time -->> " + (end3 - end2) + " ms");*/
			// client.shutdownClient();
			// List<GoodsInfo> goodsList =
			// sresult.getSourceAsObjectList(GoodsInfo.class);
			List<Object> result = new ArrayList<Object>();
			//List<Object> outWithZero = new ArrayList<Object>();
			List<Object> items = new ArrayList<Object>();
			brandAlphaMap = new LinkedHashMap<String, Object>();
			TermsAggregation terms = sresult.getAggregations().getTermsAggregation("alphabetic");
			if(terms!=null){
			List<Entry> buckets = terms.getBuckets();
			for (Entry bucket : buckets) {
				String key = bucket.getKey();
				List<Object> number = new ArrayList<Object>();
				TermsAggregation terms2 = bucket.getTermsAggregation("brandids");
				List<Entry> buckets2 = terms2.getBuckets();
				for (Entry bucket2 : buckets2) {
					Map<String, Object> tempAlpha = new LinkedHashMap<String, Object>();
					tempAlpha.put("brand_id", bucket2.getKey());
					tempAlpha.put("hits", bucket2.getCount());
					TermsAggregation terms3 = bucket2.getTermsAggregation("brandnames");
					List<Entry> buckets3 = terms3.getBuckets();
					if(buckets3.size()!=0){
					tempAlpha.put("brand_name", buckets3.get(0).getKey());
					}else{
						tempAlpha.put("brand_name", "");						
					}
					if(buckets3.get(0).getTermsAggregation("brandenames").getBuckets().size()!=0){
					Entry bucket4 = buckets3.get(0).getTermsAggregation("brandenames").getBuckets()
							.get(0);
					tempAlpha.put("brand_ename", bucket4.getKey());}
					else{
						tempAlpha.put("brand_ename", "");
					}
					
					number.add(tempAlpha);
				}
				brandAlphaMap.put(key, number);
			}
			}
	        /*long end4 = System.currentTimeMillis();
			logger.info("step4 take time -->> " + (end4 - end3) + " ms");*/
			List<CategoryForShow> tempList =  new ArrayList<CategoryForShow>();
			//List<CategoryForShow> tempList = goodsService.selectShowCategory();
			Gson gson = new Gson();  
			if(null != redisTemplate.opsForValue().get("showCategory")){
				tempList= gson.fromJson(redisTemplate.opsForValue().get("showCategory"),new TypeToken<List<CategoryForShow>>() {  
                }.getType());
			}else{
				tempList=goodsService.selectShowCategory();
				redisTemplate.opsForValue().set("showCategory", gson.toJson(tempList), 1, TimeUnit.DAYS);
			}
			
	        /*long end5 = System.currentTimeMillis();
			logger.info("step5 take time -->> " + (end5 - end4) + " ms");*/
			List<Object> fcategories = new ArrayList<Object>();
			TermsAggregation fcatids = sresult.getAggregations().getTermsAggregation("fcat_ids");
			if(fcatids!=null){
			List<Entry> fcatidsBuckets = fcatids.getBuckets();

			for (Entry fb : fcatidsBuckets) {
				String fkey = fb.getKey();
				Map<String, Object> fcats = new LinkedHashMap<String, Object>();
				Map<String, String> fcatPojo = findCatName(fkey, tempList);
				if(fcatPojo.get("is_show")!=null&&fcatPojo.get("is_show").equals("1")){
				fcats.put("cat_id", fkey);
				fcats.put("cat_name", fcatPojo.get("cat_name"));
				fcats.put("cat_ename", fcatPojo.get("cat_ename"));
				fcats.put("parent_id", fcatPojo.get("parent_id"));
				fcats.put("hits", fb.getCount());

				TermsAggregation scatids = fb.getTermsAggregation("scat_ids");
				List<Entry> scatidsBuckets = scatids.getBuckets();
				List<Object> scategories = new ArrayList<Object>();
				for (Entry sb : scatidsBuckets) {
					String skey = sb.getKey();
					Map<String, Object> scats = new LinkedHashMap<String, Object>();
					Map<String, String> scatPojo = findCatName(skey, tempList);
					if(scatPojo.get("is_show")!=null&&scatPojo.get("is_show").equals("1")){
					scats.put("cat_id", skey);
					scats.put("cat_name", scatPojo.get("cat_name"));
					scats.put("cat_ename", scatPojo.get("cat_ename"));
					scats.put("parent_id", scatPojo.get("parent_id"));
					scats.put("hits", sb.getCount());

					TermsAggregation catids = sb.getTermsAggregation("cat_ids");
					List<Entry> catidsBuckets = catids.getBuckets();
					List<Object> categories = new ArrayList<Object>();
					for (Entry b : catidsBuckets) {
						String key = b.getKey();
						Map<String, Object> cats = new LinkedHashMap<String, Object>();
						Map<String, String> catPojo = findCatName(key, tempList);
						if(catPojo.get("is_show")!=null&&catPojo.get("is_show").equals("1")){
						cats.put("cat_id", key);
						cats.put("cat_name", catPojo.get("cat_name"));
						cats.put("cat_ename", catPojo.get("cat_ename"));
						cats.put("parent_id", catPojo.get("parent_id"));
						cats.put("hits", b.getCount());
						categories.add(cats);
						}
					}
					scats.put("children", categories);
					scategories.add(scats);
					}
				}

				fcats.put("children", scategories);
				fcategories.add(fcats);
				}
			}
			}

			for (int i1 = 0; i1 < hits.size(); i1++) {
				GoodsInfo strArray = hits.get(i1).source;
				// list.remove(i1);
				// i1-=1;
				Map<String, Object> output = new HashMap<String, Object>();

				output.put("goods_id", strArray.getGoods_id());
				output.put("cat_id", strArray.getCat_id());
				output.put("scat_id", strArray.getScat_id());
				output.put("fcat_id", strArray.getFcat_id());
				output.put("cat_name", strArray.getCat_ename());
				output.put("cat_ename", strArray.getCat_ename());
				output.put("brand_id", strArray.getBrand_id());
				output.put("brand_name", strArray.getBrand_name());
				output.put("brand_ename", strArray.getBrand_ename());
				// 添加括号
				if(hits.get(i1).highlight!=null){
				output.put("goods_name_mark",
						hits.get(i1).highlight.get("goods_name") == null ? strArray.getGoods_name()
								: hits.get(i1).highlight.get("goods_name").get(0));
				output.put(
						"goods_ename_mark",
						hits.get(i1).highlight.get("goods_ename") == null ? strArray
								.getGoods_ename() : hits.get(i1).highlight.get("goods_ename")
								.get(0));
				}else{
					output.put("goods_name_mark",strArray.getGoods_name());
					output.put("goods_ename_mark",strArray.getGoods_ename());
				}
				output.put("goods_name",strArray.getGoods_name());
				output.put("goods_ename",strArray.getGoods_ename());
				output.put("shop_price", strArray.getShop_price());
				// BigDecimal price=new BigDecimal(strArray.get(15));
				// BigDecimal price_temp = price.multiply(new
				// BigDecimal(100)).divide(new BigDecimal(100)).setScale(2);
				// output.put("shop_price",price_temp);

				output.put("promote_price", strArray.getPromote_price());
				// BigDecimal pprice=new BigDecimal(strArray.get(14));
				// BigDecimal pprice_temp = pprice.multiply(new
				// BigDecimal(100)).divide(new BigDecimal(100)).setScale(2);
				// output.put("promote_price",pprice_temp);

				output.put("goods_number", strArray.getGoods_number());
				output.put("click_count", strArray.getClick_count());
				if(strArray.getIs_promote()==1&&strArray.getPromote_start_date()<=now&&strArray.getPromote_end_date()>=now){
					output.put("is_promote", 1);
				}else{
					output.put("is_promote", 0);
				}
				//output.put("is_promote", strArray.getIs_promote());
				output.put("first_add_time", strArray.getFirst_add_time());
				output.put("image", strArray.getImage());
				output.put("parent_id", strArray.getParent_id());
				output.put("alphabetic_index", strArray.getAlphabetic_index());
				output.put("score", strArray.getScore());
				output.put("vendor_id", strArray.getVendor_id());
				result.add(output);
/*				if (strArray.getGoods_number() != 0) {
					result.add(output);
				} else {
					outWithZero.add(output);
				}*/
				// total += 1;
			}
/*			for (int i = 0; i < outWithZero.size(); i++) {
				result.add(outWithZero.get(i));
			}*/

			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> item = new HashMap<String, Object>();
				Map<String, Object> temp = (Map<String, Object>) result.get(i);
				item.put("goods_id", (Integer) temp.get("goods_id"));
				item.put("goods_name", temp.get("goods_name"));
				item.put("goods_ename", temp.get("goods_ename"));
				item.put("goods_name_mark", temp.get("goods_name_mark"));
				item.put("goods_ename_mark", temp.get("goods_ename_mark"));
				// item.put("shop_price",
				// BigDecimal.valueOf(Double.valueOf((String)
				// temp.get("shop_price"))));

				BigDecimal price = (BigDecimal) temp.get("shop_price");
				BigDecimal price_temp = price.multiply(new BigDecimal(100))
						.divide(new BigDecimal(100)).setScale(2);

				BigDecimal pprice = (BigDecimal) temp.get("promote_price");
				BigDecimal pprice_temp = pprice.multiply(new BigDecimal(100))
						.divide(new BigDecimal(100)).setScale(2);
				item.put("is_promote", (Integer) temp.get("is_promote"));

				// item.put("promote_price",
				// BigDecimal.valueOf(Double.valueOf((String)
				// temp.get("promote_price"))));
				item.put("shop_price", price_temp.toString());
				item.put("promote_price", pprice_temp.toString());
				if (platform_id.equals("1")) {
					item.put("promote_price", MemberPriceUtil.getMemberPrice(price, (int)temp.get("scat_id")));
				}
				if ((Integer) temp.get("goods_number") > 0) {
					item.put("is_oos", 0);
				} else {
					item.put("is_oos", 1);
				}
				item.put("image", YamiConstant.IMAGE_URL + temp.get("image"));
				item.put("vendor_id", temp.get("vendor_id"));
				items.add(item);
			}
			page_count = 0;
			if (sresult.getTotal() % ITEMS_PER_PAGE == 0) {
				page_count = sresult.getTotal().intValue() / ITEMS_PER_PAGE;
			} else {
				page_count = sresult.getTotal().intValue() / ITEMS_PER_PAGE + 1;
			}
			/*
			 * List<Object> itemsEnd1 = new ArrayList<Object>(); for (int i =
			 * (page - 1) * 40; i < page * 40; i++) { if (i < items.size()) {
			 * itemsEnd1.add(items.get(i)); } }
			 */
			end.put("items", items);
			end.put("total", sresult.getTotal());
			// categoryTreeNode = new CategoryTreeNode();
			/*
			 * List<CategoryForShow> tempList =
			 * goodsService.selectShowCategory();
			 * 
			 * 
			 * new NewtonDownhill(catList).NewtonDownhillTreeCatShowForGoods(
			 * categoryTreeNode, catList, tempList);
			 */
			// System.out.println(input+":2");
			if(type ==1){
				logger.info("search brand:(" + input + ")");				
			}else if(type ==2){
				logger.info("search vendor:(" + input + ")");				
			}else if(type ==6){
				logger.info("search tag:(" + input + ")");				
			}else if(isNumeric(input)){
				logger.info("search upc:(" + input + ")");				
			}else if(!"".equals(input)){
				logger.info("search word:(" + input + ")");
			}else{
				logger.info("get category cat_id:["+cat_id+ "]");
			}
			//
			end.put("page_count", page_count);
			// end.put("Category", categoryTreeNode.children);
			end.put("Category", fcategories);
			end.put("Brands", brandAlphaMap);
			end.put("page", page);

	        /*long end6 = System.currentTimeMillis();
			logger.info("step6 take time -->> " + (end6 - end5) + " ms");
			logger.info("all take time -->> " + (end6 - start) + " ms");*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return end;

		// System.out.println(sresult.getJsonString());
		// model.put("itmes", sresult.getJsonString());
		// return model;

	}
	
	public void markKeyword(String input) throws Exception {
		final String finalUInput = input;
		// String indexName = "key_words";
		// String typeName = "goods_name";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		String script = "{" + "    \"script\" : \"ctx._source.click_count += 1\","
				+ "    \"upsert\" : {" + "        \"click_count\" : 1" + ","
				+ " \"activity_point\" : 0" + "    ," + " \"key_word\" : \"KEYWORDXXX\"    }" + "}";

/*		JestResult result = client.execute(new Update.Builder(script.replace("KEYWORDXXX",
				input.trim())).index(KEYWORD_INDEX).type(KEYWORD_TYPE).id(input.trim()).build());
		if (!result.isSucceeded()) {
			logger.error(String.format("Failed to mark keyword: %s", result.getErrorMessage()));
		} else {
			logger.debug("Marked input keyword :" + input);
		}*/
		client.executeAsync(new Update.Builder(script.replace("KEYWORDXXX",
				input.trim().toLowerCase())).index(KEYWORD_INDEX).type(KEYWORD_TYPE).id(input.trim()).build(),new JestResultHandler<JestResult>(){
			@Override
		    public void completed(JestResult result) {
				logger.info("Marked input keyword :" + finalUInput);
		    }
		    @Override
		    public void failed(Exception ex) {
		    	logger.error(String.format("Failed to mark keyword: %s", ex.getMessage()));
		    }
		});

		// client.shutdownClient();
	}

	public List<String> searchSuggestion(String input) throws Exception {
		long start = System.currentTimeMillis();

		List<String> result = new ArrayList<String>();
		if(input == null){
			input="";
		}else{
			input=input.trim().replace("\\", "").replace("\"", "");
		}
		if (input.equals("")) {
			/*
			 * //热搜排序 //reduce nc for(int i=0;i<nc.size();i++) { for(int
			 * j=0;j<nc.size();j++) { if(i!=j) {
			 * if(nc.get(i).name.equals(nc.get(j).name)) {
			 * nc.get(i).count+=nc.get(j).count; nc.remove(j); j-=1; } } } }
			 * //排序 for(int i=0;i<nc.size();i++) { for(int j=0;j<nc.size();j++)
			 * { if(nc.get(i).count>nc.get(j).count) { NC n= new NC();
			 * n.name=new String(nc.get(i).name); n.count=nc.get(i).count;
			 * 
			 * nc.set(i, nc.get(j)); nc.set(j, n); } } } //取前10 for(int
			 * i=0;i<nc.size();i++) { if(i<30) {
			 * System.out.println("name:"+nc.get
			 * (i).name+"\tcount:"+nc.get(i).count); } } //取前10 for(int
			 * i=0;i<nc.size();i++) { if(i<10) { result.add(nc.get(i).name); } }
			 */
			result = getHotSuggestion();
			return result;
		}

		// String indexName = "key_words";
		// String typeName = "goods_name";
		// TODO

		URL url = SearchServiceDelegate.class.getResource("elastic-searchKeyword.json");
		String searchJson = Resources.toString(url, Charsets.UTF_8);

		Search.Builder searchBuilder = new Search.Builder(searchJson.replace("KEYWORDXXX", input))
				.addIndex(KEYWORD_INDEX).addType(KEYWORD_TYPE);
		SearchResult sresult = client.execute(searchBuilder.build());
		if (sresult.getHits(GoodsNameInfo.class).size() == 0) {
			return result;
		}
		List<KeyWords> keyWords = sresult.getSourceAsObjectList(KeyWords.class);
		for (KeyWords keyWord : keyWords) {
			result.add(keyWord.getKey_word());
		}

		// logger.info("Successed insert bulk data to:" + indexName);

		// client.shutdownClient();
		long end = System.currentTimeMillis();
		logger.info("get suggestions of keyword (" + input + ") take time -->> " + (end - start)
				+ " ms");
		return result;
	}

	public List<String> makeHotSuggestion() throws Exception {

		List<String> result = new ArrayList<String>();
		// String indexName = "key_words";
		// String typeName = "goods_name";

		Gson gson = new Gson();

		URL url = SearchServiceDelegate.class.getResource("elastic-hotKeyword.json");
		String searchJson = Resources.toString(url, Charsets.UTF_8);

		Search.Builder searchBuilder = new Search.Builder(searchJson).addIndex(KEYWORD_INDEX)
				.addType(KEYWORD_TYPE);
		SearchResult sresult = client.execute(searchBuilder.build());

		List<SearchResult.Hit<KeyWords, Void>> hits = sresult.getHits(KeyWords.class);

		for (int i1 = 0; i1 < hits.size(); i1++) {
			result.add(hits.get(i1).source.getKey_word());
		}
		String strResult = gson.toJsonTree(result).toString();
		redisTemplate.opsForValue().set("HotSuggestion", strResult, 6000, TimeUnit.DAYS);
		return result;
	}
	
	public List<String> getHotSuggestion() {

		List<String> result = new ArrayList<String>();

		String mapInfo = redisTemplate.opsForValue().get("HotSuggestion");
		Gson gson = new Gson();
		result = gson.fromJson(mapInfo, new TypeToken<List<String>>() {
		}.getType());

		return result;

	}
	
	public void importKeyword() throws Exception {
		// String indexName = "yami_search";
		// String typeName = "goods_info";
		// String indexName2 = "key_words";
		// String typeName2 = "goods_name";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		// 批量新增的方式,效率更高
		Bulk.Builder bulkBuilder = new Bulk.Builder();

		String script = "{" + "    \"script\" : \"ctx.op ='none'\"," + "    \"upsert\" : {"
				+ "        \"click_count\" : 0" + "," + " \"activity_point\" : 0" + "    ,"
				+ " \"key_word\" : \"KEYWORD\"    }" + "}";

		URL url = SearchServiceDelegate.class.getResource("elastic-searchAll.json");
		String searchJson = Resources.toString(url, Charsets.UTF_8);
		// TODO 20000 need replace with real size of result index.
		for (int i = 0; i < 9999999; i = i + 500) {
			Search.Builder searchBuilder = new Search.Builder(searchJson.replace("FROMXXX",
					String.valueOf(i))).addIndex(RESULT_INDEX).addType(RESULT_TYPE);
			SearchResult sresult = client.execute(searchBuilder.build());
			if (sresult.getHits(GoodsNameInfo.class).size() == 0) {
				break;
			}
			List<GoodsNameInfo> goodsNames = sresult.getSourceAsObjectList(GoodsNameInfo.class);
			for (GoodsNameInfo goodsNameInfo : goodsNames) {
				Analyze action = new Analyze.Builder().analyzer("ik_smart")
						.text(goodsNameInfo.getGoods_name() + " " + goodsNameInfo.getGoods_ename())
						.build();
				JestResult result = client.execute(action);
				JsonObject resultObj = result.getJsonObject();
				JsonArray tokens = resultObj.getAsJsonArray("tokens");
				// System.out.println(tokens);

				for (JsonElement ss : tokens.getAsJsonArray()) {
					if ("CN_WORD".equals(ss.getAsJsonObject().get("type").getAsString())
							|| "CN_CHAR".equals(ss.getAsJsonObject().get("type").getAsString())) {
						if (ss.getAsJsonObject().get("end_offset").getAsInt()
								- ss.getAsJsonObject().get("start_offset").getAsInt() >= 2) {
							bulkBuilder.addAction(new Update.Builder(StringUtils.chomp(script
									.replace("KEYWORD", ss.getAsJsonObject().get("token")
											.getAsString()))).index(KEYWORD_INDEX)
									.type(KEYWORD_TYPE)
									.id(ss.getAsJsonObject().get("token").getAsString()).build());

						}
					}
					if ("ENGLISH".equals(ss.getAsJsonObject().get("type").getAsString())) {
						bulkBuilder.addAction(new Update.Builder(StringUtils.chomp(script.replace(
								"KEYWORD", ss.getAsJsonObject().get("token").getAsString())))
								.index(KEYWORD_INDEX).type(KEYWORD_TYPE)
								.id(ss.getAsJsonObject().get("token").getAsString()).build());
					}
				}

			}
			JestResult results = client.execute(bulkBuilder.build());
			bulkBuilder = new Bulk.Builder();
			if (!results.isSucceeded()) {
				logger.error(String.format("Failed to insert bulk data: %s",
						results.getErrorMessage()));
			} else {
				// System.out.println("Successed insert bulk data :" +
				// goodsNameInfo.getGoods_name());
				logger.info("Successed insert bulk data :" + i);
			}
		}

		logger.info("Successed insert bulk data to:" + KEYWORD_INDEX);

		/*
		 * JestResult result = client.execute(bulkBuilder.build()); if
		 * (!result.isSucceeded()) {
		 * System.out.println(String.format("Failed to insert bulk data: %s",
		 * result.getErrorMessage())); } else {
		 * System.out.println("Created inserting bulk data :" + indexName); }
		 */
		// client.shutdownClient();
	}


	public void reindex(String from_index, String to_index) throws Exception {
		createToIndex(to_index);

		/*
		 * // Configuration ClientConfig clientConfig = new
		 * ClientConfig.Builder(ELASTICSEARCH_URL) .multiThreaded(false)
		 * .build(); // Construct a new Jest client according to configuration
		 * via factory JestClientFactory factory = new JestClientFactory();
		 * factory.setClientConfig(clientConfig); JestClient client =
		 * factory.getObject();
		 */

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		String toIndex = "";
		String typeName = "";
		if(to_index.startsWith("key_words")){
			toIndex = KEYWORD_INDEX;
			typeName =  KEYWORD_TYPE;
		}else{
			toIndex = RESULT_INDEX;
			typeName = RESULT_TYPE;
		}
		reindexData(client, searchSourceBuilder, from_index, to_index, typeName);

		logger.info("************Reindex Finished************");

		// remove alias
		ModifyAliases modifyAliases = new ModifyAliases.Builder(new RemoveAliasMapping.Builder(
				from_index, toIndex).build()).build();
		JestResult result2 = client.execute(modifyAliases);
		if (!result2.isSucceeded()) {
			logger.error(String.format("Failed to remove alias: %s", result2.getErrorMessage()));
		} else {
			logger.info("Removed alias( " + toIndex + " ) from index :" + from_index);
		}

		// add alias
		modifyAliases = new ModifyAliases.Builder(new AddAliasMapping.Builder((to_index),
				toIndex).build()).build();

		JestResult result3 = client.execute(modifyAliases);
		if (!result3.isSucceeded()) {
			logger.error(String.format("Failed to create alias: %s", result3.getErrorMessage()));
		} else {
			logger.info("Created alias( " + toIndex + " ) for index :" + to_index);
		}
	}

	private void reindexData(JestClient client, SearchSourceBuilder searchSourceBuilder,
			String fromIndexName, String toIndexName, String typeName) {
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(fromIndexName)
				.addType(typeName).setParameter(Parameters.SEARCH_TYPE, SearchType.SCAN)
				.setParameter(Parameters.SIZE, PAGE_SIZE).setParameter(Parameters.SCROLL, "5m")
				.build();
		// System.out.println(search.getData(null));
		JestResult result = handleResult(client, search);
		String scrollId = result.getJsonObject().get("_scroll_id").getAsString();

		int currentResultSize = 0;
		int pageNumber = 1;
		do {
			SearchScroll scroll = new SearchScroll.Builder(scrollId, "5m").build();
			result = handleResult(client, scroll);
			scrollId = result.getJsonObject().get("_scroll_id").getAsString();
			List hits = ((List) ((Map) result.getJsonMap().get("hits")).get("hits"));
			currentResultSize = hits.size();
			logger.info("finished scrolling page # " + pageNumber++ + " which had "
					+ currentResultSize + " results.");

			Builder bulkIndexBuilder = new Bulk.Builder().defaultIndex(toIndexName).defaultType(
					typeName);
			boolean somethingToIndex = false;
			for (int i = 0; i < currentResultSize; i++) {
				Map source = ((Map) ((Map) hits.get(i)).get("_source"));
				String sourceId = ((String) ((Map) hits.get(i)).get("_id"));
				//logger.info("adding " + sourceId + " for bulk indexing");

				// TODO: we could transform the source if we wanted to here,
				// before adding it to the bulk index queue

				Index index = new Index.Builder(source).index(toIndexName).type(typeName)
						.id(sourceId).build();
				bulkIndexBuilder = bulkIndexBuilder.addAction(index);
				somethingToIndex = true;
			}
			if (somethingToIndex) {
				Bulk bulk = bulkIndexBuilder.build();
				// System.out.println(bulk.getData(null));
				handleResult(client, bulk);
			} else {
				logger.info("there weren't any results to index in this set/page");
			}
			// TODO SHARD=5!!!!
		} while (currentResultSize == PAGE_SIZE * Integer.valueOf(NUMBER_OF_SHARDS));
	}

	protected JestResult handleResult(JestClient client, Action action) {
		JestResult result = null;
		try {
			result = client.execute(action);
			if (result.isSucceeded()) {
				//logger.info(result.getJsonString());
				// List hits = ((List) ((Map)
				// result.getJsonMap().get("hits")).get("hits"));
				// System.out.println("hits.size(): " + hits.size());
			} else {
				logger.error(result.getErrorMessage());
				logger.error(result.getJsonString());
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return result;
	}

	/**
	 * 删除索引
	 * 
	 * @param indexId
	 * @param indexName
	 * @param indexType
	 */
	private boolean deleteIndex(String indexName) {
		// builder.refresh(true);

		try {
			/*
			 * JestClientFactory factory = new JestClientFactory();
			 * factory.setHttpClientConfig(new
			 * HttpClientConfig.Builder("http://192.168.1.80:9200")
			 * .multiThreaded(true).build()); JestClient client =
			 * factory.getObject();
			 */
			JestResult result = client.execute(new Delete.Builder(indexName).build());
			if (result != null && !result.isSucceeded()) {
				logger.error("delete index " + indexName + " failed:" + result.getErrorMessage());
			}
		} catch (Exception e) {
			logger.error("deleteIndex " + indexName + " failed:", e);
			return false;
		}

		return true;
	}

	private int addparent(int j, List<CategoryForShow> tempList) {

		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getCat_id() == j) {
				return tempList.get(i).getParent_id();
			}
		}
		return 0;
	}

	private CatInfo getCatInfo(List<CatInfo> catList, int intValue) {
		for (int i = 0; i < catList.size(); i++) {
			if (catList.get(i).getCat_id().equals(intValue)) {
				CatInfo temp = catList.get(i);
				// catList.remove(i);
				return temp;
			}
		}
		return null;
	}

	private BrandInfo getBrandInfo(List<BrandInfo> brandList, int intValue) {
		for (int i = 0; i < brandList.size(); i++) {
			if (brandList.get(i).getBrand_id().equals(intValue)) {
				BrandInfo temp = brandList.get(i);
				// brandList.remove(i);
				return temp;
			}
		}
		return null;
	}

	public Map<String, Object> clearSearchHistory(String token) {
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new Gson();
		Token tokenIn = gson.fromJson(StringUtil.decode(token), Token.class);
		redisTemplate.delete(YamiConstant.SEARCH_HISTORY + ":" + tokenIn.getData());
		model.put("status", YamiConstant.STATUS_OK);
		return model;
	}

	public Map<String, Object> getSearchHistory(String token, String input) {
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new Gson();
		Token tokenIn = gson.fromJson(StringUtil.decode(token), Token.class);
		String StingKey = YamiConstant.SEARCH_HISTORY + ":" + tokenIn.getData();
		Long sResult = redisTemplate.opsForList().size(StingKey);
		List<String> searchHistory = new ArrayList<String>();
		if (sResult > 0) {
			searchHistory = redisTemplate.opsForList().range(StingKey, 0, sResult);
			/*
			 * for(Long m =new Long(0);m<sResult;m++){
			 * searchHistory.add(redisTemplate.opsForList().leftPop(StingKey));
			 * model.put("searchHistory", searchHistory); }
			 */
		}
		model.put("searchHistory", searchHistory);
		return model;
	}

	public void setSearchHistory(String token, String input) {
		String StingKey = "";
		try{
		Gson gson = new Gson();
		Token tokenIn = gson.fromJson(StringUtil.decode(token), Token.class);
		StingKey = YamiConstant.SEARCH_HISTORY + ":" + tokenIn.getData();

		Long sResult = redisTemplate.opsForList().size(StingKey);

		List<String> searchHistory = redisTemplate.opsForList().range(StingKey, 0, sResult);
		if (searchHistory.contains(input)) {
			redisTemplate.opsForList().remove(StingKey, searchHistory.indexOf(input), input);
			redisTemplate.opsForList().leftPush(StingKey, input);
		} else {
			if (sResult > 9) {
				redisTemplate.opsForList().rightPop(StingKey);
				redisTemplate.opsForList().leftPush(StingKey, input);
			} else {
				redisTemplate.opsForList().leftPush(StingKey, input);
			}
		}
		}catch(Exception e1){
			logger.fatal("setSearchHistory failed:" + input+" StingKey:"+StingKey+" message:"+e1.getMessage()+" token:"+token);
		}
/*		if(tokenIn.getIsLogin()==0){
			redisTemplate.expire(StingKey, 7, TimeUnit.DAYS);
		}	*/	
	}
	public List<com.ai.entity.GoodsInCat> categoryList(int mc_id, int ex_id,
			List<GoodsInCat> catList) {
		com.ai.entity.GoodsInCat goodsCat = new com.ai.entity.GoodsInCat();

		goodsCat.setCat_second_id(mc_id);
		goodsCat.setCat_third_id(ex_id);
		catList.add(goodsCat);

		// System.out.println("finish");
		return catList;
	}

	public Map<String, String> findCatName(String key, List<CategoryForShow> tempList) {

		Map<String, String> result = new HashMap<String, String>();
		for (CategoryForShow cat : tempList) {
			if (Integer.valueOf(key) == cat.getCat_id()) {
				result.put("cat_name", cat.getCat_name());
				result.put("cat_ename", cat.getCat_ename());
				result.put("parent_id", String.valueOf(cat.getParent_id()));
				result.put("is_show", String.valueOf(cat.getIs_show()));
				break;
			}
		}

		return result;
	}

	@Test
	public void testUTC() throws ParseException {
		String string = "2016-07-05T21:38:55.105Z";
		String defaultTimezone = TimeZone.getDefault().getID();
		Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(string.replaceAll(
				"Z$", "+0000"));

		System.out.println("string: " + string);
		System.out.println("defaultTimezone: " + defaultTimezone);
		System.out.println("date: "
				+ (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).format(date));

		LocalDateTime localtDateAndTime = LocalDateTime.now();
		ZoneId zoneId = ZoneId.of("America/Los_Angeles");
		ZonedDateTime dateAndTimeInLA = ZonedDateTime.of(localtDateAndTime, zoneId);
	}

	@Test
	public void testAnalyzer() throws ParseException, IOException {
		String indexName = "yami_search";
		String typeName = "goods_info";
		String indexName2 = "key_words";
		String typeName2 = "goods_name";
		String script = "{\n" + "    \"script\" : \"ctx._source.click_count += 1\",\n"
				+ "    \"upsert\" : {\n" + "        \"click_count\" : 0\n" + ",\n"
				+ " \"activity_point\" : 0\n" + "    }\n" + "}";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		URL url = SearchServiceDelegate.class.getResource("elastic-searchAll.json");
		String searchJson = Resources.toString(url, Charsets.UTF_8);

		Search.Builder searchBuilder = new Search.Builder(searchJson).addIndex(indexName).addType(
				typeName);
		SearchResult sresult = client.execute(searchBuilder.build());

		List<GoodsNameInfo> goodsNames = sresult.getSourceAsObjectList(GoodsNameInfo.class);
		for (GoodsNameInfo goodsNameInfo : goodsNames) {
			Analyze action = new Analyze.Builder().analyzer("ik_smart")
					.text(goodsNameInfo.getGoods_name() + " " + goodsNameInfo.getGoods_ename())
					.build();
			JestResult result = client.execute(action);
			JsonObject resultObj = result.getJsonObject();
			JsonArray tokens = resultObj.getAsJsonArray("tokens");
			// System.out.println(tokens);

			for (JsonElement ss : tokens.getAsJsonArray()) {
				if ("CN_WORD".equals(ss.getAsJsonObject().get("type").getAsString())
						|| "CN_CHAR".equals(ss.getAsJsonObject().get("type").getAsString())) {
					if (ss.getAsJsonObject().get("end_offset").getAsInt()
							- ss.getAsJsonObject().get("start_offset").getAsInt() >= 2) {
						JestResult updateResult = client.execute(new Update.Builder(script)
								.index(indexName2).type(typeName2)
								.id(ss.getAsJsonObject().get("token").getAsString()).build());
						if (!updateResult.isSucceeded()) {
							System.out.println("Failed to update id :"
									+ ss.getAsJsonObject().get("token").getAsString());
							System.out.println(String.format("Failed to update: %s",
									updateResult.getErrorMessage()));
						}
					}
				}
				if ("ENGLISH".equals(ss.getAsJsonObject().get("type").getAsString())) {

				}
			}
		}
		System.out.println("Successed update document for index :" + indexName2);
	}

	@Test
	public void testTime() throws Exception{
		System.out.println(DateUtil.getUnixTime().toString());
	}
	
	@Test
	public void testJestBulk2() throws Exception {
		String indexName = "key_words";
		String typeName = "goods_name";
		/*
		 * JestClientFactory factory = new JestClientFactory();
		 * factory.setHttpClientConfig(new
		 * HttpClientConfig.Builder("http://192.168.1.80:9200")
		 * .multiThreaded(true).build()); JestClient client =
		 * factory.getObject();
		 */

		// 批量新增的方式,效率更高
		Bulk.Builder bulkBuilder = new Bulk.Builder();
		/*
		 * for(Klarticle k:lists){ Index index = new
		 * Index.Builder(k).index(indexName).type(typeName).build();
		 * bulkBuilder.addAction(index); }
		 */
		/*
		 * GoodsInfo goods = new GoodsInfo(); goods.setGoods_id(1101);
		 * goods.setGoods_name("台湾乡味 黑米高钙芝麻糊 12包入"); Index index = new
		 * Index.Builder(goods).index(indexName).type(typeName).build();
		 * bulkBuilder.addAction(index); goods = new GoodsInfo();
		 * goods.setGoods_id(1102);
		 * goods.setGoods_name("台湾健康时代 乌发养颜无糖黑芝麻粉 500g"); index = new
		 * Index.Builder(goods).index(indexName).type(typeName).build();
		 * bulkBuilder.addAction(index);
		 */
		String script = "{" + "    \"script\" : \"ctx._source.click_count += 1\","
				+ "    \"upsert\" : {" + "        \"click_count\" : 0" + ","
				+ " \"activity_point\" : 0" + "    }," + " \"key_word\" : \"KEYWORD\"    " + "}";

		// 写取goods info, brand info ,cate info
		// List<GoodsInfo> list = new ArrayList<GoodsInfo>();
		for (int i = 0; i < 4; i++) {
			System.out.println("caculating i = " + i);
			String cnName = "";
			String enName = "";
			// System.out.println(i);
			KeyWords temp = new KeyWords();
			temp.setActivity_point(i);
			temp.setClick_count(3);
			temp.setKey_word("ss" + i);

			// Index index = new
			// Index.Builder(temp).index(indexName).type(typeName).build();
			// bulkBuilder.addAction(index);
			bulkBuilder.addAction(new Update.Builder(StringUtils.chomp(script.replace("KEYWORD",
					temp.getKey_word()))).index(indexName).type(typeName).id(temp.getKey_word())
					.build());
			// list.add(temp);

		}
		/*
		 * KeyWords temp = new KeyWords(); temp.setActivity_point(1);
		 * temp.setClick_count(3); temp.setKey_word("ss1"); JestResult result =
		 * client.execute(new Update.Builder(script.replace("KEYWORD",
		 * temp.getKey_word())) .index(indexName).type(typeName)
		 * .id(temp.getKey_word()).build());
		 */
		JestResult result = client.execute(bulkBuilder.build());
		if (!result.isSucceeded()) {
			System.out.println(String.format("Failed to insert bulk data: %s",
					result.getErrorMessage()));
		} else {
			System.out.println("Created inserting bulk data :" + indexName);
		}
		// client.shutdownClient();
	}
	
	public static boolean isNumeric(String str) {
		if (str.length() < 8)
			return false;
		for (int i = 0; i < str.length(); i++) {
			// System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
