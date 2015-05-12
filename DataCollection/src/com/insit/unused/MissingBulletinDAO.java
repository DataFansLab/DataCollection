package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class MissingBulletinDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(MissingBulletinDAO.class);
	//property constants


//	@Autowired
	public MissingBulletinDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}