package com.ai.entity;

import java.math.BigDecimal;

public class UserProfile {
	
	private BigDecimal profile_id;
	
	private Integer user_id;
	
	private String firstname;
	
	private String lastname;
	
	private Integer tail;
	
	private Integer expiry;
	
	private Integer address_id;
	
	private String card_type;
	
	private Integer csc;
	
	private Integer is_primary;

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public BigDecimal getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(BigDecimal profile_id) {
		this.profile_id = profile_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getTail() {
		return tail;
	}

	public void setTail(Integer tail) {
		this.tail = tail;
	}

	public Integer getExpiry() {
		return expiry;
	}

	public void setExpiry(Integer expiry) {
		this.expiry = expiry;
	}

	public Integer getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}

	public Integer getCsc() {
		return csc;
	}

	public void setCsc(Integer csc) {
		this.csc = csc;
	}

	public Integer getIs_primary() {
		return is_primary;
	}

	public void setIs_primary(Integer is_primary) {
		this.is_primary = is_primary;
	}


}
