package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserCertDao;
import com.websystique.springmvc.model.UserCert;

@Service("userCertService")
@Transactional
public class UserCertServiceImpl implements UserCertService {

	@Autowired
	private UserCertDao dao;
	
	@Override
	public List<UserCert> findByUserId(int user_id) {
		// TODO Auto-generated method stub
		return dao.findByUserId(user_id);
	}

	@Override
	public UserCert findByCertId(int cert_id) {
		// TODO Auto-generated method stub

		return dao.findByCertId(cert_id);
	}

	@Override
	public void saveUser(UserCert userCert) {
		// TODO Auto-generated method stub
		dao.save(userCert);
	}

	@Override
	public void deleteByCertId(int cert_id) {
		// TODO Auto-generated method stub

		dao.deleteByCertId(cert_id);
	}

	@Override
	public List<UserCert> findAllUserCerts() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public boolean authUserCert(int cert_id, String hash_value) {
		// TODO Auto-generated method stub
		UserCert cert = dao.findByCertId(cert_id);
		
		return cert.getHash_value().equals(hash_value);
	}

	@Override
	public List<UserCert> findByKwd(int user_id, String kwd) {
		// TODO Auto-generated method stub
		return dao.findByKwd(user_id, kwd);
	}

}
