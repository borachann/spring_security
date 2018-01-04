package com.kosign.wecafe.entities;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FileValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return FileBucket.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		FileBucket file = (FileBucket) object;
		
		if(file.getFile()!=null){
			if(file.getFile().getSize() == 0){
				errors.rejectValue("file", "missing.file");
			}
		}
	}
}
