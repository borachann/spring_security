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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="request_stock")
public class RequestStock implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="req_stock_id", name="req_id")
	@GeneratedValue(generator="req_id", strategy=GenerationType.SEQUENCE)
	@Column(name="req_id")
	private long reqId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk1.requestStock", cascade=CascadeType.ALL)
	private Set<RequestStockDetail> requestStockDetail = new HashSet<RequestStockDetail>();

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="use_id")
	private User userRequest;	
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="app_id")
	private User userApprove;	
	
	
	@Column(name="req_date")
	private Date reqDate ;
	
	
	@Column(name="app_date")
	private Date appDate ;
	
	@Column(name="status")
	private Boolean status;

	public long getReqId() {
		return reqId;
	}

	public void setReqId(long reqId) {
		this.reqId = reqId;
	}

	public Set<RequestStockDetail> getRequestStockDetail() {
		return requestStockDetail;
	}

	public void setRequestStockDetail(Set<RequestStockDetail> requestStockDetail) {
		this.requestStockDetail = requestStockDetail;
	}

	public User getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(User userRequest) {
		this.userRequest = userRequest;
	}

	public User getUserApprove() {
		return userApprove;
	}

	public void setUserApprove(User userApprove) {
		this.userApprove = userApprove;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RequestStock [reqId=" + reqId + ", requestStockDetail=" + requestStockDetail + ", userRequest="
				+ userRequest + ", userApprove=" + userApprove + ", reqDate=" + reqDate + ", appDate=" + appDate
				+ ", status=" + status + "]";
	}
	
	


}
