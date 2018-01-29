package com.ai.parser;

import java.math.BigDecimal;

public class Goods {
	
	    private Integer goodsId;

	    private String itemNumber;
	    
	    private String goodsName;

	    private String goodsEname;
	    
		private Integer catId;
		
	    private Integer vendorId;
		
		private Integer brandId;
		
		private Integer clickCount;

		private Integer goodsNumber;
		
		private BigDecimal shopPrice;
		
		private BigDecimal promotePrice;

		private Integer firstAddTime;
		private Integer promoteStartDate;
		private Integer promoteEndDate;

		private Integer integral;
		
		private Byte isPromote;
	    
		private String goodsImg;
		
		private String keywords;
		
	    private BigDecimal goodsWeight;
	    
	    private String brandName;
	    		
		private Integer giveIntegral;

		private String weightUnit;
		
		private Integer addTime;
		
		private String goodsThumb;

		private String prefixCn;
		private String suffixCn;
		private String prefixEn;
		private String suffixEn;
		private Byte isDelete;
		private Byte isOnSale;
		private String upcCode;
		private Integer tagId;
		private String tag;
		private String tagEng;
		private String status;
		
		public String getUpcCode() {
			return upcCode;
		}
		public void setUpcCode(String upcCode) {
			this.upcCode = upcCode;
		}
		public Integer getTagId() {
			return tagId;
		}
		public void setTagId(Integer tagId) {
			this.tagId = tagId;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public String getTagEng() {
			return tagEng;
		}
		public void setTagEng(String tagEng) {
			this.tagEng = tagEng;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Integer getPromoteStartDate() {
			return promoteStartDate;
		}
		public void setPromoteStartDate(Integer promoteStartDate) {
			this.promoteStartDate = promoteStartDate;
		}
		public Integer getPromoteEndDate() {
			return promoteEndDate;
		}
		public void setPromoteEndDate(Integer promoteEndDate) {
			this.promoteEndDate = promoteEndDate;
		}
		public String getItemNumber() {
			return itemNumber;
		}
		public void setItemNumber(String itemNumber) {
			this.itemNumber = itemNumber;
		}
		public Byte getIsDelete() {
			return isDelete;
		}
		public void setIsDelete(Byte isDelete) {
			this.isDelete = isDelete;
		}
		public Byte getIsOnSale() {
			return isOnSale;
		}
		public void setIsOnSale(Byte isOnSale) {
			this.isOnSale = isOnSale;
		}
		public Integer getVendorId() {
			return vendorId;
		}
		public void setVendorId(Integer vendorId) {
			this.vendorId = vendorId;
		}
		public String getPrefixCn() {
			return prefixCn;
		}
		public void setPrefixCn(String prefixCn) {
			this.prefixCn = prefixCn;
		}
		public String getSuffixCn() {
			return suffixCn;
		}
		public void setSuffixCn(String suffixCn) {
			this.suffixCn = suffixCn;
		}
		public String getPrefixEn() {
			return prefixEn;
		}
		public void setPrefixEn(String prefixEn) {
			this.prefixEn = prefixEn;
		}
		public String getSuffixEn() {
			return suffixEn;
		}
		public void setSuffixEn(String suffixEn) {
			this.suffixEn = suffixEn;
		}
		public String getGoodsThumb() {
			return goodsThumb;
		}
		public void setGoodsThumb(String goodsThumb) {
			this.goodsThumb = goodsThumb;
		}
		public Integer getAddTime() {
			return addTime;
		}
		public void setAddTime(Integer addTime) {
			this.addTime = addTime;
		}
		
    	
		public String getWeightUnit() {
			return weightUnit;
		}

		public void setWeightUnit(String weightUnit) {
			this.weightUnit = weightUnit;
		}
		
		
	    public Integer getGiveIntegral() {
			return giveIntegral;
		}

		public void setGiveIntegral(Integer giveIntegral) {
			this.giveIntegral = giveIntegral;
		}	
		
	    public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public BigDecimal getGoodsWeight() {
			return goodsWeight;
		}

		public void setGoodsWeight(BigDecimal goodsWeight) {
			this.goodsWeight = goodsWeight;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		 public String getGoodsImg() {
			return goodsImg;
		}

		public void setGoodsImg(String goodsImg) {
			this.goodsImg = goodsImg;
		}

		public Integer getIntegral() {
			return integral;
		}

		public void setIntegral(Integer integral) {
			this.integral = integral;
		}
		
		
		public Integer getClickCount() {
			return clickCount;
		}

		public void setClickCount(Integer clickCount) {
			this.clickCount = clickCount;
		}

		public Integer getGoodsNumber() {
			return goodsNumber;
		}

		public void setGoodsNumber(Integer goodsNumber) {
			this.goodsNumber = goodsNumber;
		}

		public BigDecimal getShopPrice() {
			return shopPrice;
		}

		public void setShopPrice(BigDecimal shopPrice) {
			this.shopPrice = shopPrice;
		}

		public BigDecimal getPromotePrice() {
			return promotePrice;
		}

		public void setPromotePrice(BigDecimal promotePrice) {
			this.promotePrice = promotePrice;
		}

		public Integer getFirstAddTime() {
			return firstAddTime;
		}

		public void setFirstAddTime(Integer firstAddTime) {
			this.firstAddTime = firstAddTime;
		}

		public Byte getIsPromote() {
			return isPromote;
		}

		public void setIsPromote(Byte isPromote) {
			this.isPromote = isPromote;
		}

	    public Integer getBrandId() {
			return brandId;
		}

		public void setBrandId(Integer brandId) {
			this.brandId = brandId;
		}
		
	    public Integer getCatId() {
			return catId;
		}

		public void setCatId(Integer catId) {
			this.catId = catId;
		}

		public Integer getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(Integer goodsId) {
			this.goodsId = goodsId;
		}

		
		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public String getGoodsEname() {
			return goodsEname;
		}

		public void setGoodsEname(String goodsEname) {
			this.goodsEname = goodsEname;
		}
}
