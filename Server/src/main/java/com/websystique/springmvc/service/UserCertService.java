package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.UserCert;

public interface UserCertService {
	
	List<UserCert> findByUserId(int user_id);
	
	UserCert findByCertId(int cert_id);
	
	void saveUser(UserCert userCert);
	
	void deleteByCertId(int cert_id);

	List<UserCert> findAllUserCerts(); 
	
	boolean authUserCert(int cert_id, String hash_value);
	
	List<UserCert> findByKwd(int user_id, String kwd);
}
