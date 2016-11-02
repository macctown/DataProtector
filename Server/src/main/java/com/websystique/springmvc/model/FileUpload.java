package com.websystique.springmvc.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	
	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getHash(){
		 
	    String hash_value;
	    int read;
	    byte[] buffer = new byte[8192];
	    InputStream is = null;
	    
		try {
			is = this.file.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        while ((read = is.read(buffer)) > 0) {
	            digest.update(buffer, 0, read);
	        }
	        byte[] hash = digest.digest();
	        BigInteger bigInt = new BigInteger(1, hash);
	        hash_value = bigInt.toString(16);
	        while ( hash_value.length() < 32 ) {
	        	hash_value = "0"+hash_value;
	        }
	    } 
	    catch (Exception e) {
	        e.printStackTrace(System.err);
	        return null;
	    }

	    return hash_value;
	    
	}
}
