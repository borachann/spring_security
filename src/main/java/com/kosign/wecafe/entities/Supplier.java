package com.kosign.wecafe.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.taglibs.standard.tag.common.core.ImportSupport;

@Entity
@Table(name = "supplier")
public class Supplier implements java.io.Serializable {
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "supplier_sup_id_seq", name = "sup_id")
	@GeneratedValue(generator = "sup_id", strategy = GenerationType.SEQUENCE)
	@Column(name = "sup_id")
	private Long supId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="supplier")
	private List<ImportDetail> importDetail = new ArrayList<>();

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@Column(name = "sup_address")
	private String supplierAddress;

	@Column(name = "sup_email")
	private String supplierEmail;

	@Column(name = "sup_name")
	private String supplierName;

	@Column(name = "sup_phone_number")
	private String supplierNumber;

	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public List<ImportDetail> getImportDetail() {
		return importDetail;
	}

	public void setImportDetail(List<ImportDetail> importDetail) {
		this.importDetail = importDetail;
	}
}
