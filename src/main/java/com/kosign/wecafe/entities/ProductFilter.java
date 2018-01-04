package com.kosign.wecafe.entities;

import java.util.Date;

public class ProductFilter {
	
	private Date startDate;
	private Date endDate;
	private int byYear;
	public int getByYear() {
		return byYear;
	}
	public void setByYear(int byYear) {
		this.byYear = byYear;
	}
	private int displayType;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getDisplayType() {
		return displayType;
	}
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}
	
}
