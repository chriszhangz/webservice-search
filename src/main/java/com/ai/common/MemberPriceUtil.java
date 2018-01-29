package com.ai.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemberPriceUtil {

	private static Map<Integer, String> map = new LinkedHashMap<Integer, String>();
	static {
		map.put(16, "0.9");
		map.put(17, "0.9");
		map.put(18, "0.9");
		map.put(19, "0.9");
		map.put(20, "0.9");
		map.put(21, "0.88");
		map.put(22, "0.9");
		map.put(23, "0.9");
		map.put(24, "0.88");
		map.put(25, "0.9");
		map.put(26, "0.9");
		map.put(27, "0.88");
		map.put(28, "0.9");
		map.put(29, "0.9");
		map.put(30, "0.9");
		map.put(31, "0.88");
	}

	public static BigDecimal getMemberPrice(BigDecimal price) {
		String default_percent = "0.9";
		return price.multiply(new BigDecimal(default_percent)).setScale(2, RoundingMode.FLOOR);
	}

	public static BigDecimal getMemberPrice(BigDecimal price, int cat_id) {
		String percent = map.get(cat_id);
		if (null == percent) {
			return getMemberPrice(price);
		}
		return price.multiply(new BigDecimal(percent)).setScale(2, RoundingMode.FLOOR);
	}

}
