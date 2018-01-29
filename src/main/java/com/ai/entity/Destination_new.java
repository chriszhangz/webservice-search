package com.ai.entity;

import java.util.Date;

/**
 * @author Arthur
 */

public class Destination_new {
		
	public int getDesId() {
		return desId;
	}
	public void setDesId(int desId) {
		this.desId = desId;
	}
	public String getDesName() {
		return desName;
	}
	public void setDesName(String desName) {
		this.desName = desName;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	

	public String getDesName_zh() {
		return desName_zh;
	}
	public void setDesName_zh(String desName_zh) {
		this.desName_zh = desName_zh;
	}
	public String getState_zh() {
		return state_zh;
	}
	public void setState_zh(String state_zh) {
		this.state_zh = state_zh;
	}
	public String getCity_zh() {
		return city_zh;
	}
	public void setCity_zh(String city_zh) {
		this.city_zh = city_zh;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	public String getOriginalDest() {
		return originalDest;
	}
	public void setOriginalDest(String originalDest) {
		this.originalDest = originalDest;
	}
	public String getOriginalState() {
		return originalState;
	}
	public void setOriginalState(String originalState) {
		this.originalState = originalState;
	}
	public String getOriginalRegion() {
		return originalRegion;
	}
	public void setOriginalRegion(String originalRegion) {
		this.originalRegion = originalRegion;
	}

	public int getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(int zoneCode) {
		this.zoneCode = zoneCode;
	}

	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}

	private int desId;
	private int regionId;
	private String desName;
	private int countryId;
	private int display;
	private String state;
	private String city;
	private String desName_zh;
	private String state_zh;
	private String city_zh;
	private String originalId;
	private String originalDest;
	private String originalState;
	private String originalRegion;
	private int zoneCode;
	private int priority;
	private Date createdAt;
	private Date updatedAt;
}
