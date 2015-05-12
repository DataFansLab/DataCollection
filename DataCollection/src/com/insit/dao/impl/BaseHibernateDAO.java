package com.insit.dao.impl;

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.insit.dao.IBaseHibernateDAO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	private static final Logger log = LoggerFactory.getLogger(BaseHibernateDAO.class);
	private static final String packageName = "com.insit.model";
	private SessionFactory sessionFactory;
	
	@Autowired
	public BaseHibernateDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Object transientInstance) {
		log.debug("saving " + transientInstance.getClass().getName() + " instance");
		try {
			currentSession().save(transientInstance);
			log.debug("save successful");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Object persistentInstance) {
		log.debug("deleting " + persistentInstance.getClass().getName() + " instance");
		System.out.println("deleting");
		try {
			Session session = sessionFactory.openSession();
			session.delete(persistentInstance);
			log.debug("delete successful");
			System.out.println("================delete success");
		} catch(Exception e) {
			e.printStackTrace();
			log.error("delete failed", e);
			throw e;
		}
	}

	@Override
	public Object findById(Class entity, Serializable id) {
		log.debug("getting " + entity.getName() + " instance with id: " + id);
		try {
			Object instance = currentSession().get(entity, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
            throw re;
		}
	}

	@Override
	public List findByExample(Object instance) {
		Class c = instance.getClass();
		log.debug("finding " + c.getName() + " instance by example");
		try {
			List results = currentSession().createCriteria(c).add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
            throw re;
		}
	}

	@Override
	public List findByProperty(String entityName, String propertyName, Object value) {
		log.debug("finding " + entityName + " instance with property: " + propertyName
	            + ", value: " + value);
		System.out.println(value);
		try {
			String queryString = "from " + entityName + " as model where model." 
						+ propertyName + "= ?";
			Query queryObject = currentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	@Override
	public List findAll(String entityName) {
		log.debug("finding all " + entityName + " instances");
		try {
			String queryString = "from " + entityName;
			Query queryObject = currentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Object merge(Object detachedInstance) {
		Class c = detachedInstance.getClass();
		log.debug("merging " + c.getName() + " instance");
		try {
			Object result = currentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
            throw re;
		}
	}

	@Override
	public void attachDirty(Object instance) {
		Class c = instance.getClass();
		log.debug("attaching dirty " + c.getName() + " instance");
		try {
			currentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
            throw re;
		}
		
	}

	@Override
	public void attachClean(Object instance) {
		Class c = instance.getClass();
		log.debug("attaching clean " + c.getName() + " instance");
		try {
			currentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
            throw re;
		}
		
	}

	@Override
	public Map selectMulti(String[] entityList, String[] propertyNameList,
			String[] whereConditions, int start, int limit) {
		

		String sql = "select new map(";
		String countSql = "select count(*) ";
		String commonSql = "from ";
		
		// add properties
		for (String property : propertyNameList) {
			sql += (property + ",");
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ") ";
		
		// add entities
		for (String entity : entityList) {
			commonSql += (entity + ",");
		}
		commonSql = commonSql.substring(0, commonSql.length() - 1);
		commonSql += " where ";
		
		// add where conditions
		for (Object condition : whereConditions) {
			commonSql += (condition + " and ");
		}
		commonSql = commonSql.substring(0, commonSql.length() - 5);
		
		sql += commonSql;
		countSql += commonSql;
		
		Session session = currentSession();
		List list = null;
		List count = null;
		try {
			Query query = session.createQuery(sql);
			Query countQuery = session.createQuery(countSql);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			list = query.list();
			count = countQuery.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		HashMap<String, List> r = new HashMap<>();
		r.put("list", list);
		r.put("count", count);
		return r;
	}
	
	public int count(Class entity,Map map){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(entity);		
			for(Object o:map.keySet()){
				if(map.get(o).equals("")==false&&map.get(o).equals("全选")==false)
					dc.add(Restrictions.eq(o.toString(), map.get(o)));	
			}
			Criteria c = dc.getExecutableCriteria(session);
			c.setProjection(Projections.rowCount());			
			return Integer.parseInt(c.uniqueResult().toString()); 
		}finally{
			session.close();
		}	
	}
	
	public List select(Class entity, Map whereMap,int first,int limit){
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		try{
			tx = session.beginTransaction();		
			DetachedCriteria dc = DetachedCriteria.forClass(entity);		
			for(Object o:whereMap.keySet()){
				if(whereMap.get(o).equals("")==false&&whereMap.get(o).equals("全选")==false)
					dc.add(Restrictions.eq(o.toString(), whereMap.get(o)));	
			}			
			Criteria c = dc.getExecutableCriteria(session);
			c.setMaxResults(limit);
			c.setFirstResult(first);
			tx.commit();
			List<Object> list = c.list();			
			return list;
		}finally{
			session.close();
		}
	}
	
	@Override
	public JSONObject selectMulti(int queryType, List params, int start, int limit) {
		String queryStr = QueryObject.getQueryStr(queryType);
		Map fieldsMapping = QueryObject.getFieldsMapping(queryType);
		Session session = currentSession();
		List list = null;
		List count = null;
		try {
			for (int i = 0; i < params.size(); i++) {
				queryStr = queryStr.replaceFirst("\\?", (String) params.get(i));
			}
			Query query = session.createQuery(queryStr);
			Query countQuery = session.createQuery("select count(*)" +
					queryStr.substring(queryStr.indexOf("from") - 1));
			
			query.setFirstResult(start);
			query.setMaxResults(limit);
			list = query.list();
			count = countQuery.list();
			
			JSONArray listJson = new JSONArray();
			
			for (Map record : (List<Map>) list) {
				JSONObject item = new JSONObject();
				Iterator it = fieldsMapping.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String value = null;
					if (((String) fieldsMapping.get(key)).matches("\\d+")) {
						value = record.get(fieldsMapping.get(key)) == null ? "--" : record.get(fieldsMapping.get(key)).toString();
					}
					else value = ((String) fieldsMapping.get(key));
					item.put(key, value);
				}
				listJson.add(item);
			}
			
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("rows", listJson);
			jsonResult.put("totalCount", count.get(0));
			
			return jsonResult;
		} catch (RuntimeException re) {
			re.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean executeUpdate(String hql){
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
	        Query query = session.createQuery(hql);  
	        query.executeUpdate();  
	        session.getTransaction().commit();
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean executeInsert(String sql){
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
	        Query query = session.createSQLQuery(sql);  
	        query.executeUpdate();
	        session.flush();
	        session.getTransaction().commit();
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean deleteByPrimarykey(String hql,String id){
		try{
			Session session = sessionFactory.openSession();
			session.beginTransaction();
	        Query query = session.createQuery(hql);
	        query.setString(0, id);
	        query.executeUpdate();
	        session.flush();
	        session.getTransaction().commit();
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List fuzzySearch(String hql){
		try{
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			List result = session.createQuery(hql).list();		
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public List callProcedure(String pName, List params) {
		try {
			Session session = currentSession();
			String temp = "";
//			for (int i = 0; i < params.size(); i++)
//				temp += "?,";
//			temp = temp.substring(0, temp.length() - 1);
//			SQLQuery sqlQuery = session.createSQLQuery("{call " + pName + "(" + temp + ")}");
//			for (int i = 0; i < params.size(); i++)
//				sqlQuery.setString(i, params.get(i).toString());
//			
			if (params != null) {
				for (Object item : params)
					temp += ("'" + item.toString() + "',");
				temp = temp.substring(0, temp.length() - 1);
			}
			
			SQLQuery sqlQuery = session.createSQLQuery("{call " + pName + "(" + temp + ")}");
			return sqlQuery.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public List executeSQL(String sql) {
		try {
			Session session = currentSession();
			Query query = session.createQuery(sql);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}