package com.insit.unused;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insit.dao.impl.BaseHibernateDAO;


//@Repository
public class AnalysisDetailErrorDAO extends BaseHibernateDAO  {

	private static final Logger log = LoggerFactory.getLogger(AnalysisDetailErrorDAO.class);
	//property constants
	public static final String PD_PAGE = "pdPage";
	public static final String LOCATE_INFO = "locateInfo";
	public static final String FIELDCONFLICT = "fieldconflict";
	public static final String FIELDNAME_QC = "fieldnameQc";

//	@Autowired
	public AnalysisDetailErrorDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
}