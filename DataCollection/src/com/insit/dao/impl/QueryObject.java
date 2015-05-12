/**
 * DataCollection/com.insit.dao.impl/QueryObject.java
 * 2014-4-8/下午3:29:26 by nano
 */
package com.insit.dao.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nano
 *
 */
public class QueryObject {
	
	public static final int BULLETIN_MISSING = 0;
	public static final int ANALYSIS_QC_PROMPTS = 1;
	public static final int DOWNLOAD_QC_LOG = 2;
	public static final int DOWNLOAD_QC_PROMPTS = 3;
	public static final int DOWNLOAD_TASK_LOG = 4;
	public static final int ANALYSIS_QC_PROMPTS_AOR = 5;
	public static final int ANALYSIS_QC_PROMPTS_CSS = 9;
	public static final int COMPANY_LIST = 6;
	public static final int ANALYSIS_QC_LOG = 8;
	public static final int ANALYSIS_LOGS = 10;
	public static final int AFFAIRS_ASSIGN = 11;
	public static final int QC_RULES = 12;
	public static final int MISSING_SYS_PROMPT = 13;
	
	public static final HashMap<String, String> companyInfoCatagory = new HashMap<String, String>() {
		{
			put("jichu", "公司资料/基础信息");
			put("zhengquan", "公司资料/证券信息");
			put("gongshang", "公司资料/工商信息");
			put("lianxi", "公司资料/联系方式");
			put("fanwei", "公司资料/经营范围");
			put("jianjie", "公司资料/公司简介");
		}
	};
	
