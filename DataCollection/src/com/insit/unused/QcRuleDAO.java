package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class QcRuleDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(QcRuleDAO.class);
	//property constants
	public static final String BUSINESS_TYPE = "businessType";
	public static final String QC_TYPE = "qcType";
	public static final String QC_DESCRIPTION = "qcDescription";
	public static final String SYS_DESCRIPTION = "sysDescription";


//	@Autowired
	public QcRuleDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}