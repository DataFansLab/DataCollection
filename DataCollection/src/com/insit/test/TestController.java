/**
 * DataCollection/com.insit.test/TestController.java
 */
package com.insit.test;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * @author nano
 * 2014-1-8 am 10:43:56
 */

@Controller
@RequestMapping("/test")
public class TestController {
	
	private ArrayList<String> docList;
	private List download_logs = null;
	private List analysis_logs = null;
	private List bulletin_missing = null;
	
	public TestController() {
		docList = new ArrayList<String>();
		for(int i = 0; i < 50; i++) {
			docList.add("DC0000" + i);
		}
	}
	
	@RequestMapping(value = "/getStockInfo")
	@ResponseBody
	public String getStrockInfo(@RequestParam("params") String params){
		String response = "";
		for(int i = 0; i < 20 ; i++) {
			response += ("{stock_id: '00000" + i + "'," +
					"com_id:'CI00000" + i+ "'},");
		}
		response = "{rows: [" + response + "]}";
		
		return response;
	}

	@RequestMapping(value="/qcprompts/download/getRecords", produces="text/html;charset=utf-8")
	@ResponseBody
	public String pushTestJsonData(int start, int limit) {
		String rows = "";
		for(int i = start; i < docList.size() && i < (start + limit); i++) {
			rows += ("{doc_id: '" + docList.get(i) + 
					"', com_id:'CI" + docList.get(i) +
					"', stock_id:'" + docList.get(i) +
					"', com_name: '****', qc_time: '2013-05-03', error_type: '冗余', qc_desp: '与文档D00024的hashcode不同，但meta信息相同', qc_pos: '流程位置：下载；提示位置：NULL'},");
		}
		String root = "{rows: [" + rows + "], totalCount: " + docList.size() + "}";
		
		return root;
	}
	
	@RequestMapping(value="/qcprompts/download/delete")
	@ResponseBody
	public String deleteRecords(String model) {
		System.out.println(model);
		JSONArray models = JSONArray.fromObject(model);
		for(Object item : models) {
			JSONObject jsonItem = (JSONObject) item;
			docList.remove(jsonItem.getString("id"));
		}
		
		return "success";
	}
	
	@RequestMapping(value="/taskLogs/download/getFields", produces="text/html;charset=utf-8")
	@ResponseBody
	public String getDownloadLogsFields() {
		String[] fields = {"id", "time", "data_type", "status", "name"};
		String[] headers = {"ID", "时间", "数据类别", "下载状态", "数据名称"};

		JSONArray array = new JSONArray();
		for(int i = 0; i < fields.length; i++) {
			JSONObject item = new JSONObject();
			item.put("field", fields[i]);
			item.put("header", headers[i]);
			array.add(item);
		}
		
		return "{initData: " + array.toString() + "}";
	}
	
	@RequestMapping(value="/taskLogs/download/getRecords", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getDownloadLogsRecords(int start, int limit) {
		if(download_logs == null) {
			download_logs = MockData.getDownloadLogsRecords(50);
		}
		String json = "[";
		for(int i = start; i < (start + limit); i++) {
			json += download_logs.get(i) + ",";
		}
		json += "]";
		return "{rows: " + json + ", totalCount: " + download_logs.size() + "}";
 	}
	
	@RequestMapping(value="/taskLogs/download/delete", produces="text/html; charset=utf-8")
	@ResponseBody
	public String deleteDownloadLogsRecords(String model) {
		JSONArray array = JSONArray.fromObject(model);
		for(Object item : array) {
			JSONObject record = (JSONObject) item;
			download_logs.remove(Integer.parseInt(record.getString("id").substring(4)));
		}
		
		return "success";
	}
	
	@RequestMapping(value="/taskLogs/analysis/getFields", produces="text/html;charset=utf-8")
	@ResponseBody
	public String getAnalysisLogsFields(String type) {
		String[] fields = {"id", "time", "data_type", "status", "name"};
		String[] headers = {"ID", "时间", "数据类别", "解析状态", "数据名称"};
		
		JSONArray array = new JSONArray();
		for(int i = 0; i < fields.length; i++) {
			JSONObject item = new JSONObject();
			item.put("field", fields[i]);
			item.put("header", headers[i]);
			array.add(item);
		}
		
		return "{initData: " + array.toString() + "}";
	}
	
	@RequestMapping(value="/taskLogs/analysis/getRecords", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getAnalysisLogsRecords(int start, int limit) {
		if(analysis_logs == null) {
			analysis_logs = MockData.getAnalysisLogsRecords(50);
		}
		String json = "[";
		for(int i = start; i < (start + limit); i++) {
			json += analysis_logs.get(i) + ",";
		}
		json += "]";
		return "{rows: " + json + ", totalCount: " + analysis_logs.size() + "}";
 	}
	
	@RequestMapping(value="/taskLogs/analysis/delete", produces="text/html; charset=utf-8")
	@ResponseBody
	public String deleteAnalysisLogsRecords(String model) {
		JSONArray array = JSONArray.fromObject(model);
		for(Object item : array) {
			JSONObject record = (JSONObject) item;
			analysis_logs.remove(Integer.parseInt(record.getString("id").substring(4)));
		}
		
		return "success";
	}
	
	@RequestMapping(value="/bulletinMissing/getFields", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getBulletinMissingFields() {
		String[] fields = {"id", "com_id", "stock_id", "com_name", "doc_type", "accounting_date", "stock_type", "exchange", "missing_type"};
		String[] headers = {"ID", "公司ID", "股票代码", "公司名称", "文档类型", "会计结算日期", "股票类型", "交易所", "缺失状态"};
		JSONArray array = new JSONArray();
		for(int i = 0; i < fields.length; i++) {
			JSONObject item = new JSONObject();
			item.put("field", fields[i]);
			item.put("header", headers[i]);
			array.add(item);
		}
		
		return array.toString();
	}
	
	@RequestMapping(value="/bulletinMissing/getRecords", produces="text/html; charset=utf-8")
	@ResponseBody
	public String getBulletinMissingRecords(int start, int limit) {
		if(bulletin_missing == null) {
			bulletin_missing = MockData.getBulletinMissingRecords(50);
		}
		String json = "[";
		for(int i = start; i < (start + limit); i++) {
			json += bulletin_missing.get(i) + ",";
		}
		json += "]";
		return "{rows: " + json + ", totalCount: " + bulletin_missing.size() + "}";
		
	}
	
	@RequestMapping(value="/bulletinMissing/delete", produces="text/html; charset=utf-8")
	@ResponseBody
	public String deleteBulletinMissingRecords(String model) {
		JSONArray array = JSONArray.fromObject(model);
		for(Object item : array) {
			JSONObject record = (JSONObject) item;
			bulletin_missing.remove(Integer.parseInt(record.getString("id").substring(4)));
		}
		
		return "success";
	}
}
