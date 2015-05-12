package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class StockTableDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(StockTableDAO.class);
	//property constants
	public static final String COMPANY_ID = "companyId";
	public static final String STOCK_NAME = "stockName";
	public static final String STOCK_TYPE = "stockType";
	public static final String INDUSTRY = "industry";
	public static final String EXCHANGE = "exchange";
	public static final String STATUS = "status";
	public static final String BLOCK = "block";
	public static final String LIST_DATE = "listDate";
	public static final String CHANGE_DATE = "changeDate";


//	@Autowired
	public StockTableDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}