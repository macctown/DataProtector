package com.websystique.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.UserCert;

@Repository("userCertDao")
public class UserCertDaoImpl extends AbstractDao<Integer, UserCert> implements UserCertDao {

	static final Logger logger = LoggerFactory.getLogger(UserCertDaoImpl.class);
	
	@Override
	public List<UserCert> findAll() {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("upload_time"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<UserCert> userCerts = (List<UserCert>) criteria.list();
		
		return userCerts;
	}

	@Override
	public List<UserCert> findByUserId(int user_id) {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("upload_time"));
		criteria.add(Restrictions.eq("user_id", user_id));
		List<UserCert> userCerts = (List<UserCert>) criteria.list();
		
		return userCerts;
	}

	@Override
	public UserCert findByCertId(int cert_id) {
		// TODO Auto-generated method stub
		logger.info("certID : {}", cert_id);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", cert_id));
		UserCert userCert = (UserCert)crit.uniqueResult();
		if(userCert!=null){
			Hibernate.initialize(userCert);
		}
		return userCert;
	}

	@Override
	public void save(UserCert cert) {
		// TODO Auto-generated method stub
		cert.createdAt();
		persist(cert);
	}

	@Override
	public void deleteByCertId(int cert_id) {
		// TODO Auto-generated method stub
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", cert_id));
		UserCert userCert = (UserCert)crit.uniqueResult();
		System.out.println(userCert.getFile_name() + "gonna be deleted");
		delete(userCert);
	}

	@Override
	public List<UserCert> findByKwd(int user_id, String kwd) {
		// TODO Auto-generated method stub
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("upload_time"));
		criteria.add(Restrictions.eq("user_id", user_id));
		criteria.add(Restrictions.like("file_name", "%"+kwd+"%"));
		List<UserCert> userCerts = (List<UserCert>) criteria.list();
		
		return userCerts;
	}

}
