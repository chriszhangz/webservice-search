package com.ai.parser;

public class CatInfo
{
	
	private Integer cat_id;
	
	private String cat_name;
	
	private String cat_ename;
	
	private Short parent_id;
	
	public Short getParent_id() {
		return parent_id;
	}

	public void setParent_id(Short parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getCat_id() {
		return cat_id;
	}

	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
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
	
}