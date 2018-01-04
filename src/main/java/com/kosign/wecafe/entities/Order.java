package com.kosign.wecafe.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="wecafe_order")
public class Order implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="order_order_id_seq", name="order_id")
	@GeneratedValue(generator="order_id", strategy=GenerationType.SEQUENCE)
	@Column(name="order_id")
	private long orderId;
		
	/*@ManyToMany(mappedBy="order")
	private Set<OrderDetail> orderDetail = new HashSet<OrderDetail>();*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.order", cascade=CascadeType.ALL)
	private Set<OrderDetail> orderDetail = new HashSet<OrderDetail>();
	
	@DateTimeFormat(pattern = "dd/MM/yy")
	@Column(name="order_Date")
	private Date orderDate;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cus_id")
	private User customer;
	
	@Formula("(SELECT SUM(o.pro_qty*o.pro_unit_price) FROM order_detail o WHERE o.order_id=order_id)")
	private BigDecimal orderAmount;
	
	@Column(name="status")
	private Integer status;
	
	@Formula("(SELECT SUM(o.pro_qty) FROM order_detail o WHERE o.order_id=order_id)")
	private Integer orderQuantity;
	

	@OneToOne(mappedBy="order", fetch=FetchType.LAZY)
	private Sale sale;
	
	public Sale getSale() {
		return sale;
	}
	
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	public Set<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(Set<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
}
