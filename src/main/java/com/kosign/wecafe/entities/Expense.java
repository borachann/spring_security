package com.kosign.wecafe.entities;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="tbl_expense")
public class Expense implements Serializable{
	public long getExpId() {
		return expId;
	}

	public void setExpId(long expId) {
		this.expId = expId;
	}


	/*public long getExp_user_id() {
		return exp_user_id;
	}

	public void setExp_user_id(long exp_user_id) {
		this.exp_user_id = exp_user_id;
	}*/

	public Date getExp_date() {
		return exp_date;
	}

	public void setExp_date(Date exp_date) {
		this.exp_date = exp_date;
	}

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="exp_id", name="expense_id")
	@GeneratedValue(generator="expense_id", strategy=GenerationType.SEQUENCE)
	@Column(name="expense_id")
	private long expId;
   
	@Column(name="expense_date")
	private Date exp_date;
	
	@Column(name="description")
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	 public Set<ExpenseDetail> getExpenseDetails() {
		return expenseDetails;
	}

	public void setExpenseDetails(Set<ExpenseDetail> expenseDetails) {
		this.expenseDetails = expenseDetails;
	} 

	/*@Column(name="expense_user_id")
	private long exp_user_id;*/
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="exp_user_id")
	private User exp_user;
	
	public User getExp_user() {
		return exp_user;
	}

	public void setExp_user(User exp_user) {
		this.exp_user = exp_user;
	}

/*	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	@OneToMany(mappedBy="expense", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<ExpenseDetail> expenseDetails = new HashSet<ExpenseDetail>();
	
	@Override
	public String toString() {
		return "ID = " + this.expId + " DESCRIPTION = " + this.description + " Date = " + this.exp_date;
	}
	
/*	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;*/
	 
	@Formula("(SELECT  coalesce(SUM(o.expense_qty*o.expense_unitprice),0) FROM tbl_expense_detail o WHERE o.expense_id=expense_id)")
	@Column(name = "total_Amount")
	private long totalAmount;
}

