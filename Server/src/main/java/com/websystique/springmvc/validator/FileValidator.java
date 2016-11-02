package com.websystique.springmvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.websystique.springmvc.model.FileUpload;

public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		FileUpload file = (FileUpload) target;
        
        if(file.getFile()!=null){
            if (file.getFile().getSize() == 0) {
                errors.rejectValue("file", "missing.file");
            }
        }
        else{
        	errors.rejectValue("file", "null.file");
        }
	}

}
