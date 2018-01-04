package com.kosign.wecafe.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kosign.wecafe.enums.UserRoleType;

@Entity
@Table(name="USER_ROLES")
public class UserRole implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROLE", unique=true, nullable=false)
	private UserRoleType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public UserRoleType getType() {
		return type;
	}

	public void setType(UserRoleType type) {
		this.type = type;
	}
	
	 @Override
	public String toString()
	{
		return ("getID = " + id + " getType = " + type);
	} 
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
