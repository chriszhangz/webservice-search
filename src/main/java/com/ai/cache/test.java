package com.ai.cache;
 
import com.ai.cache.CacheManager;
 
/**
 * @author Crunchify.com
 */
 
public class test {
 
	public static void main(String[] args) { 
		// CacheManager.putCache("abc", new Cache());
		
		Cache c= new Cache();
		c.setValue("good");
		CacheManager.putCache("result", c);
        System.out.println(CacheManager.getCacheInfo("result").getValue().toString()); 

        
      
 
 
    } 

}