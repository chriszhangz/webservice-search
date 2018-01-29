package com.ai.common;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;

public class ESFactory {
	private static JestHttpClient client;

	
    private ESFactory() {
 
    }
 
    public synchronized static JestHttpClient getClient() {
        if (client == null) {
            JestClientFactory factory = new JestClientFactory();
            ArrayList<String> servers = new ArrayList<String>();
            servers.add("http://35.167.235.10:9200");
            //servers.add("http://172.31.30.224:9200");
            /*servers.add("http://192.168.1.80:9200");*/
            factory.setHttpClientConfig(new HttpClientConfig.Builder(
            		servers).connTimeout(5000).readTimeout(5000).maxTotalConnection(4000).multiThreaded(true).build());
        //    		"http://172.31.30.224:9200").multiThreaded(true).build());
		//"http://192.168.1.80:9200").multiThreaded(true).build());
            client = (JestHttpClient) factory.getObject();
        }
        return client;
    }
 

}
