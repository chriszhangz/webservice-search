package com.ai.entity;

import io.searchbox.annotations.JestId;

import java.math.BigDecimal;

public class GoodsInfo {
	@JestId
	private Integer goods_id;
	private String item_number;
	private Integer cat_id;
	private Integer vendor_id;
	private Integer scat_id;
	private Integer fcat_id;
    private String cat_name;
    private String cat_ename;
    private Integer brand_id;
    private String brand_name;
    private String brand_ename;
    private String goods_name;
    private String goods_ename;
    private Integer click_count;
    private Integer first_add_time;
    private Integer promote_start_date;
    private Integer promote_end_date;
    private Integer is_promote;
    private Integer is_delete;
    private Integer is_on_sale;
    private BigDecimal promote_price;
    private BigDecimal shop_price;
    private Integer has_number;
    private Integer goods_number;
    private String image;
    private Integer parent_id;
    private String alphabetic_index;
	private BigDecimal score;
    private Integer is_book;
	private String upc_code;
	private Integer tag_id;
	private String tag;
	private String tag_eng;
	private String status;


		public GoodsInfo() {
			this.goods_id = 0;
			this.item_number = "";
			this.cat_id = 0;
			this.vendor_id = 0;
			this.scat_id = 0;
			this.fcat_id = 0;
			this.cat_name = "";
			this.cat_ename = "";
			this.brand_id = 0;
			this.brand_name = "";
			this.brand_ename = "";
			this.goods_name = "";
			this.goods_ename = "";
			this.click_count = 0;
			this.first_add_time = 0;
			this.promote_start_date = 0;
			this.promote_end_date = 0;
			this.is_promote = 0;
			this.is_delete = 0;
			this.is_on_sale = 1;
			this.promote_price = new BigDecimal(0);
			this.shop_price = new BigDecimal(0);
			this.has_number = 0;
			this.goods_number = 0;
			this.image = "";
			this.parent_id = 0;
			this.alphabetic_index = "#";
			this.score = new BigDecimal(0);
			this.upc_code="";
			this.tag_id=0;
			this.tag="";
			this.tag_eng="";
			this.status="";
			
			// TODO Auto-generated constructor stub
		}

		public String getUpc_code() {
			return upc_code;
		}

		public void setUpc_code(String upc_code) {
			this.upc_code = upc_code;
		}

		public Integer getTag_id() {
			return tag_id;
		}

		public void setTag_id(Integer tag_id) {
			this.tag_id = tag_id;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getTag_eng() {
			return tag_eng;
		}

		public void setTag_eng(String tag_eng) {
			this.tag_eng = tag_eng;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Integer getPromote_start_date() {
			return promote_start_date;
		}

		public void setPromote_start_date(Integer promote_start_date) {
			this.promote_start_date = promote_start_date;
		}

		public Integer getPromote_end_date() {
			return promote_end_date;
		}

		public void setPromote_end_date(Integer promote_end_date) {
			this.promote_end_date = promote_end_date;
		}

		public Integer getIs_book() {
			return is_book;
		}

		public void setIs_book(Integer is_book) {
			this.is_book = is_book;
		}

		public Integer getHas_number() {
			return has_number;
		}

		public void setHas_number(Integer has_number) {
			this.has_number = has_number;
		}

		public String getItem_number() {
			return item_number;
		}

		public void setItem_number(String item_number) {
			this.item_number = item_number;
		}

		public Integer getIs_delete() {
			return is_delete;
		}

		public void setIs_delete(Integer is_delete) {
			this.is_delete = is_delete;
		}

		public Integer getIs_on_sale() {
			return is_on_sale;
		}

		public void setIs_on_sale(Integer is_on_sale) {
			this.is_on_sale = is_on_sale;
		}

		public Integer getVendor_id() {
			return vendor_id;
		}

		public void setVendor_id(Integer vendor_id) {
			this.vendor_id = vendor_id;
		}

		public Integer getGoods_id() {
			return goods_id;
		}

		public void setGoods_id(Integer goods_id) {
			this.goods_id = goods_id;
		}

		public String getGoods_name() {
			return goods_name;
		}

		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}

		public Integer getCat_id() {
			return cat_id;
		}

		public void setCat_id(Integer cat_id) {
			this.cat_id = cat_id;
		}

		public Integer getScat_id() {
			return scat_id;
		}

		public void setScat_id(Integer scat_id) {
			this.scat_id = scat_id;
		}

		public Integer getFcat_id() {
			return fcat_id;
		}

		public void setFcat_id(Integer fcat_id) {
			this.fcat_id = fcat_id;
		}

		public String getCat_name() {
			return cat_name;
		}

		public void setCat_name(String cat_name) {
			this.cat_name = cat_name;
		}

		public String getCat_ename() {
			return cat_ename;
		}

		public void setCat_ename(String cat_ename) {
			this.cat_ename = cat_ename;
		}

		public Integer getBrand_id() {
			return brand_id;
		}

		public void setBrand_id(Integer brand_id) {
			this.brand_id = brand_id;
		}

		public String getBrand_name() {
			return brand_name;
		}

		public void setBrand_name(String brand_name) {
			this.brand_name = brand_name;
		}

		public String getBrand_ename() {
			return brand_ename;
		}

		public void setBrand_ename(String brand_ename) {
			this.brand_ename = brand_ename;
		}

		public String getGoods_ename() {
			return goods_ename;
		}

		public void setGoods_ename(String goods_ename) {
			this.goods_ename = goods_ename;
		}

		public Integer getClick_count() {
			return click_count;
		}

		public void setClick_count(Integer click_count) {
			this.click_count = click_count;
		}

		public Integer getFirst_add_time() {
			return first_add_time;
		}

		public void setFirst_add_time(Integer first_add_time) {
			this.first_add_time = first_add_time;
		}

		public Integer getIs_promote() {
			return is_promote;
		}

		public void setIs_promote(Integer is_promote) {
			this.is_promote = is_promote;
		}

		public BigDecimal getPromote_price() {
			return promote_price;
		}

		public void setPromote_price(BigDecimal promote_price) {
			this.promote_price = promote_price;
		}

		public BigDecimal getShop_price() {
			return shop_price;
		}

		public void setShop_price(BigDecimal shop_price) {
			this.shop_price = shop_price;
		}

		public Integer getGoods_number() {
			return goods_number;
		}

		public void setGoods_number(Integer goods_number) {
			this.goods_number = goods_number;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Integer getParent_id() {
			return parent_id;
		}

		public void setParent_id(Integer parent_id) {
			this.parent_id = parent_id;
		}

		public String getAlphabetic_index() {
			return alphabetic_index;
		}

		public void setAlphabetic_index(String alphabetic_index) {
			this.alphabetic_index = alphabetic_index;
		}

		public BigDecimal getScore() {
			return score;
		}

		public void setScore(BigDecimal score) {
			this.score = score;
		}


	    

}
