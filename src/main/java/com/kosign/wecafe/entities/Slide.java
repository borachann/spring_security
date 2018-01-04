package com.kosign.wecafe.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Slides")
public class Slide implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String image;
	
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	
}
