/**
 * DataCollection/com.insit.controller/InsertResponseController.java
 * 2014-4-10/涓嬪崍1:46:52 by Administrator
 */
package com.insit.controller;

import java.text.SimpleDateFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.model.BulletinTable;
import com.insit.model.QcRule;
import com.insit.model.StockTable;
import com.insit.util.StringOperate;

/**
 * @author zq
 *
 */

@Controller
@RequestMapping("/insert")
public class InsertResponseController {
	private IBaseHibernateDAO dao = null;
	private StringOperate handler = new StringOperate();
	
	@Autowired
	public void setDAO(IBaseHibernateDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping(value="/qc_rules",method=RequestMethod.POST)
	public @ResponseBody String HandleQCRuleInsert(@RequestParam("addedItem") String addedItem){
		String SUCCESS_FLAG = "1";
		try{
			JSONObject jsonObj = new JSONObject();
			jsonObj = JSONObject.fromObject(addedItem);
			
			QcRule qcrule = new QcRule();
			qcrule.setQcRuleId(jsonObj.getString("id"));
			qcrule.setQcDescription(jsonObj.getString("qcDes"));
			qcrule.setBusinessType(jsonObj.getString("bus_type"));
			qcrule.setQcType(jsonObj.getString("qc_type"));
			qcrule.setSysDescription(jsonObj.getString("sysDes"));
			dao.save(qcrule);		
		}catch(Exception e){
			e.printStackTrace();
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
	
	@RequestMapping(value="/bulletinTable",method=RequestMethod.POST)
	public @ResponseBody String HandleBulletinTableInsert(@RequestParam("addedItem") String addedItem){
		String SUCCESS_FLAG = "1";
		try{
			JSONObject jsonObj = new JSONObject();
			SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyy-MM-dd" ); 
			jsonObj = JSONObject.fromObject(addedItem);
			
			BulletinTable bt = new BulletinTable();
			bt.setBulletinId(jsonObj.getString("bulletin_id"));
			bt.setStockId(jsonObj.getString("stock_id"));
			bt.setBulletinName(jsonObj.getString("company_name"));
			bt.setBulletinType(jsonObj.getString("bulletinType"));
			bt.setAccountDate(sdf.parse(jsonObj.getString("account_date")));
			bt.setDownloadDate(sdf.parse(jsonObj.getString("download_date")));
			dao.save(bt);
		}catch(Exception e){
			e.printStackTrace();
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
	@RequestMapping(value="/companyList",method=RequestMethod.POST)
	public @ResponseBody String HandleCompanyListInsert(@RequestParam("addedItem") String addedItem){
		String SUCCESS_FLAG = "1";
		try{
			addedItem = handler.unicodeToString(addedItem);
			JSONObject jsonObj = new JSONObject();
			jsonObj = JSONObject.fromObject(addedItem);
			
			StockTable st = new StockTable();
			st.setCompanyId(jsonObj.getString("campany_id"));
			st.setStockId(jsonObj.getString("stock_id"));
			st.setStockName(jsonObj.getString("stock_name"));
			st.setStockType(jsonObj.getString("stock_type"));
			st.setIndustry(jsonObj.getString("industry"));
			st.setExchange(jsonObj.getString("exchange"));
			st.setStatus(jsonObj.getString("status"));
			st.setBlock(jsonObj.getString("block"));
			st.setListDate(jsonObj.getString("list_date"));
			st.setChangeDate(jsonObj.getString("change_date"));
			
			dao.save(st);				
		}catch(Exception e){
			e.printStackTrace();
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
}
