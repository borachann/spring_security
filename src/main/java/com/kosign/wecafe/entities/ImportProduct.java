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
@Table(name="import")
public class ImportProduct implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="import_imp_id", name="imp_id")
	@GeneratedValue(generator="imp_id", strategy=GenerationType.SEQUENCE)
	@Column(name="imp_id")
	private long impId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk1.importProduct", cascade=CascadeType.ALL)
	private Set<ImportDetail> importDetail = new HashSet<ImportDetail>();
	
	
	@Column(name="imp_date")
	private Date impDate;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	
	@Formula("(SELECT  coalesce(SUM(o.pro_qty*o.unit_price),0) FROM import_detail o WHERE o.imp_id=imp_id)")
	@Column(name = "total_Amount")
	private long totalAmount;
	
	
	public Set<ImportDetail> getImportDetail() {
		return importDetail;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setImportDetail(Set<ImportDetail> importDetail) {
		this.importDetail = importDetail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getImpId() {
		return impId;
	}

	public void setImpId(long impId) {
		this.impId = impId;
	}

	public Date getImpDate() {
		return impDate;
	}

	public void setImpDate(Date impDate) {
		this.impDate = impDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString(){
		return ("impDate = " + impDate);
	} 

}
