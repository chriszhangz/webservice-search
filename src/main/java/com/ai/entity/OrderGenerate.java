package com.ai.entity;

import java.math.BigDecimal;

public class OrderGenerate {
	private String tempId;

    private Integer userId;

    private Integer bonusId;

    private Integer shippingId;

    private Integer shippingAdd;

    private BigDecimal profileId;

    
    public Integer getPointFlag() {
		return pointFlag;
	}

	public void setPointFlag(Integer pointFlag) {
		this.pointFlag = pointFlag;
	}

	private Integer pointFlag;

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBonusId() {
		return bonusId;
	}

	public void setBonusId(Integer bonusId) {
		this.bonusId = bonusId;
	}

	public Integer getShippingId() {
		return shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public Integer getShippingAdd() {
		return shippingAdd;
	}

	public void setShippingAdd(Integer shippingAdd) {
		this.shippingAdd = shippingAdd;
	}

	public BigDecimal getProfileId() {
		return profileId;
	}

	public void setProfileId(BigDecimal profileId) {
		this.profileId = profileId;
	}





    
}