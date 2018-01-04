package com.kosign.wecafe.entities;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "request_stock_detail")
@AssociationOverrides({
	@AssociationOverride(name = "pk1.requestStock", 
		joinColumns = @JoinColumn(name = "req_id")),
	@AssociationOverride(name = "pk1.product", 
		joinColumns = @JoinColumn(name = "pro_id")) })
public class RequestStockDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RequestStockDetailPK pk1 = new RequestStockDetailPK();
	
	@Column(name = "pro_qty")
	private long proQty; 
	
	@Column(name = "remain_qty")
	private long remainQty; 
	

	
	public long getProQty() {
		return proQty;
	}

	public void setProQty(long proQty) {
		this.proQty = proQty;
	}

	public RequestStockDetailPK getPk1() {
		return pk1;
	}

	public void setPk1(RequestStockDetailPK pk1) {
		this.pk1 = pk1;
	}

	public long getRemainQty() {
		return remainQty;
	}

	public void setRemainQty(long remainQty) {
		this.remainQty = remainQty;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	public Product getProduct(){
		return pk1.getProduct();
	}
	
	public void setProduct(Product product) {
		this.pk1.setProduct(product);
	}
	
	@Transient
	public RequestStock getRequestStock(){
		return pk1.getRequestStock();
	}

	public void setRequestStock(RequestStock requestStock){
		this.pk1.setRequestStock(requestStock);
	}

}
