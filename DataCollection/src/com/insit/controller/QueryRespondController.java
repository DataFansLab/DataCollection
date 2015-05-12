/**
 * DataCollection/com.insit.controller/QueryRespondController.java
 */
package com.insit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insit.dao.IBaseHibernateDAO;

import com.insit.model.AffairsAssign;
import com.insit.model.BulletinTable;
import com.insit.model.QcRule;
import com.insit.model.TempTableInfoId;
//import com.insit.model.QcRule;
import com.insit.model.StockTable;

import com.insit.model.TempTableInfo;
import com.insit.service.IQueryService;


/**
 * @author zhuqiao
 * 2014-3-10 下午5:19:40
 */

@Controller
@RequestMapping("/query")

public class QueryRespondController {
	
	private IQueryService queryService = null;

	private IBaseHibernateDAO dao = null;

	@Autowired
	public void setDAO(IBaseHibernateDAO dao) {
		this.dao = dao;
	}
	
	@Autowired
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	@RequestMapping(value = "/table_content",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	@ResponseBody public String getTableInfo(){
		String response = "";

		List<TempTableInfo> res = dao.findByProperty("TempTableInfo","time", "2012-12-31");			
		
		for(TempTableInfo info : res){
			response = info.getContent().toString();
		}		
		return response;
	}
	
	@RequestMapping(value = "/table_content_byId",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public @ResponseBody String getTableInfoById(@RequestParam("bulletin_id") String bulletinId, @RequestParam("table_type") String type){
		String response = "";
		TempTableInfoId id = new TempTableInfoId(bulletinId,Short.parseShort(type));
		try{
			TempTableInfo table = (TempTableInfo) dao.findById(TempTableInfo.class,id);
			
			if(table == null){
				System.out.println("table is null");
				int i = Integer.parseInt(type) + 4;
				System.out.println(" i : " + i);
				id = new TempTableInfoId(bulletinId,Short.parseShort(Integer.toString(i)));
				table = (TempTableInfo) dao.findById(TempTableInfo.class,id);
			}
			if(table == null ) return "null";
			BulletinTable bulletin = (BulletinTable) dao.findById(BulletinTable.class, bulletinId);
			StockTable stock = (StockTable) dao.findById(StockTable.class, bulletin.getStockId());
			String stockName = stock.getStockName();
			String companyId = stock.getCompanyId();
			String industry = stock.getIndustry();
			String exchange = stock.getExchange();
			String bulletinType = bulletin.getBulletinYear() + "年" + bulletin.getBulletinType();
			response = "{\"stockName\":\"" + stockName + "\",\"companyId\":\"" +companyId 
					+ "\",\"industry\":\"" + industry + "\",\"exchange\":\"" + exchange
					+ "\",\"bulletinType\":\"" + bulletinType + "\",\"content\":" + table.getContent() + "}";
			System.out.println(" response : " + response);		
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/getStockInfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getStrockInfo(@RequestParam("params") String params){
		String response = "";
		String hql = null;
		params = params.replace("\"", "");
		params = unicodeToString(params);
		if(isNumeric(params))
			hql = "from StockTable as st where st.stockId like"+"'%" + params +"%'";
		else if(isChinese(params))
			hql = "from StockTable as st where st.stockName like"+"'%" + params +"%'";
		else
			hql = "from StockTable as st where st.companyId like"+"'%" + params +"%'";
		List<StockTable> res = dao.fuzzySearch(hql);
		
		response += "{\"rows\":[";
		for(StockTable st: res){
			response += "{\"stock_info\":" + "\"" + st.getCompanyId() + " | " 
						+ st.getStockId() + " | " 
						+ st.getStockName() + "\"},";
		}
		response += "]}";
		return response;
	}
	
	@RequestMapping(value = "company_list",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCompanyList(@RequestParam("paramsList") String paramsJsonStr,int start, int limit){		
		return queryService.companyListQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	
	@RequestMapping(value = "uploadBulletin",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUploadBulletinList (@RequestParam("paramList") String paramsJsonStr, int start, int limit) {
		return queryService.uploadBulletinQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/bulletin/missing", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getBulletinMissingList (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.bulletinMissingQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/download/qclog", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDownloadQcLogList (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.downloadQcLogQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/qc_prompts/download", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDownloadQCPromptsList (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.downloadQCPromptsQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/task_logs/download", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDownloadTaskLogsQuery (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.downloadTaskLogsQuery(unicodeToString(paramsJsonStr), start, limit);
	}

	@RequestMapping(value = "/analysis/taskList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAnalysisTaskListQuery (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.analysisTaskListQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/analysis/qcPrompts", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAnalysisQcPromptsQuery (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.analysisQcPromptsQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/analysis/logs", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAnalysisLogsQuery (@RequestParam("paramsList") String paramsJsonStr, int start, int limit) {
		return queryService.analysisLogsQuery(unicodeToString(paramsJsonStr), start, limit);
	}
	
	@RequestMapping(value = "/affairs_assign",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody 
	public String getAffairsAssign(@RequestParam("stock_code") String stockCode, 
			@RequestParam("email_addr") String emailAddr,
			@RequestParam("start") String start,
			@RequestParam("limit") String limit){
		stockCode = stockCode.replace("\"", "");
		emailAddr = emailAddr.replace("\"", "");
		return queryService.affairsAssignQuery(emailAddr, stockCode, Integer.parseInt(start), Integer.parseInt(limit));
	}
	
	@RequestMapping(value = "/qc_rules",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody 
	public String getQcRules(@RequestParam("business_type") String type,int start, int limit){
		return queryService.qcRulesQuery(type,start,limit);
	}
	
	@RequestMapping(value = "/companyInfo/{stockId}/{catagory}", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCompanyInfo(@PathVariable("stockId") String stockId, 
			@PathVariable("catagory") String catagory) {
		return queryService.companyInfoQuery(stockId, catagory);
	}
	
	@RequestMapping(value = "/downloadHome/{status}", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDownloadHome(@PathVariable("status") String status) {
		return queryService.downloadHomeQuery(status);
	}
	
	@RequestMapping(value = "/analysisHome/{status}", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAnalysisHome(@PathVariable("status") String status) {
		return queryService.analysisHomeQuery(status);
	}
	
	@RequestMapping(value = "/exchangeWeekly", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getExchangeWeekly() {
		return queryService.exchangeWeeklyQuery();
	}
	
	@RequestMapping(value = "/homeMissing", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getHomeMissing() {
		return queryService.homeMissingQuery();
	}

	public List  getMap(String[] titles , String[] values){
		List whereConditions =  new ArrayList();
		for(int i = 0; i < values.length; i++){
			String value = unicodeToString(values[i]).replace("\"", "");
			if(value.equals("")==false && value.equals("全选") == false ){
				whereConditions.add(titles[i] + "='"+ value +"'");
			}
		}
		return whereConditions;
	}
	
	public static String unicodeToString(String str) {
	    Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
	    Matcher matcher = pattern.matcher(str);
	    char ch;
	    while (matcher.find()) {
	        ch = (char) Integer.parseInt(matcher.group(2), 16);
	        str = str.replace(matcher.group(1), ch + "");    
	    }
	    return str;
	}
	
	
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isChinese(String str){
		if(str.getBytes().length == str.length())
			return false;
		return true;
	}
	
}
