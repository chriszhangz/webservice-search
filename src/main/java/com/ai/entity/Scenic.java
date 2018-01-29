package com.ai.entity;

import java.util.Date;

/**
 * @author Arthur
 */

public class Scenic {
		

	private int id;
	private String name;
	private String name_zh;
	private String name_short;
	private String name_full;
	private String type;
	private int destination_id;
	private String destination_name;
	private String destination_name_zh;
	private double northeast_lat;
	private double northeast_lng;
	private double southwest_lat;
	private double southwest_lng;
	private int sequence;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName_zh() {
		return name_zh;
	}
	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}
	public String getName_short() {
		return name_short;
	}
	public void setName_short(String name_short) {
		this.name_short = name_short;
	}
	public String getName_full() {
		return name_full;
	}
	public void setName_full(String name_full) {
		this.name_full = name_full;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDestination_id() {
		return destination_id;
	}
	public void setDestination_id(int destination_id) {
		this.destination_id = destination_id;
	}
	public String getDestination_name() {
		return destination_name;
	}
	public void setDestination_name(String destination_name) {
		this.destination_name = destination_name;
	}
	public String getDestination_name_zh() {
		return destination_name_zh;
	}
	public void setDestination_name_zh(String destination_name_zh) {
		this.destination_name_zh = destination_name_zh;
	}
	public double getNortheast_lat() {
		return northeast_lat;
	}
	public void setNortheast_lat(double northeast_lat) {
		this.northeast_lat = northeast_lat;
	}
	public double getNortheast_lng() {
		return northeast_lng;
	}
	public void setNortheast_lng(double northeast_lng) {
		this.northeast_lng = northeast_lng;
	}
	public double getSouthwest_lat() {
		return southwest_lat;
	}
	public void setSouthwest_lat(double southwest_lat) {
		this.southwest_lat = southwest_lat;
	}
	public double getSouthwest_lng() {
		return southwest_lng;
	}
	public void setSouthwest_lng(double southwest_lng) {
		this.southwest_lng = southwest_lng;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
}
