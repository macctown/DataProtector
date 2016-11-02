package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.UserCert;

public interface UserCertDao {

	List<UserCert> findAll();
	
	List<UserCert> findByUserId(int user_id);
	
	UserCert findByCertId(int cert_id);
	
	void save(UserCert cert);
	
	void deleteByCertId(int cert_id);
	
	List<UserCert> findByKwd(int user_id, String kwd);
}
