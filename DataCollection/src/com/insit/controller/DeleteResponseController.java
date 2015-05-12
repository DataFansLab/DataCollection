/**
 * DataCollection/com.insit.controller/DeleteResponseController.java
 * 2014-4-10/下午5:15:00 by Administrator
 */
package com.insit.controller;

import java.io.File;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.model.AnalysisDetailError;
import com.insit.model.AnalysisDetailErrorId;
import com.insit.model.BulletinTable;
import com.insit.model.QcRule;

/**
 * @author zq
 *
 */

@Controller
@RequestMapping("/delete")
public class DeleteResponseController {
	
	private IBaseHibernateDAO dao = null;
	
	@Autowired
	public void setDAO(IBaseHibernateDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping(value="/qc_rules",method=RequestMethod.POST)
	public @ResponseBody String HandleQCRuleDelete(@RequestParam("oids") List ids){
		String hql = "delete from QcRule where qcRuleId = ?";
		return DeleteHandler(hql,ids)? "1" : "0";
	}
	
	@RequestMapping(value="/upload_bulletin",method=RequestMethod.POST)
	public @ResponseBody String HandleUploadBulletinDelete(@RequestParam("oids") List ids){
		for(Object id : ids){
			String idStr = id.toString();
			id = idStr.substring((idStr.indexOf(">")+1), idStr.lastIndexOf("<"));
			System.out.println(id);
			BulletinTable bulletin = (BulletinTable) dao.findById(BulletinTable.class, id.toString());
			String path = bulletin.getPath();
			try{
				System.out.println("path:" + path);
				File file = new File (path);
				if(file.isFile() && file.exists())
					file.delete();
				else{
					System.out.println("文件不存在");
					return "0";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String hql = "update BulletinTable bt set bt.downloadStatus = 3 where bt.bulletinId = '" + id +"'" ;
			dao.executeUpdate(hql);
		}
		return "1";
	}
	
	@RequestMapping(value="/company_list",method=RequestMethod.POST)
	public @ResponseBody String HandleCompanyListDelete(@RequestParam("oids") List ids){
		String hql = "delete from StockTable where stockId = ?";
		return DeleteHandler(hql,ids)? "1" : "0";
	}
	
	@RequestMapping(value="/qcPrompts/download",method=RequestMethod.POST)
	public @ResponseBody String HandleQCPromptsDelete(@RequestParam("oids") List ids){
		String hql = "delete from DownloadError de where bulletinId = ?";
		return DeleteHandler(hql,ids)? "1" : "0";
	}
	
	@RequestMapping(value="/qc_prompts/analysis",method=RequestMethod.POST)
	public @ResponseBody String HandleAnalysisQcPromptsDelete(@RequestParam("oids") List ids){
		for(Object id : ids){
			String idStr = id.toString();
			String bulletinId = idStr.substring(0, idStr.indexOf("_"));
			String type = idStr.substring(idStr.indexOf("_")+1);
			String HQL = "delete from AnalysisDetailError ade where ade.id.bulletinId = '" + bulletinId + 
					"' and ade.id.tableType = '" + type + "'";
			dao.executeUpdate(HQL);
		}
		return "1";
	}
	
	@RequestMapping(value="/bulletin_missing",method=RequestMethod.POST)
	public @ResponseBody String HandleBulletinMissingDelete(@RequestParam("oids") List ids){
		String hql = "delete from MissingBulletin mb where id = ?";
		return DeleteHandler(hql,ids)? "1" : "0";
	}
	
	private boolean DeleteHandler(String hql,List ids){
		try{
			for(Object id : ids){
				dao.deleteByPrimarykey(hql,(String) id);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	
}
