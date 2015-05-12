package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class TempTableInfoDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(TempTableInfoDAO.class);
	//property constants
	public static final String UNIT = "unit";
	public static final String PD_PAGE = "pdPage";
	public static final String TIME = "time";
	public static final String MONTH_RANGE = "monthRange";
	public static final String CURRENCY_TYPE = "currencyType";
	public static final String CONTENT = "content";


//	@Autowired
	public TempTableInfoDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}