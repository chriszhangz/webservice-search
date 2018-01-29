package com.ai.entity;

import java.math.BigDecimal;

public class GoodsOfCatItems {
	private int goods_id;

    private String goods_name;

    private String goods_ename;
    
    private BigDecimal shop_price;

    private BigDecimal promote_price;

    private int is_promote;
    
	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_ename() {
		return goods_ename;
	}

	public void setGoods_ename(String goods_ename) {
		this.goods_ename = goods_ename;
	}

	public BigDecimal getShop_price() {
		return shop_price;
	}

	public void setShop_price(BigDecimal shop_price) {
		this.shop_price = shop_price;
	}

	public BigDecimal getPromote_price() {
		return promote_price;
	}

	public void setPromote_price(BigDecimal promote_price) {
		this.promote_price = promote_price;
	}

	public int getIs_promote() {
		return is_promote;
	}

	public void setIs_promote(int is_promote) {
		this.is_promote = is_promote;
	}

}