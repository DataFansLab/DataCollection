package com.insit.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.model.AffairsAssign;
import com.insit.model.AnalysisDetailError;
import com.insit.model.AnalysisDetailErrorId;
import com.insit.unused.AffairsAssignDAO;
import com.insit.util.BeanContainer;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		IBaseHibernateDAO dao = (IBaseHibernateDAO) BeanContainer.getBean("baseHibernateDAO");
		
//		List results = dao.callProcedure("bulletins", new String[]{"%","%","%","2","2014-03-11","2014-06-12"});

		//List results = dao.selectMulti(new String[]{"BulletinTable"}, new String[]{"bulletinId", "bulletinName"}, new String[]{"bulletinType='半年报'"}, 0, 0);
//		System.out.println(results.toString());

	}

}
