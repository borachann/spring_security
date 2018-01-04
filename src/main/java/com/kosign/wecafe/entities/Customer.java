package com.kosign.wecafe.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="customer")

public class Customer implements java.io.Serializable {

	@Id
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="customer_cus_id_seq", name="customer_id")
	@GeneratedValue(generator="customer_id", strategy=GenerationType.SEQUENCE)
	@Column(name="cus_id")
	private Long customerId;
	
	@Column(name="cus_name")
	private String customerName;
	
	@Column(name="phone_number")
	private String phoneNumber;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
