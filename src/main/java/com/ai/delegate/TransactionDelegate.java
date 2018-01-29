package com.ai.delegate;

import java.math.BigDecimal;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ai.common.ErrorCodeEnum;
import com.ai.common.StringUtil;
import com.ai.common.YamiConstant;
import com.ai.common.YamiException;
import com.ai.entity.Cart;
import com.ai.entity.Goods;
import com.ai.entity.OrderGenerate;
import com.ai.entity.Suggestion;
import com.ai.entity.Token;
import com.ai.entity.User;
import com.ai.entity.UserAddress;
import com.ai.entity.UserProfile;
import com.ai.service.GoodsService;


@Service
public class TransactionDelegate {		
	
	@Autowired
	private GoodsService goodsService;

     
 	public void transactionUpdateSuggestion(Suggestion temp) {
		// TODO Auto-generated method stub
	     goodsService.updateSuggestion(temp);
		
	}

	public void transactionInsertSuggestion(Suggestion temp) {
		// TODO Auto-generated method stub
		goodsService.insertSuggestion(temp);
		
	}
     
}
