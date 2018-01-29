package com.ai.parser;

import java.math.BigDecimal;
import java.util.Date;

public class Dests {
	
	    private Integer desId;

	    private String desName;
	    
	    private String desName_zh;

	    private Integer display;
	    
		private Integer countryId;
		
	    private String state;
		
		private String state_zh;
		
		private String city;

		private String city_zh;
		
		private Integer priority;
		
		private Integer click_count;

		private Date created_at;
		private Date updated_at;
		public Integer getDesId() {
			return desId;
		}
		public void setDesId(Integer desId) {
			this.desId = desId;
		}
		public String getDesName() {
			return desName;
		}
		public void setDesName(String desName) {
			this.desName = desName;
		}
		public String getDesName_zh() {
			return desName_zh;
		}
		public void setDesName_zh(String desName_zh) {
			this.desName_zh = desName_zh;
		}
		public Integer getDisplay() {
			return display;
		}
		public void setDisplay(Integer display) {
			this.display = display;
		}
		public Integer getCountryId() {
			return countryId;
		}
		public void setCountryId(Integer countryId) {
			this.countryId = countryId;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getState_zh() {
			return state_zh;
		}
		public void setState_zh(String state_zh) {
			this.state_zh = state_zh;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCity_zh() {
			return city_zh;
		}
		public void setCity_zh(String city_zh) {
			this.city_zh = city_zh;
		}
		public Integer getPriority() {
			return priority;
		}
		public void setPriority(Integer priority) {
			this.priority = priority;
		}
		public Integer getClick_count() {
			return click_count;
		}
		public void setClick_count(Integer click_count) {
			this.click_count = click_count;
		}
		public Date getCreated_at() {
			return created_at;
		}
		public void setCreated_at(Date created_at) {
			this.created_at = created_at;
		}
		public Date getUpdated_at() {
			return updated_at;
		}
		public void setUpdated_at(Date updated_at) {
			this.updated_at = updated_at;
		}

}
