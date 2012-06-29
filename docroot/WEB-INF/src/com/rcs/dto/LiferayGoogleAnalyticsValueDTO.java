package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiferayGoogleAnalyticsValueDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	List<String> data = new ArrayList<String>();
	private String sparkline;
	private String value = "0";
	private String delta = "+100%";
	
	
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public String getSparkline() {
		return sparkline;
	}
	public void setSparkline(String sparkline) {
		this.sparkline = sparkline;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDelta() {
		return delta;
	}
	public void setDelta(String delta) {
		this.delta = delta;
	}
	public void addData(String value){
		data.add(value);
	}
	
	

}
