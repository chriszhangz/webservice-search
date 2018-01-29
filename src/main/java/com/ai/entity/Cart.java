package com.ai.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class Cart {
	
	private Integer rec_id;

	private Integer user_id;
	
	private String session_id;
	
	private Integer vendorId;
	
	private Integer goods_id;
	
	private String goods_sn;
	
	private Integer product_id;
	
	private String goods_name;
	
	private String goods_ename;

    private BigDecimal cost;

    private BigDecimal tax;

    private BigDecimal market_price;

    private BigDecimal goods_price;

    private Short goods_number;

    private Boolean is_real;

    private String extension_code;

    private Integer parent_id;

    private Boolean rec_type;

    private Short is_gift;

    private Boolean is_shipping;

    private Byte can_handsel;

    private String goods_attr_id;

    private BigDecimal deal_price;

    private String goods_attr;

    private Map<String,String> mapActivity;
    
	public Integer getRec_id() {
		return rec_id;
	}

	public void setRec_id(Integer rec_id) {
		this.rec_id = rec_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public Integer getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getMarket_price() {
		return market_price;
	}

	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}

	public BigDecimal getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(BigDecimal goods_price) {
		this.goods_price = goods_price;
	}

	public Short getGoods_number() {
		return goods_number;
	}

	public void setGoods_number(Short goods_number) {
		this.goods_number = goods_number;
	}

	public Boolean getIs_real() {
		return is_real;
	}

	public void setIs_real(Boolean is_real) {
		this.is_real = is_real;
	}

	public String getExtension_code() {
		return extension_code;
	}

	public void setExtension_code(String extension_code) {
		this.extension_code = extension_code;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public Boolean getRec_type() {
		return rec_type;
	}

	public void setRec_type(Boolean rec_type) {
		this.rec_type = rec_type;
	}

	public Short getIs_gift() {
		return is_gift;
	}

	public void setIs_gift(Short is_gift) {
		this.is_gift = is_gift;
	}

	public Boolean getIs_shipping() {
		return is_shipping;
	}

	public void setIs_shipping(Boolean is_shipping) {
		this.is_shipping = is_shipping;
	}

	public Byte getCan_handsel() {
		return can_handsel;
	}

	public void setCan_handsel(Byte can_handsel) {
		this.can_handsel = can_handsel;
	}

	public String getGoods_attr_id() {
		return goods_attr_id;
	}

	public void setGoods_attr_id(String goods_attr_id) {
		this.goods_attr_id = goods_attr_id;
	}

	public BigDecimal getDeal_price() {
		return deal_price;
	}

	public void setDeal_price(BigDecimal deal_price) {
		this.deal_price = deal_price;
	}

	public String getGoods_attr() {
		return goods_attr;
	}

	public void setGoods_attr(String goods_attr) {
		this.goods_attr = goods_attr;
	}

	public Map<String, String> getMapActivity() {
		return mapActivity;
	}

	public void setMapActivity(Map<String, String> mapActivity) {
		this.mapActivity = mapActivity;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}



    
}
