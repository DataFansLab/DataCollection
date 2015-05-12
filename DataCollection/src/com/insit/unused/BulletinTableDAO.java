package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class BulletinTableDAO extends BaseHibernateDAO  {
	private static final Logger log = LoggerFactory.getLogger(BulletinTableDAO.class);
	//property constants
	public static final String URL = "url";
	public static final String STOCK_ID = "stockId";
	public static final String BULLETIN_NAME = "bulletinName";
	public static final String BULLETIN_TYPE = "bulletinType";
	public static final String ACCOUNT_DATE = "accountDate";
	public static final String BULLETIN_YEAR = "bulletinYear";
	public static final String RELEASE_DATE = "releaseDate";
	public static final String DOWNLOAD_DATE = "downloadDate";
	public static final String HASHCODE = "hashcode";
	public static final String PATH = "path";
	public static final String DOWNLOAD_STATUS = "downloadStatus";
	public static final String ANALYSIS_TIME = "analysisTime";
	public static final String ANALYSIS_STATUS = "analysisStatus";


//	@Autowired
	public BulletinTableDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

}