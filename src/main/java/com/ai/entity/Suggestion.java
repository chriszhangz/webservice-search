package com.ai.entity;

import java.math.BigDecimal;

public class Suggestion {
	
	private BigDecimal krank;	
	private BigDecimal vrank;	
	private BigDecimal kdemo;	
	private BigDecimal vdemo;	
	private String k;
	private String v;

	public BigDecimal getKrank() {
		return krank;
	}
	public void setKrank(BigDecimal krank) {
		this.krank = krank;
	}
	public BigDecimal getVrank() {
		return vrank;
	}
	public void setVrank(BigDecimal vrank) {
		this.vrank = vrank;
	}
	public BigDecimal getKdemo() {
		return kdemo;
	}
	public void setKdemo(BigDecimal kdemo) {
		this.kdemo = kdemo;
	}
	public BigDecimal getVdemo() {
		return vdemo;
	}
	public void setVdemo(BigDecimal vdemo) {
		this.vdemo = vdemo;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
}