/**
 * DataCollection/com.insit.service.impl/QueryService.java
 * 2014-3-27/下午10:00:58 by Nano
 */
package com.insit.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.dao.impl.QueryObject;
import com.insit.model.BulletinTable;
import com.insit.service.IQueryService;

/**
 * @author Nano
 * 
 */
@Service
public class QueryService implements IQueryService {

	private IBaseHibernateDAO baseDAO = null;

	@Autowired
	public void setBaseDAO(IBaseHibernateDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	@Override
	public String bulletinMissingQuery(String paramsStr, int start, int limit) {
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"stock_id", "accounting_date", "bulletin_type"};
			
			List params = getParamsList(jsonParams, titles, false);
			
			return baseDAO.selectMulti(QueryObject.BULLETIN_MISSING, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String uploadBulletinQuery(String paramsStr, int start, int limit) {
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"stock_name", "bulletin_id", "bulletin_type", "download_status", "priority", "qc", "qc_state"};
			
			List results = baseDAO.callProcedure("bulletins", getParamsListWithPageLimit(jsonParams, titles, true, start, limit));
			
			JSONArray listJson = new JSONArray();
			JSONObject jsonResult = new JSONObject();
			
			for (int i = 0; i < results.size(); i++) {
				Object[] row = (Object[]) results.get(i);
				if (i == 0) {
					jsonResult.put("totalCount", row[11].toString());
					if (row[11].toString().equals("0") || (Integer.parseInt(row[11].toString()) < start))
						break;
				}
				
				JSONObject item = new JSONObject();
				item.put("doc_id", row[0].toString());
				item.put("company_id", row[1].toString());
				item.put("company_code", row[2].toString());
				item.put("company_name", row[3].toString());
				item.put("doc_type", row[4].toString());
				item.put("account_date", row[5].toString());
				item.put("release_date", row[6].toString());
				item.put("stock_type", row[7].toString());
				item.put("exchange", row[8].toString());
				item.put("states", row[9].toString());
				item.put("qc_error_num", row[10].toString());
				listJson.add(item);
				
			}
			if (!jsonResult.has("totalCount"))
				jsonResult.put("totalCount", 0);
			
			jsonResult.put("rows", listJson);
			
			return jsonResult.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String downloadQcLogQuery(String paramsStr, int start, int limit) {
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"stock_id", "status"};
			List params = getParamsList(jsonParams, titles, true);
			
			return baseDAO.selectMulti(QueryObject.DOWNLOAD_QC_LOG, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String downloadQCPromptsQuery(String paramsStr, int start, int limit){
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"bulletin_id", "company_id", "error_type" };
			List params = getParamsList(jsonParams, titles, true);
			
			return baseDAO.selectMulti(QueryObject.DOWNLOAD_QC_PROMPTS, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String downloadTaskLogsQuery(String paramsStr,int start,int limit){
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"data_type", "download_status"};
			List params = getParamsList(jsonParams, titles, true);
			
			return baseDAO.selectMulti(QueryObject.DOWNLOAD_TASK_LOG, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String companyListQuery(String paramsStr,int start,int limit){
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"stockId", "exchange", "stockType"};
			List params = getParamsList(jsonParams, titles, false);
			
			return baseDAO.selectMulti(QueryObject.COMPANY_LIST, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private JSONObject paramsStr2Json(String paramsStr) {
		paramsStr = paramsStr.replace("\\", "");
		paramsStr = paramsStr.substring(1, paramsStr.length() - 1);
		return JSONObject.fromObject(paramsStr);
	}
	
	private List getParamsListWithPageLimit(JSONObject jsonParams, String[] titles, boolean dateLimit, int start, int limit) {
		List params = new ArrayList<String>();
		for (String title : titles) {
			if (jsonParams.containsKey(title))
				params.add(jsonParams.getString(title));
			else params.add("%");
		}
		if (dateLimit) {
			if (jsonParams.containsKey("start_date"))
				params.add(jsonParams.getString("start_date"));
			else params.add("2000-01-01");
			if (jsonParams.containsKey("end_date"))
				params.add(jsonParams.getString("end_date"));
			else params.add("2099-01-01");
		}
		params.add(start);
		params.add(limit);
		return params;
	}
	
	private List getParamsList(JSONObject jsonParams, String[] titles, boolean dateLimit) {
		List params = new ArrayList<String>();
		for (String title : titles) {
			if (jsonParams.containsKey(title))
				params.add("'" + jsonParams.getString(title) + "'");
			else params.add("'%'");
		}
		if (dateLimit) {
			if (jsonParams.containsKey("start_date"))
				params.add("'" + jsonParams.getString("start_date") + "'");
			else params.add("'2000-01-01'");
			if (jsonParams.containsKey("end_date"))
				params.add("'" + jsonParams.getString("end_date") + "'");
			else params.add("'2099-01-01'");
		}
		return params;
	}

	

	@Override
	public String analysisTaskListQuery(String paramsStr, int start, int limit) {
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"doc_id", "stock_id", "bulletin_status", "home", "priority", "qc_state"};
			List results = baseDAO.callProcedure("analysis_tasks", getParamsListWithPageLimit(jsonParams, titles, true, start, limit));
			
			JSONArray listJson = new JSONArray();
			JSONObject jsonResult = new JSONObject();
			
			for (int i = 0; i < results.size(); i++) {
				Object[] row = (Object[]) results.get(i);
				if (i == 0) {
					jsonResult.put("totalCount", row[9].toString());
					if (row[9].toString().equals("0") || (Integer.parseInt(row[9].toString()) < start))
						break;
				}
				
				JSONObject item = new JSONObject();
				item.put("doc_id", row[0].toString());
				item.put("com_id", row[1].toString());
				item.put("com_name", row[2].toString());
				item.put("doc_type", row[3].toString());
				item.put("account_date", row[4].toString());
				item.put("analysis_state", row[5].toString());
				item.put("analysis_date", (row[6] == null) ? "--" : row[6].toString());
				item.put("aor_link", row[0].toString());
				item.put("aor_qc_num", row[7].toString());
				item.put("css_link", row[0].toString());
				item.put("css_qc_num", row[8].toString());
				listJson.add(item);
				
			}
			if (!jsonResult.has("totalCount"))
				jsonResult.put("totalCount", 0);
			
			jsonResult.put("rows", listJson);
			
			return jsonResult.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String analysisQcPromptsQuery(String paramsStr, int start, int limit) {
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		JSONObject returnObject = null;
		try {
			String[] titles = {"doc_id", "com_id"};
			List params = getParamsList(jsonParams, titles, true);
			if (jsonParams.containsKey("phase")) {
				if (jsonParams.get("phase").equals("0")) {
					if (jsonParams.containsKey("qc_type")) {
						if (jsonParams.get("qc_type").equals("0"))
							params.add("not ade.locateInfo='0'");
						else params.add("not ade.fieldconflict='0'");
					} else params.add("((not ade.locateInfo='0') or (not ade.fieldconflict='0'))");
					
					returnObject = baseDAO.selectMulti(QueryObject.ANALYSIS_QC_PROMPTS_AOR, params, start, limit);
				} else {
					if (jsonParams.containsKey("qc_type")) {
						if (jsonParams.get("qc_type").equals("0"))
							params.add("ade.fieldnameQc='1'");
						else params.add("ade.fieldnameQc='2'");
					} else params.add("not ade.fieldnameQc='0'");
					
					returnObject = baseDAO.selectMulti(QueryObject.ANALYSIS_QC_PROMPTS_CSS, params, start, limit);
				}
			} else {
				returnObject = baseDAO.selectMulti(QueryObject.ANALYSIS_QC_PROMPTS, params, start, limit);
			}
			return returnObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String analysisLogsQuery(String paramsStr, int start, int limit) {
		JSONObject jsonParams = paramsStr2Json(paramsStr);
		try {
			String[] titles = {"analysis_status"};
			List params = getParamsList(jsonParams, titles, true);
			
			return baseDAO.selectMulti(QueryObject.ANALYSIS_LOGS, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String findBulletinFilePath(String docId) {
		BulletinTable bt = (BulletinTable) baseDAO.findById(BulletinTable.class, docId);
		return bt.getPath();
	}
	
	@Override
	public String affairsAssignQuery(String emailAddr,String stockCode, int start, int limit) {
		try {
			List params = new ArrayList();
			emailAddr = emailAddr.equals("") ?  "%" : emailAddr;
			stockCode = stockCode.equals("") ?  "%" : stockCode;
			params.add("'" + stockCode + "'");
			params.add("'" + emailAddr + "'");
			
			return baseDAO.selectMulti(QueryObject.AFFAIRS_ASSIGN, params, start, limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String qcRulesQuery(String type,int start,int limit){
		try {
			List params = new ArrayList();
			params.add("'" + type + "'");
			return baseDAO.selectMulti(QueryObject.QC_RULES, params,start,limit).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String companyInfoQuery(String stockId, String catagory) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String path = QueryObject.companyInfoCatagory.get(catagory);
		String level1 = path.substring(0, path.indexOf("/"));
		String level2 = path.substring(path.indexOf("/") + 1);
		try {
			HttpGet companyInfoGet = new HttpGet("http://192.168.5.53:8080/dataAccess_rest/stock/"
					+ stockId + "/" + path);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				
				@Override
				public String handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return ((entity != null) ? EntityUtils.toString(entity) : null);
					}
					return null;
				}
			};
			
			String responseBody = httpClient.execute(companyInfoGet, responseHandler);
			JSONObject row = null;
			if (catagory.equals("fanwei") || catagory.equals("jianjie")) {
				row = JSONObject.fromObject(responseBody).getJSONObject(level1);
			} else row = JSONObject.fromObject(responseBody).getJSONObject(level1).getJSONObject(level2);
			JSONArray responseRow = new JSONArray();
			Iterator it = row.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) row.get(key);
				JSONObject item = new JSONObject();
				item.put("key", key);
				item.put("value", value);
				responseRow.add(item);
			}
			JSONObject result = new JSONObject();
			result.put("row", responseRow);
			
			httpClient.close();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String downloadHomeQuery(String status) {
		List params = new ArrayList<String>();
		params.add(status);
		List result = baseDAO.callProcedure("download_home", params);
		JSONArray row = new JSONArray();
		for (int i = 0; i < result.size(); i += 10) {
			JSONObject JSON_item = new JSONObject();
			int day = (int) ((Object[]) result.get(i))[1];
			JSON_item.put("delay", (day == -1) ? ">4天" : (day + "天"));
			JSON_item.put("p" + ((Object[]) result.get(i))[2], ((Object[]) result.get(i))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 1))[2], ((Object[]) result.get(i + 1))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 2))[2], ((Object[]) result.get(i + 2))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 3))[2], ((Object[]) result.get(i + 3))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 4))[2], ((Object[]) result.get(i + 4))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 5))[2], ((Object[]) result.get(i + 5))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 6))[2], ((Object[]) result.get(i + 6))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 7))[2], ((Object[]) result.get(i + 7))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 8))[2], ((Object[]) result.get(i + 8))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 9))[2], ((Object[]) result.get(i + 9))[0]);
			row.add(JSON_item);
		}
		
		JSONObject res = new JSONObject();
		res.put("row", row);
		
		return res.toString();
	}

	@Override
	public String exchangeWeeklyQuery() {
		List result = baseDAO.callProcedure("home_sys_prompt", null);
		String[] exchanges = new String[] {"深交所", "上交所"};
		JSONArray row = new JSONArray();
		for (int i = 0; i < result.size(); i++) {
			JSONObject item = new JSONObject();
			item.put("exchange", exchanges[i]);
			item.put("w1", ((Object[]) result.get(i))[0]);
			item.put("w2", ((Object[]) result.get(i))[1]);
			item.put("w3", ((Object[]) result.get(i))[2]);
			item.put("w4", ((Object[]) result.get(i))[3]);
			row.add(item);
		}
		
		JSONObject res = new JSONObject();
		res.put("row", row);
		
		return res.toString();
	}

	@Override
	public String homeMissingQuery() {
		List<Map> result = baseDAO.executeSQL(QueryObject.getQueryStr(QueryObject.MISSING_SYS_PROMPT));
		JSONArray row = new JSONArray();
		JSONObject item1 = new JSONObject();
		item1.put("id", "缺失文档的公司数");
		item1.put("data", result.get(0).get("0"));
		row.add(item1);
		JSONObject item2 = new JSONObject();
		item2.put("id", "缺失文档的文档数");
		item2.put("data", result.get(0).get("1"));
		row.add(item2);
		
		JSONObject res = new JSONObject();
		res.put("row", row);
		
		return res.toString();
	}

	@Override
	public String analysisHomeQuery(String status) {
		List params = new ArrayList<String>();
		params.add(status);
		List result = baseDAO.callProcedure("analysis_home", params);
		JSONArray row = new JSONArray();
		for (int i = 0; i < result.size(); i += 10) {
			JSONObject JSON_item = new JSONObject();
			int day = (int) ((Object[]) result.get(i))[1];
			JSON_item.put("delay", (day == -1) ? ">4天" : (day + "天"));
			JSON_item.put("p" + ((Object[]) result.get(i))[2], ((Object[]) result.get(i))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 1))[2], ((Object[]) result.get(i + 1))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 2))[2], ((Object[]) result.get(i + 2))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 3))[2], ((Object[]) result.get(i + 3))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 4))[2], ((Object[]) result.get(i + 4))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 5))[2], ((Object[]) result.get(i + 5))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 6))[2], ((Object[]) result.get(i + 6))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 7))[2], ((Object[]) result.get(i + 7))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 8))[2], ((Object[]) result.get(i + 8))[0]);
			JSON_item.put("p" + ((Object[]) result.get(i + 9))[2], ((Object[]) result.get(i + 9))[0]);
			row.add(JSON_item);
		}
		
		JSONObject res = new JSONObject();
		res.put("row", row);
		
		return res.toString();
	}
}
