package com.kosign.wecafe.entities;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_expense_detail")
public class ExpenseDetail {
	
	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="id", name="id")
	@GeneratedValue(generator="id", strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="expense_id")
	private Expense expense; 
/*	@Column(name="expense_id")
	private long exp_id;*/
	
	@Column(name="expense_description")
	private String exp_description;
	
	@Column(name="expense_qty")
	private long exp_qty;
	
	@Column(name="expense_unitprice")
	private long exp_unitprice;
	
	@Column(name="customer")
	private String customer;
	
	@Column(name="expense_remarks")
	private String remark;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

/*	public long getExp_id() {
		return exp_id;
	}

	public void setExp_id(long exp_id) {
		this.exp_id = exp_id;
	}*/

	/*@Column(name="expense_date")
	private Date exp_date;
*/
/*	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}*/

	public String getExp_description() {
		return exp_description;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public void setExp_description(String exp_description) {
		this.exp_description = exp_description;
	}

	public long getExp_qty() {
		return exp_qty;
	}

	public void setExp_qty(long exp_qty) {
		this.exp_qty = exp_qty;
	}

	public long getExp_unitprice() {
		return exp_unitprice;
	}

	public void setExp_unitprice(long exp_unitprice) {
		this.exp_unitprice = exp_unitprice;
	}

/*	public Date getExp_date() {
		return exp_date;
	}

	public void setExp_date(Date exp_date) {
		this.exp_date = exp_date;
	}*/
	 @Override
	public String toString() {
		 return  " DESCRIPTION = " + this.exp_description + " qty = " + this.exp_qty + " uniprice = " + this.exp_unitprice;
	}
	
}