	public static String getQueryStr(int queryType) {
		String queryStr = null;
		switch (queryType) {
		case MISSING_SYS_PROMPT:
			queryStr = "select new map(count(distinct mb.stockId), count(*)) from MissingBulletin mb";
			break;
		case BULLETIN_MISSING:
			queryStr = "select new map(" +
					"st.companyId," +
					"mb.id.stockId," +
					"st.stockName," +
					"mb.id.bulletinType," +
					"mb.id.accountDate," + 
					"st.stockType," +
					"st.exchange," + 
					"st.frequence," +
					"mb.id)" +
					" from " +
					"MissingBulletin mb," +
					"StockTable st" + 
					" where " +
					"mb.id.stockId like ? and " +
					"mb.id.accountDate like ? and " +
					"mb.id.bulletinType like ? and " +
					"mb.id.stockId=st.stockId";
			break;
		case DOWNLOAD_QC_LOG:
			queryStr = "select new map(" +
					"de.bulletinId," +
					"st.companyId," +
					"de.stockId," +
					"st.stockName," +
					"de.promptTime," +
					"de.operateTime," +
					"qr.qcType," +
					"qr.qcDescription," +
					"de.operateStatus)" +
					" from " +
					"DownloadError de," +
					"StockTable st," +
					"QcRule qr" +
					" where " +
					"de.stockId=st.stockId and " +
					"de.qcRuleId=qr.qcRuleId and " +
					"de.stockId like ? and " +
					"de.operateStatus like ? and " +
					"(de.promptTime between ? and ?)";
			break;
		case DOWNLOAD_QC_PROMPTS:
			queryStr = "select new map(" +
					"de.bulletinId," +
					"st.companyId," +
					"de.stockId," +
					"st.stockName," +
					"de.promptTime," +
					"qr.qcType," +
					"qr.qcDescription)" +
					" from " +
					"DownloadError de," +
					"StockTable st," +
					"QcRule qr" +
					" where " +
					"de.stockId=st.stockId and " +
					"de.qcRuleId=qr.qcRuleId and " +
					"de.bulletinId like ? and " +
					"st.companyId like ? and " +
					"qr.qcType like ? and " +
					"(de.promptTime between ? and ?)";
			break;
		case DOWNLOAD_TASK_LOG:
			queryStr = "select new map(" +
					"DATE_FORMAT(dl.id.downloadTime, '%Y-%m-%d')," +
					"DATE_FORMAT(dl.id.downloadTime, '%H:%i:%s')," +
					"dl.id.downloadStatus," +
					"dl.id.dataName," +
					"dl.id.dataType)" +
					" from " +
					"DownloadLog dl" +
					" where " +
					"dl.id.dataType like ? and " +
					"dl.id.downloadStatus like ? and " + 
					"(dl.id.downloadTime between ? and ?)";
			break;
		case ANALYSIS_QC_PROMPTS:
			queryStr = "select new map(" + 
					"ade.id.bulletinId," +
					"st.companyId," +
					"st.stockId," +
					"st.stockName," +
					"bt.analysisTime," +
					"(case when ade.fieldnameQc=-1 then 0 else 1 end)," + 
					"(case when ade.fieldnameQc=-1 then concat(ade.locateInfo, ade.fieldconflict) else ade.fieldnameQc end)," +
					"concat(ade.id.tableType, ',', ade.pdPage))" + 
					" from " +
					"BulletinTable bt," +
					"StockTable st," +
					"AnalysisDetailError ade" +
					" where " +
					"bt.stockId = st.stockId and " +
					"bt.bulletinId = ade.id.bulletinId and " +
					"not bt.analysisTime is null and " + 
					"((not ade.locateInfo='0') or (not ade.fieldconflict='0') or (not ade.fieldnameQc='0')) and " +
					"ade.id.bulletinId like ? and " +
					"bt.stockId like ? and " +
					"(bt.analysisTime between ? and ?)";
			break;
		case ANALYSIS_QC_PROMPTS_AOR:
			queryStr = "select new map(" + 
					"ade.id.bulletinId," +
					"st.companyId," +
					"st.stockId," +
					"st.stockName," +
					"bt.analysisTime," +
					"0," +
					"concat(ade.locateInfo, ade.fieldconflict)," +
					"concat(ade.id.tableType, ',', ade.pdPage))" +
					" from " +
					"BulletinTable bt," +
					"StockTable st," +
					"AnalysisDetailError ade" +
					" where " +
					"bt.stockId = st.stockId and " +
					"bt.bulletinId = ade.id.bulletinId and " +
					"ade.id.bulletinId like ? and " +
					"bt.stockId like ? and " +
					"not bt.analysisTime is null and " + 
					"(bt.analysisTime between ? and ?) and " +
					"?";
			break;
		case ANALYSIS_QC_PROMPTS_CSS:
			queryStr = "select new map(" + 
					"ade.id.bulletinId," +
					"st.companyId," +
					"st.stockId," +
					"st.stockName," +
					"bt.analysisTime," +
					"1," +
					"ade.fieldnameQc," +
					"concat(ade.id.tableType, ',', ade.pdPage))" +
					" from " +
					"BulletinTable bt," +
					"StockTable st," +
					"AnalysisDetailError ade" +
					" where " +
					"bt.stockId = st.stockId and " +
					"bt.bulletinId = ade.id.bulletinId and " +
					"ade.id.bulletinId like ? and " +
					"bt.stockId like ? and " +
					"not bt.analysisTime is null and " + 
					"(bt.analysisTime between ? and ?) and " +
					"?";
			break;
		case COMPANY_LIST:
			queryStr = "select new map(" +
					"st.companyId," +
					"st.stockId," +
					"st.stockName," +
					"st.stockType," +
					"st.exchange)" +
					" from " +
					"StockTable st" +
					" where " +
					"st.stockId like ? and " +
					"st.exchange like ? and " + 
					"st.stockType like ?";
			break;
		case ANALYSIS_QC_LOG:
			
			break;
		case ANALYSIS_LOGS:
			queryStr = "select new map(" + 
					"DATE_FORMAT(al.analyzeTime, '%Y-%m-%d')," +
					"DATE_FORMAT(al.analyzeTime, '%H:%i:%s')," +
					"'PDF文档解析'," +
					"al.status," +
					"concat(st.exchange, '所 | ', bt.bulletinType, ' | ', bt.accountDate))" +
					" from " +
					"AnalysisLog al," +
					"BulletinTable bt," +
					"StockTable st" +
					" where " +
					"al.bulletinId=bt.bulletinId and " +
					"bt.stockId=st.stockId and " +
					"al.status like ? and " +
					"al.analyzeTime between ? and ?";
			break;
		case AFFAIRS_ASSIGN:
			queryStr = "select new map(" +
					"aa.id," +
					"aa.companyId," +
					"aa.docId," +
					"aa.emailAddr) " +
					"from " +
					"AffairsAssign aa " +
					"where " +
					"aa.companyId like ? and " +
					"aa.emailAddr like ?";
			break;
		case QC_RULES:
			queryStr = "select new map(" +
					"qr.qcRuleId," +
					"qr.businessType," +
					"qr.qcType," +
					"qr.qcDescription," +
					"qr.sysDescription) " +
					"from " +
					"QcRule qr where " +
					"qr.businessType like ?";
			break;
		}
		return queryStr;
	}
	
