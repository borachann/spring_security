package com.kosign.wecafe.forms;

import org.springframework.web.multipart.MultipartFile;

public class CategoryForm {

	private Long categoryId;
	
	private String categoryName;
	
	private String image;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
