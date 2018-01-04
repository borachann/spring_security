package com.kosign.wecafe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sale")
public class Sale implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="sale_sale_id_seq", name="sale_id")
	@GeneratedValue(generator="sale_id", strategy=GenerationType.SEQUENCE)
	@Column(name="sale_id")
	private long saleId;
	
	@Column(name="total_amount")
	private BigDecimal totalAmount= new BigDecimal("0");
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="ord_id")
	private Order order;
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="money_in")
	private BigDecimal moneyIn;
	
	@Column(name="sale_datetime")
	private Date saleDatetime;

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getMoneyIn() {
		return moneyIn;
	}

	public void setMoneyIn(BigDecimal moneyIn) {
		this.moneyIn = moneyIn;
	}

	public Date getSaleDatetime() {
		return saleDatetime;
	}

	public void setSaleDatetime(Date saleDatetime) {
		this.saleDatetime = saleDatetime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