	public static Map getFieldsMapping(int queryType) {
		HashMap<String, String> fieldsMapping = new HashMap<>();
		switch (queryType) {
		case BULLETIN_MISSING:
			fieldsMapping.put("id", "8");
			fieldsMapping.put("com_id", "0");
			fieldsMapping.put("stock_id", "1");
			fieldsMapping.put("com_name", "2");
			fieldsMapping.put("pdf_type", "3");
			fieldsMapping.put("account_date", "4");
			fieldsMapping.put("stock_type", "5");
			fieldsMapping.put("exchange", "6");
			fieldsMapping.put("frequency", "7");
			fieldsMapping.put("state", "--");
			break;
		case DOWNLOAD_QC_LOG:
			fieldsMapping.put("doc_id", "0");
			fieldsMapping.put("company_id", "1");
			fieldsMapping.put("stock_code", "2");
			fieldsMapping.put("company_name", "3");
			fieldsMapping.put("prompte_time", "4");
			fieldsMapping.put("excute_time", "5");
			fieldsMapping.put("error_type", "6");
			fieldsMapping.put("error_details", "7");
			fieldsMapping.put("status", "8");
			break;
		case DOWNLOAD_QC_PROMPTS:
			fieldsMapping.put("bulletin_id", "0");
			fieldsMapping.put("company_id", "1");
			fieldsMapping.put("stock_id", "2");
			fieldsMapping.put("stock_name", "3");
			fieldsMapping.put("prompte_time", "4");
			fieldsMapping.put("error_type", "5");
			fieldsMapping.put("detail_describe", "6");
			fieldsMapping.put("qc_pos", "--");
			break;
		case DOWNLOAD_TASK_LOG:
			fieldsMapping.put("date", "0");
			fieldsMapping.put("time", "1");
			fieldsMapping.put("data_type", "4");
			fieldsMapping.put("status", "2");
			fieldsMapping.put("name", "3");
			break;
		case ANALYSIS_QC_PROMPTS:
		case ANALYSIS_QC_PROMPTS_AOR:
		case ANALYSIS_QC_PROMPTS_CSS:
			fieldsMapping.put("bulletin_id", "0");
			fieldsMapping.put("company_id", "1");
			fieldsMapping.put("stock_id", "2");
			fieldsMapping.put("stock_name", "3");
			fieldsMapping.put("prompte_time", "4");
			fieldsMapping.put("qc_phase", "5");
			fieldsMapping.put("error_type", "6");
			fieldsMapping.put("detail_describe", "--");
			fieldsMapping.put("qc_pos", "7");
			break;
		case COMPANY_LIST:
			fieldsMapping.put("id", "0");
			fieldsMapping.put("stockCode", "1");
			fieldsMapping.put("compName", "2");
			fieldsMapping.put("stockType", "3");
			fieldsMapping.put("exchange", "4");
			break;
		case ANALYSIS_LOGS:
			fieldsMapping.put("date", "0");
			fieldsMapping.put("time", "1");
			fieldsMapping.put("data_type", "2");
			fieldsMapping.put("status", "3");
			fieldsMapping.put("name", "4");
			break;
		case AFFAIRS_ASSIGN:
			fieldsMapping.put("id", "0");
			fieldsMapping.put("emailAddr", "3");
			fieldsMapping.put("companyId", "1");
			fieldsMapping.put("stockCode", "2");
			break;
		case QC_RULES:
			fieldsMapping.put("id", "0");
			fieldsMapping.put("bus_type", "1");
			fieldsMapping.put("qc_type", "2");
			fieldsMapping.put("qcDes", "3");
			fieldsMapping.put("sysDes", "4");
			break;
		}
		
		return fieldsMapping;
	}
}
