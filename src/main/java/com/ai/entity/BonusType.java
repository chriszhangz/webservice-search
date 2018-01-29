package com.ai.entity;

import java.math.BigDecimal;
import java.util.List;

public class BonusType {
    private Short typeId;

    private String typeName;

    private Integer isPercentOff;

    private Short isOffOnAmount;

    private BigDecimal typeMoney;

    private BigDecimal reduceAmount;

    private Byte sendType;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer sendStartDate;

    private Integer sendEndDate;

    private Integer useStartDate;

    private Integer useEndDate;

    private BigDecimal minGoodsAmount;

    private String type;

    private Integer scope;

    private Short isDelete;

    private List<UserBonus> userBonus;
    
    
    public Short getTypeId() {
        return typeId;
    }

    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getIsPercentOff() {
        return isPercentOff;
    }

    public void setIsPercentOff(Integer isPercentOff) {
        this.isPercentOff = isPercentOff;
    }

    public Short getIsOffOnAmount() {
        return isOffOnAmount;
    }

    public void setIsOffOnAmount(Short isOffOnAmount) {
        this.isOffOnAmount = isOffOnAmount;
    }

    public BigDecimal getTypeMoney() {
        return typeMoney;
    }

    public void setTypeMoney(BigDecimal typeMoney) {
        this.typeMoney = typeMoney;
    }

    public BigDecimal getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(BigDecimal reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public Byte getSendType() {
        return sendType;
    }

    public void setSendType(Byte sendType) {
        this.sendType = sendType;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getSendStartDate() {
        return sendStartDate;
    }

    public void setSendStartDate(Integer sendStartDate) {
        this.sendStartDate = sendStartDate;
    }

    public Integer getSendEndDate() {
        return sendEndDate;
    }

    public void setSendEndDate(Integer sendEndDate) {
        this.sendEndDate = sendEndDate;
    }

    public Integer getUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(Integer useStartDate) {
        this.useStartDate = useStartDate;
    }

    public Integer getUseEndDate() {
        return useEndDate;
    }

    public void setUseEndDate(Integer useEndDate) {
        this.useEndDate = useEndDate;
    }

    public BigDecimal getMinGoodsAmount() {
        return minGoodsAmount;
    }

    public void setMinGoodsAmount(BigDecimal minGoodsAmount) {
        this.minGoodsAmount = minGoodsAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

	public List<UserBonus> getUserBonus() {
		return userBonus;
	}

	public void setUserBonus(List<UserBonus> userBonus) {
		this.userBonus = userBonus;
	}
    
}