/**
 * DataCollection/com.insit.controller/UpdateResponseController.java
 */
package com.insit.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.model.ModifyTableInfo;
import com.insit.model.ModifyTableInfoId;


/**
 * @author zq
 * 2014-4-9 涓嬪崍5:21:01
 */

@Controller
@RequestMapping("/update")
public class UpdateResponseController {
	
	private IBaseHibernateDAO dao = null;
	
	@Autowired
	public void setDAO(IBaseHibernateDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping(value="/qc_rules",method=RequestMethod.POST)
	public @ResponseBody String HandleQcRuleUpdate(@RequestParam("changed_data") String changedData){
		String SUCCESS_FLAG = "1";
		
		try{
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			
			jsonArr = JSONArray.fromObject(changedData);
			for(int i = 0; i <jsonArr.size();i++){
				jsonObj = (JSONObject) jsonArr.get(i);
				String HQLStr = "update QcRule qcRule set qcRule.businessType='" + jsonObj.getString("bus_type")
						+"',qcRule.qcType ='" + jsonObj.getString("qc_type") 
						+"',qcRule.qcDescription = '" + jsonObj.getString("qcDes") 
						+ "',qcRule.sysDescription= '" + jsonObj.getString("sysDes")
						+"' where qcRule.qcRuleId ='" + jsonObj.getString("id") +"'";
				dao.executeUpdate(HQLStr);
			}			
		}catch(Exception e){
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
	@RequestMapping(value="/upload_bulletin",method=RequestMethod.POST)
	public @ResponseBody String HandleBulletinTableUpdate(@RequestParam("changed_data") String changedData){
		String SUCCESS_FLAG = "1";
		
		try{
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			
			jsonArr = JSONArray.fromObject(changedData);
			for(int i = 0; i <jsonArr.size();i++){
				jsonObj = (JSONObject) jsonArr.get(i);		
				String idStr = (String) jsonObj.get("doc_id");
				String bulletin_id = idStr.substring((idStr.indexOf(">")+1), idStr.lastIndexOf("<"));			
				String HQLStr = "update BulletinTable bt set bt.bulletinType='" + jsonObj.getString("doc_type") 
						+"',bt.accountDate ='" + jsonObj.getString("account_date") 
						+"' where bt.bulletinId ='" + bulletin_id +"'";
				if(!dao.executeUpdate(HQLStr)){
					SUCCESS_FLAG = "0";
				}
			}			
		}catch(Exception e){
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
	
	@RequestMapping(value="/company_list",method=RequestMethod.POST)
	public @ResponseBody String HandleCompanyListUpdate(@RequestParam("changed_data") String changedData){
		String SUCCESS_FLAG = "1";
		
		try{
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			
			jsonArr = JSONArray.fromObject(changedData);
			for(int i = 0; i <jsonArr.size();i++){
				jsonObj = (JSONObject) jsonArr.get(i);
				
				String HQLStr = "update StockTable st set st.exchange='" + jsonObj.getString("exchange")
						+"',st.companyId ='" +  jsonObj.getString("id")
						+"',st.stockName ='" +  jsonObj.getString("compName")
						+"',st.stockType ='" +  jsonObj.getString("stockType")
						+ "' where st.stockId ='" +jsonObj.getString("stockCode") +"'";
				if(!dao.executeUpdate(HQLStr)){
					SUCCESS_FLAG = "0";
				}
			}			
		}catch(Exception e){
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
	@RequestMapping(value="/table_content",method=RequestMethod.POST)
	public @ResponseBody String HandleTableContentUpdate(@RequestParam("changed_data") String content,
			@RequestParam("bulletin_id") String bulletinId,
			@RequestParam("table_type") Short tableType){
		String SUCCESS_FLAG = "1";
		ModifyTableInfoId id = new ModifyTableInfoId();
		id.setBulletinId(bulletinId);
		id.setTableType(tableType);
		ModifyTableInfo mst = (ModifyTableInfo) dao.findById(ModifyTableInfo.class, id);
		if(mst == null){
			System.out.println("null");
			try{
				mst = new ModifyTableInfo();
				mst.setContent(content);
				mst.setId(id);
				dao.save(mst);
			}catch(Exception e){
				e.printStackTrace();
				SUCCESS_FLAG = "0";
			}
		}else{
			String HQLStr = "update ModifyTableInfo mst set mst.content='" + content
					+ "' where mst.id.bulletinId ='" + bulletinId + "' and mst.id.tableType ='" + tableType + "'";
			SUCCESS_FLAG = dao.executeUpdate(HQLStr) ? "1" :"0";
			System.out.println(HQLStr);
		}
		return SUCCESS_FLAG;
	}
	
	@RequestMapping(value="/bulletin_missing",method=RequestMethod.POST)
	public @ResponseBody String HandleBulletinMissingUpdate(@RequestParam("changed_data") String changedData){
		String SUCCESS_FLAG = "1";
		try{
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			
			jsonArr = JSONArray.fromObject(changedData);
			for(int i = 0; i <jsonArr.size();i++){
				jsonObj = (JSONObject) jsonArr.get(i);
				String HQLStr = "update StockTable st set st.frequence='" + jsonObj.getString("frequency")
						+"' where st.stockId ='" + jsonObj.getString("stock_id") +"'";
				dao.executeUpdate(HQLStr);
			}			
		}catch(Exception e){
			SUCCESS_FLAG = "0";
		}
		return SUCCESS_FLAG;
	}
	
}
