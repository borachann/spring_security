package com.kosign.wecafe.enums;

public enum UserRoleType {

	CUSTOMER,
	SELLER,
	ADMIN;
	
	String userRoleType;

	public String getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
	}
	
	/*@Override
	public String toString(){
		return ("userRoleType = " + userRoleType);
	}*/
	
}
