package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class StorageMapDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(StorageMapDAO.class);
	//property constants
	public static final String NODE = "node";
	public static final String FORM = "form";
	public static final String DB = "db";


//	@Autowired
	public StorageMapDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}