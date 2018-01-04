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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kosign.wecafe.enums.Status;

@Entity
@Table(name="USERS")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="USERNAME", unique=true, nullable=false)
	private String username;
	
	@Column(name="PASSWORD", nullable=false)
/*	@JsonIgnore*/
	private String password;
	
	@Column(name="FIRSTNAME", nullable=false)
	private String firstName;
	
	@Column(name="LASTNAME", nullable=false)
	private String lastName;
	
	@Column(name="EMAIL", nullable=false, unique=true)
	private String email;
	
	@Column(name="GNEDER")
	private String gender;
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name="STATUS", nullable=false)
	private Status status = Status.ACTIVE;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "USER_USER_ROLE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_ROLE_ID") })
	private Set<UserRole> userRoles = new HashSet<UserRole>();
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate = new Date();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CREATED_BY")
	private User createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATED_DATE")
	private Date lastUpdatedDate = new Date();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="LAST_UPDATED_BY")
	private User lastUpdatedBy;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
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

	
}