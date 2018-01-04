package com.kosign.wecafe.entities;

import java.beans.Transient;
import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_detail")
@AssociationOverrides({
	@AssociationOverride(name = "pk.order", 
		joinColumns = @JoinColumn(name = "order_id")),
	@AssociationOverride(name = "pk.product", 
		joinColumns = @JoinColumn(name = "pro_id")) })
public class OrderDetail implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderDetailsPK pk = new OrderDetailsPK();
	
	/*@Id
	@GeneratedValue
	@Column(name="id")
	private Long OrderDetailId;*/
	 
/*	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pro_id")
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="order_id")
	private Order order;*/

	@Column(name="pro_qty")
	private long proQty;
	
	@Column(name="pro_unit_price")
	private BigDecimal proUnitPrice;
	
	@Column(name="pro_comment")
	private String proComment;
	
 

	public long getProQty() {
		return proQty;
	}

	public void setProQty(long proQty) {
		this.proQty = proQty;
	}

	public BigDecimal getProUnitPrice() {
		return proUnitPrice;
	}

	public void setProUnitPrice(BigDecimal proUnitPrice) {
		this.proUnitPrice = proUnitPrice;
	}

	public String getProComment() {
		return proComment;
	}

	public void setProComment(String proComment) {
		this.proComment = proComment;
	}
 
	@Transient
	public Product getProduct() {
		return pk.getProduct();
	}

	public void setProduct(Product product) {
		this.pk.setProduct(product);
	}

	@Transient
	public Order getOrder() {
		return pk.getOrder();
	}

	public void setOrder(Order order) {
		this.pk.setOrder(order);
	}
	
}
