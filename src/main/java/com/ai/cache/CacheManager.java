package com.ai.cache; 

import java.util.*; 

import org.springframework.data.redis.core.RedisTemplate;

import com.ai.entity.CategoryForShow;
import com.ai.service.GoodsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//Description: 管理缓存 

//可扩展的功能：当chche到内存溢出时必须清除掉最早期的一些缓存对象，这就要求对每个缓存对象保存创建时间 

public class CacheManager { 
   private static HashMap cacheMap = new HashMap(); 

   //单实例构造方法 
   private CacheManager() { 
       super(); 
   } 
   //获取布尔值的缓存 
   public static boolean getSimpleFlag(String key){ 
       try{ 
           return (Boolean) cacheMap.get(key); 
       }catch(NullPointerException e){ 
           return false; 
       } 
   } 
   public static long getServerStartdt(String key){ 
       try { 
           return (Long)cacheMap.get(key); 
       } catch (Exception ex) { 
           return 0; 
       } 
   } 
   //设置布尔值的缓存 
   public synchronized static boolean setSimpleFlag(String key,boolean flag){ 
       if (flag && getSimpleFlag(key)) {//假如为真不允许被覆盖 
           return false; 
       }else{ 
           cacheMap.put(key, flag); 
           return true; 
       } 
   } 
   public synchronized static boolean setSimpleFlag(String key,long serverbegrundt){ 
       if (cacheMap.get(key) == null) { 
           cacheMap.put(key,serverbegrundt); 
           return true; 
       }else{ 
           return false; 
       } 
   } 


   //得到缓存。同步静态方法 
   //private synchronized static Cache getCache(String key) { 
   private  synchronized static Cache getCache(String key) { 
       return (Cache) cacheMap.get(key); 
   } 

   //判断是否存在一个缓存 
  // private synchronized static boolean hasCache(String key) { 
   private synchronized static boolean hasCache(String key) { 
       return cacheMap.containsKey(key); 
   } 

   //清除所有缓存 
   public synchronized static void clearAll() { 
       cacheMap.clear(); 
   } 

   //清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配 
   public synchronized static void clearAll(String type) { 
       Iterator i = cacheMap.entrySet().iterator(); 
       String key; 
       ArrayList arr = new ArrayList(); 
       try { 
           while (i.hasNext()) { 
               java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
               key = (String) entry.getKey(); 
               if (key.startsWith(type)) { //如果匹配则删除掉 
                   arr.add(key); 
               } 
           } 
           for (int k = 0; k < arr.size(); k++) { 
               clearOnly((String) arr.get(k)); 
           } 
       } catch (Exception ex) { 
           ex.printStackTrace(); 
       } 
   } 

   //清除指定的缓存 
   public synchronized static void clearOnly(String key) { 
       cacheMap.remove(key); 
   } 

   //载入缓存 
   //public synchronized static void putCache(String key, Cache obj) { 
   public synchronized static void putCache(String key, Cache obj) { 
      cacheMap.put(key, obj); 
   } 

   //获取缓存信息 
   public synchronized static Cache getCacheInfo(String key) { 
       if (hasCache(key)) 
       { 
           Cache cache = getCache(key); 
           if (cacheExpired(cache)) 
           { //调用判断是否终止方法 
               cache.setExpired(true); 
           } 
           return cache; 
       }else 
           return null; 
   } 
   //载入缓存信息 
   public static void putCacheInfo(String key, Cache obj, long dt,boolean expired) { 
       Cache cache = new Cache(); 
       cache.setKey(key); 
       cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存 
       cache.setValue(obj); 
       cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE 
       cacheMap.put(key, cache); 
   } 
   //重写载入缓存信息方法 
   public static void putCacheInfo(String key,Cache obj,long dt){ 
       Cache cache = new Cache(); 
       cache.setKey(key); 
       cache.setTimeOut(dt+System.currentTimeMillis()); 
       cache.setValue(obj); 
       cache.setExpired(false); 
       cacheMap.put(key,cache); 
   } 
   //判断缓存是否终止 
   public static boolean cacheExpired(Cache cache) { 
       if (null == cache) { //传入的缓存不存在 
           return false; 
       } 
       long nowDt = System.currentTimeMillis(); //系统当前的毫秒数 
       long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数 
       if (cacheDt <= 0||cacheDt > nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE 
           return false; 
       } else { //大于过期时间 即过期 
           return true; 
       } 
   } 
   //获取缓存中的大小 
   public synchronized static int getCacheSize() { 
       return cacheMap.size(); 
   } 
   //获取指定的类型的大小 
   public static int getCacheSize(String type) { 
       int k = 0; 
       Iterator i = cacheMap.entrySet().iterator(); 
       String key; 
       try { 
           while (i.hasNext()) { 
               java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
               key = (String) entry.getKey(); 
               if (key.indexOf(type) != -1) { //如果匹配则删除掉 
                   k++; 
               } 
           } 
       } catch (Exception ex) { 
           ex.printStackTrace(); 
       } 
       return k; 
   } 
   //获取缓存对象中的所有键值名称 
   public static ArrayList getCacheAllkey() { 
       ArrayList a = new ArrayList(); 
       try { 
           Iterator i = cacheMap.entrySet().iterator(); 
           while (i.hasNext()) { 
               java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
               a.add((String) entry.getKey()); 
           } 
       } catch (Exception ex) {} finally { 
           return a; 
       } 
   } 
   //获取缓存对象中指定类型 的键值名称 
   public static ArrayList getCacheListkey(String type) { 
       ArrayList a = new ArrayList(); 
       String key; 
       try { 
           Iterator i = cacheMap.entrySet().iterator(); 
           while (i.hasNext()) { 
               java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
               key = (String) entry.getKey(); 
               if (key.indexOf(type) != -1) { 
                   a.add(key); 
               } 
           } 
       } catch (Exception ex) {} finally { 
           return a; 
       } 
   }
public synchronized static List<String[]> loadSuggestioncatch(RedisTemplate<String, String> redisTemplate, Gson gson) {
	String suggestion_value = redisTemplate.opsForValue().get("Suggestion");
	List<String[]>ListTemp = gson.fromJson(suggestion_value, new TypeToken<List<String[]>>(){}.getType());
	Cache s= new Cache();
	s.setTimeOut(System.currentTimeMillis()+3600000);
	CacheManager.clearOnly("suggestion");
	CacheManager.putCache("suggestion", s);
	//s=null;
	return ListTemp;	
}
public synchronized static void loadResultCatch(RedisTemplate<String, String> redisTemplate) {
	// TODO Auto-generated method stub
	Cache c= new Cache();
	c.setValue(redisTemplate.opsForValue().get("Result"));
	c.setTimeOut(System.currentTimeMillis()+3600000);
	CacheManager.clearOnly("result");
	CacheManager.putCache("result", c);
	//c=null;
}
public synchronized static void loadCategoryCatch(GoodsService goodsService) {
	// TODO Auto-generated method stub
	List<CategoryForShow> tempList = goodsService.selectShowCategory();
	Cache t= new Cache();
	t.setValue(tempList);
	t.setTimeOut(System.currentTimeMillis()+3600000);
	CacheManager.clearOnly("category");
	CacheManager.putCache("category", t);
	//t=null;
} 
} 