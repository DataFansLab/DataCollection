package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class DownloadErrorDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(DownloadErrorDAO.class);
	//property constants
	public static final String STOCK_ID = "stockId";
	public static final String PROMPT_TIME = "promptTime";
	public static final String OPERATE_TIME = "operateTime";
	public static final String ERROR_TYPE = "errorType";
	public static final String DETAIL_DESCRIBE = "detailDescribe";
	public static final String OPERATE_STATUS = "operateStatus";


//	@Autowired
	public DownloadErrorDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}