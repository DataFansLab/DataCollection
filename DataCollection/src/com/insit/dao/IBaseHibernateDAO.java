package com.insit.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;



public interface IBaseHibernateDAO {
	
	// common methods
	public void save(Object transientInstance);
	public void delete(Object persistentInstance);
	public Object findById(Class entity, Serializable id);
	public List findByExample(Object instance);
	public List findByProperty(String entityName, String propertyName, Object value);
	public List findAll(String entityName);
	public Object merge(Object detachedInstance);
	public void attachDirty(Object instance);
	public void attachClean(Object instance);
	public boolean executeUpdate(String hql);
	public boolean executeInsert(String sql);
	public boolean deleteByPrimarykey(String hql,String id);
	public List fuzzySearch(String hql);

	public List select(Class entity, Map whereMap,int first,int limit);
	public int count(Class entity,Map map);

	public Map selectMulti(String[] entityList, String[] propertyNameList, String[] whereConditions, int start, int limit);
	public JSONObject selectMulti(int queryType, List params, int start, int limit);
	
	public List callProcedure(String pName, List params);

	public List executeSQL(String sql);

}