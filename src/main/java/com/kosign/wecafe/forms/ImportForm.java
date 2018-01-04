package com.kosign.wecafe.forms;

import java.math.BigDecimal;

public class ImportForm {

	private long quantity;
	private long proId;
	private BigDecimal UnitPrice;
	private long supplierId;
	private long impId;
	private BigDecimal saleprice;
	private BigDecimal cost_price;
	
	
	public BigDecimal getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
	public BigDecimal getCost_price() {
		return cost_price;
	}
	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}
	public long getImpId() {
		return impId;
	}
	public void setImpId(long impId) {
		this.impId = impId;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public long getProId() {
		return proId;
	}
	public void setProId(long proId) {
		this.proId = proId;
	}
	public BigDecimal getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		UnitPrice = unitPrice;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
}
