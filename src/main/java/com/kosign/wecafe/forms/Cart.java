package com.kosign.wecafe.forms;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long productId;
	
	private String productName;
	
	private BigDecimal price;
	
	private Long quantity;
	
	private BigDecimal totalAmount;
	
	private String comment;

	private String time;
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Cart [productId=" + productId + ", productName=" + productName + ", price=" + price + ", quantity="
				+ quantity + ", totalAmount=" + totalAmount + ", comment=" + comment + ", time=" + time + "]";
	}
	
}
