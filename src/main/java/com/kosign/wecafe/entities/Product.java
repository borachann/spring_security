package com.kosign.wecafe.entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="product")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="product_pro_id_seq", name="product_id")
	@GeneratedValue(generator="product_id", strategy=GenerationType.SEQUENCE)
	@Column(name="pro_id")
	private Long productId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.product", cascade=CascadeType.ALL)
	private Set<OrderDetail> orderDetail = new HashSet<OrderDetail>();
	
	@OneToMany(fetch = FetchType.LAZY,  mappedBy = "pk1.product", cascade=CascadeType.ALL)
	private Set<ImportDetail> importDetail = new HashSet<ImportDetail>();

	@Column(name="pro_name")
	private String productName;
	
	
	@Column(name="qty",nullable=true)
	private Long quantity;
	
	@Column(name="unit_price",nullable=true)
	private BigDecimal unitPrice;
	
	@Column(name="cost_price",nullable=true)
	private BigDecimal costPrice;
	
	@Column(name="sale_price",nullable=true)
	private BigDecimal salePrice;
	
	@ManyToOne
	@JoinColumn(name="cat_id")
	private Category category ; 

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="unit_id")
	private Unit unit;
	
	@Column(name="img_url")
	private String image;
	
	@Column(name="stock_type")
	private String stockType;
	
	@Column(name="product_type")
	private String productType;
	
	@Column(name="can_sell_holiday")
	private String can_sell_holiday;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CREATED_BY")
	private User createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="LAST_UPDATED_BY")
	private User lastUpdatedBy;
	
	@Column(name="status")
	private Boolean status;
		
	public Long getProductId() {
		return productId;
	}

	public Product() {
		
	}

	public Product(Long productId, Set<OrderDetail> orderDetail, String productName, Long quantity,
			BigDecimal unitPrice, BigDecimal costPrice, BigDecimal salePrice, Category category, Unit unit, String image,
			byte[] images, Date createdDate, User createdBy, Date lastUpdatedDate, User lastUpdatedBy, Boolean status) {
		super();
		this.productId = productId;
		this.orderDetail = orderDetail;
		this.productName = productName;
		this.quantity = quantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.costPrice = costPrice;
		this.salePrice = salePrice;
		this.category = category;
		this.image = image;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.status = status;
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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	 
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(Set<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Set<ImportDetail> getImportDetail() {
		return importDetail;
	}

	public void setImportDetail(Set<ImportDetail> importDetail) {
		this.importDetail = importDetail;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCan_sell_holiday() {
		return can_sell_holiday;
	}

	public void setCan_sell_holiday(String can_sell_holiday) {
		this.can_sell_holiday = can_sell_holiday;
	}
	@Override
	public String toString() {
		return "Pro_ID = " + this.productId + " User_ID = " + this.unit.getUnitId() + " Cat_ID = " + this.category.getCatId()
		+ "Pro_name = " + this.productName
		+ "Pro_type = " + this.productType	;
		
	}
}
