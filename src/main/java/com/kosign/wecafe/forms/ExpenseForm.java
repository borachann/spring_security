package com.kosign.wecafe.forms;

public class ExpenseForm {
	private long quantity;
	private String proname;
	private long UnitPrice;
	private String suppliername;
	private String remark;
	private long expId;
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public long getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(long unitPrice) {
		UnitPrice = unitPrice;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getExpId() {
		return expId;
	}
	public void setExpId(long expId) {
		this.expId = expId;
	}
	
	
}
