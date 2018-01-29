package com.ai.entity;

import io.searchbox.annotations.JestId;

import java.math.BigDecimal;

public class KeyWords {
	@JestId
    private String key_word;
    private Integer click_count;
    private Integer activity_point;
	public String getKey_word() {
		return key_word;
	}
	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}
	public Integer getClick_count() {
		return click_count;
	}
	public void setClick_count(Integer click_count) {
		this.click_count = click_count;
	}
	public Integer getActivity_point() {
		return activity_point;
	}
	public void setActivity_point(Integer activity_point) {
		this.activity_point = activity_point;
	}

	    

}
