package com.kosign.wecafe.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "import_detail")
@AssociationOverrides({
	@AssociationOverride(name = "pk1.importProduct", 
		joinColumns = @JoinColumn(name = "imp_id")),
	@AssociationOverride(name = "pk1.product", 
		joinColumns = @JoinColumn(name = "pro_id")) })
public class ImportDetail implements Serializable{

/*	@Id
	// value primary key
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "importDetail_imp_detail_id_seq", name = "imp_detail_id")
	@GeneratedValue(generator = "imp_detail_id", strategy = GenerationType.SEQUENCE)*/
	
	
	@EmbeddedId
	private importDetailPK pk1 = new importDetailPK();
	
	@ManyToOne
	@JoinColumn(name="sup_id")
	private Supplier supplier;
	


	@Column(name = "pro_qty")
	private long proQty;

	@Column(name = "unit_price")
	private BigDecimal unitPrice;

	@Column(name = "pro_status")
	private Boolean proStatus;

//	@Column(name = "sup_id")
//	private long supId;

	@Transient
	private long supplierid;
	
	public long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(long supplierid) {
		this.supplierid = supplierid;
	}
	
	/*
	public long getImpDetailId() {
		return impDetailId;
	}

	public void setImpDetailId(long impDetailId) {
		this.impDetailId = impDetailId;
	}

	public long getProId() {
		return ProId;
	}

	public void setProId(long proId) {
		ProId = proId;
	}
	*/
	public long getProQty() {
		return proQty;
	}

	public void setProQty(long proQty) {
		this.proQty = proQty;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getProStatus() {
		return proStatus;
	}

	public void setProStatus(Boolean proStatus) {
		this.proStatus = proStatus;
	}

//	public long getSupId() {
//		return supId;
//	}
//
//	public void setSupId(long supId) {
//		this.supId = supId;
//	}
//	
	@Transient
	public Product getProduct(){
		return pk1.getProduct();
	}
	
	public void setProduct(Product product) {
		this.pk1.setProduct(product);
	}
	
	@Transient
	public ImportProduct getImportProduct(){
		return pk1.getImportProduct();
	}

	public void setImportProduct(ImportProduct importProduct){
		this.pk1.setImportProduct(importProduct);
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
