package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class AnalysisErrorDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(AnalysisErrorDAO.class);
	//property constants
	public static final String CASHFLOW_STATUS = "cashflowStatus";
	public static final String BALANCESHEET_STATUS = "balancesheetStatus";
	public static final String INCOMESTATE_STATUS = "incomestateStatus";
	public static final String OTHERS_STATUS = "othersStatus";
	public static final String CONFLICT = "conflict";


//	@Autowired
	public AnalysisErrorDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}