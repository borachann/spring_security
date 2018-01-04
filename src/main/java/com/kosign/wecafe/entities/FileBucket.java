package com.kosign.wecafe.entities;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket implements Serializable{

	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
