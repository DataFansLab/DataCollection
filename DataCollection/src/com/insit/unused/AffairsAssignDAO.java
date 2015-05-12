package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class AffairsAssignDAO extends BaseHibernateDAO  {

	private static final Logger log = LoggerFactory.getLogger(AffairsAssignDAO.class);
	//property constants
	public static final String EMAIL_ADDR = "emailAddr";
	public static final String COMPANY_ID = "companyId";
	public static final String DOC_ID = "docId";

//	@Autowired
	public AffairsAssignDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public AffairsAssignDAO findById(Integer id) {
		return (AffairsAssignDAO) super.findById(getClass(), id);
	}
}