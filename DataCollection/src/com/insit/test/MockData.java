/**
 * DataCollection/com.insit.test/MockData.java
 */
package com.insit.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

/**
 * @author nano
 * 2014-1-14 pm 2:57:31
 */
public class MockData {

	/*
	 * download log data
	 */
	private static final String[] DOWNLOAD_LOGS_TIME = {"2012-12-02 14:00:28", 
		"2012-11-08 15:34:12", "2012-05-06 10:21:45"};
	private static final String[] DOWNLOAD_LOGS_DATA_TYPE = {"PDF文档下载", "Web数据抓取"};
	private static final String[] DOWNLOAD_LOGS_STATUS = {"成功", "失败"};
	private static final String[] DOWNLOAD_LOGS_NAME_PDF = {"深交所 | 年报 | 2013-03-31", 
		"上交所 | 季度报 | 2012-05-21", "深交所 | 半年报 | 2013-08-11"};
	private static final String[] DOWNLOAD_LOGS_NAME_WEB = {"广发银行 | 广发银行 | 金融业", 
		"腾讯 | 腾讯 | 互联网", "中软 | 中软 | 信息软件"};
	
	/*
	 * analysis log data
	 */
	private static final String[] ANALYSIS_LOGS_TIME = {"2012-12-02 14:00:28", 
		"2012-11-08 15:34:12", "2012-05-06 10:21:45"};
	private static final String[] ANALYSIS_LOGS_DATA_TYPE = {"PDF文档解析"};
	private static final String[] ANALYSIS_LOGS_STATUS = {"成功", "失败"};
	private static final String[] ANALYSIS_LOGS_NAME = {"深交所 | 年报 | 2013-03-31", 
		"上交所 | 季度报 | 2012-05-21", "深交所 | 半年报 | 2013-08-11"};
	
	
	public static List getDownloadLogsRecords(int num) {
		ArrayList<String> records = new ArrayList<String>();
		for(int i = 0; i < num; i++) {
			JSONObject item = new JSONObject();
			Random random = new Random();
			item.put("id", "DL00" + i);
			item.put("time", DOWNLOAD_LOGS_TIME[random.nextInt(DOWNLOAD_LOGS_TIME.length)]);
			item.put("data_type", DOWNLOAD_LOGS_DATA_TYPE[random.nextInt(DOWNLOAD_LOGS_DATA_TYPE.length)]);
			item.put("status", DOWNLOAD_LOGS_STATUS[random.nextInt(DOWNLOAD_LOGS_STATUS.length)]);
			if(item.get("data_type").equals("PDF文档下载")) {
				item.put("name", DOWNLOAD_LOGS_NAME_PDF[random.nextInt(DOWNLOAD_LOGS_NAME_PDF.length)]);
			} else {
				item.put("name", DOWNLOAD_LOGS_NAME_WEB[random.nextInt(DOWNLOAD_LOGS_NAME_WEB.length)]);
			}
			records.add(item.toString());
		}
		
		return records;
	}
	
	public static List getAnalysisLogsRecords(int num) {
		ArrayList<String> records = new ArrayList<String>();
		for(int i = 0; i < num; i++) {
			JSONObject item = new JSONObject();
			Random random = new Random();
			item.put("id", "AL00" + i);
			item.put("time", ANALYSIS_LOGS_TIME[random.nextInt(ANALYSIS_LOGS_TIME.length)]);
			item.put("data_type", ANALYSIS_LOGS_DATA_TYPE[random.nextInt(ANALYSIS_LOGS_DATA_TYPE.length)]);
			item.put("status", ANALYSIS_LOGS_STATUS[random.nextInt(ANALYSIS_LOGS_STATUS.length)]);
			item.put("name", ANALYSIS_LOGS_NAME[random.nextInt(ANALYSIS_LOGS_NAME.length)]);
			records.add(item.toString());
		}
		
		return records;
	}
	
	public static List getBulletinMissingRecords(int num) {
		ArrayList<String> records = new ArrayList<String>();
		for(int i = 0; i < num; i++) {
			JSONObject item = new JSONObject();
			Random random = new Random();
			item.put("id", "BM00" + i);
			item.put("com_id", "CM00" + i);
			item.put("stock_id", "00" + i);
			item.put("com_name", "xxxx");
			item.put("doc_type", "年报");
			item.put("accounting_date", "201x-xx-xx");
			item.put("stock_type", "A股");
			item.put("exchange", "上交");
			item.put("missing_type", "缺失");
			
			records.add(item.toString());
		}
		
		return records;
	}
}
