package com.ai.common;

public enum ErrorCodeEnum {
	ER1001("Token expired."),
	ER1002("Token service failed."),
	ER1003("Invalid token."),
	ER1004("Need login"),
	ER1005("Database error occurred."),	
	ER1006("An exception occurred."),
	

	
	//UserService ErrorCode
	ER1301("Invalid user and password."),
	ER1311("Cannot process profile. A error has occurred."),
	ER1312("Cannot process profile. Profile does not exist."),
	ER1321("The address does not exist."),
	/*---------------Cart Service ErrorCode Area------------------*/
	//商品已经下架
	ER1101("Sorry, the sales have been overdue."),
	//够买的数量已经超过限购数量
	ER1102("more than shortage_limited"),
	//库存不足暂停销售
	ER1103("Out-of-stock and stop sales"),
	//购物车更新失败
	ER1104("update cart fail"),
	//使用折扣码失败
	ER1105("Apply Coupon failed");
	/*---------------Cart Service ErrorCode Area------------------*/
	
	
	
	
	
	
	
	
	
	
	
	
	

	private String msg;

	private ErrorCodeEnum(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
