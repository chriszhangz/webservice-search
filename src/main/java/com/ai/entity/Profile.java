package com.ai.entity;

import java.math.BigDecimal;

public class Profile {
	
	private BigDecimal profile_id;	
	
	private String firstname;
	
	private String lastname;
	
	private Integer tail;
	
	private Integer expiry;

	private String type;

	private Integer is_primary;
	
	private Address address;
	
	public BigDecimal getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(BigDecimal profile_id) {
		this.profile_id = profile_id;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIs_primary() {
		return is_primary;
	}

	public void setIs_primary(Integer is_primary) {
		this.is_primary = is_primary;
	}

}
