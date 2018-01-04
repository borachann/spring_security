package com.kosign.wecafe.entities;

import java.util.Date;
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

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="category")
public class Category implements java.io.Serializable{

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=2, sequenceName="category_cat_id_seq", name="cat_id")
	@GeneratedValue(generator="cat_id", strategy=GenerationType.SEQUENCE)
	@Column(name="cat_id")
	private long catId;
	
	@Column(name="cat_name")
	private String catName;
	
	@Column(name="img_url")
	private String img;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CREATED_BY")
	private User createdBy;
	
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="LAST_UPDATED_BY")
	private User lastUpdatedBy;
	
	@Column(name="status")
	private Boolean status;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy="category")
	private Set<Product> products ;//= new HashSet<Product>();
	
	public long getCatId() {
		return catId;
	}

	public void setCatId(long catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public Set<Product> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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
	@Override
	public String toString(){
		return ( "catname = " + catName + " catId = " + catId + " create date = " + createdDate);
	}

}
