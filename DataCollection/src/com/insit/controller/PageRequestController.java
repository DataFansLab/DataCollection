/**
 * DataCollection/com.insit.controller/PageRequestController.java
 */
package com.insit.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.insit.util.RPCClient;

/**
 * @author zq
 * 2014-1-8 ����3:31:20
 */

@Controller
@RequestMapping("/page")
public class PageRequestController {
	
	@RequestMapping(value="/index-download")
	public String showDownloadIndexPage() {
		return "index-download";
	}
	
	@RequestMapping(value="/affairsAssign/{phase}")
	public String showAffairsAssignPage(@PathVariable("phase") String phase) {
		return "affairsAssign_" + phase;
	}
	
	@RequestMapping(value="/qcLog/{phase}")
	public String showQCLogPage(@PathVariable("phase") String phase) {
		return "QCLog_" + phase;
	}
	
	@RequestMapping(value="/qcPrompts/{phase}")
	public ModelAndView showQCTipPage(@PathVariable("phase") String phase) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qc_prompts_" + phase);
		String[] fieldsName = null;
		String[] headers = null;
		int[] flex = null;
		if (phase.equals("download")) {
			fieldsName = new String[] {
					"bulletin_id", 
					"company_id", 
					"stock_id", 
					"stock_name", 
					"prompte_time", 
					"error_type", 
					"detail_describe", 
					"qc_pos"
					};
			headers = new String[] {
					"文档ID", 
					"公司ID", 
					"公股票代码", 
					"公司名称", 
					"QC提示时间", 
					"错误类别", 
					"提示描述", 
					"提示位置"
					};
			flex = new int[]{100, 100, 100, 100, 100, 100, 400, 100};
		} else {
			fieldsName = new String[] {
					"bulletin_id", 
					"company_id", 
					"stock_id", 
					"stock_name", 
					"prompte_time", 
					"qc_phase",
					"error_type", 
					"detail_describe", 
					"qc_pos"
					};
			headers = new String[] {
					"文档ID", 
					"公司ID", 
					"公股票代码", 
					"公司名称", 
					"QC提示时间", 
					"QC阶段",
					"错误类别", 
					"提示描述", 
					"提示位置"
					};
			flex = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 300};
		}
		
		JSONArray fields = new JSONArray();
		for (String name : fieldsName) {
			JSONObject item = new JSONObject();
			item.put("name", name);
			fields.add(item);
		}
		
		JSONObject model = new JSONObject();
		model.put("fields", fields);
		model.put("headers", headers);
		model.put("flex", flex);
		
		mav.addObject("model", model);
		mav.addObject("phase", phase);
		
		return mav;
	}
	
	@RequestMapping(value="/upload")
	public String showUploadBulletinPage() {
		return "upload";
	}
	
	@RequestMapping(value="/{phase}/QCRules")
	public String showQCRUlesPage(@PathVariable("phase") String phase) {
		return "QCRules_" + phase;
	}
	
	@RequestMapping(value="/companyList/{phase}")
	public String showCompanyListPage(@PathVariable("phase") String phase) {
		return "companyList_" + phase;
	}
	
	@RequestMapping(value="/companyInfo")
	public String showCompanyInfoPage() {
		return "companyInfo";
	}
	
	@RequestMapping(value="/index-analysis")
	public String showAnalysisIndexPage() {
		return "index-analysis";
	}
	
	@RequestMapping(value="/taskLogs/{phase}")
	public ModelAndView showTaskLogsPage(@PathVariable("phase") String phase) {
		ModelAndView mav = new ModelAndView();
		String[] fieldsName = null;
		JSONArray fields = new JSONArray();
		String[] headers = null;
		
		fieldsName = new String[]{"date", "time", "data_type", "status", "name"};
		for (String name : fieldsName) {
			JSONObject item = new JSONObject();
			item.put("name", name);
			fields.add(item);
		}
		if(phase != null && phase.equals("analysis")) {
			headers = new String[]{
					"日期",
					"时间",
					"数据类别", 
					"解析状态", 
					"数据名称"
					};
			mav.setViewName("task_logs_analysis");
		} else {
			headers = new String[]{
					"日期",
					"时间",
					"数据类别", 
					"下载状态", 
					"数据名称"
					};
			mav.setViewName("task_logs_download");
		}
		int[] flex = {150, 150, 150, 120, 500};
		
		JSONObject model = new JSONObject();
		model.put("fields", fields);
		model.put("headers", headers);
		model.put("flex", flex);
		
		mav.addObject("model", model);
		mav.addObject("phase", phase);
		
		return mav;
	}
	
	@RequestMapping("/bulletinMissing")
	public ModelAndView showBulletinMissingPage() {
		ModelAndView mav = new ModelAndView("bulletin_missing");
		String[] fieldsName = new String[]{
			"id",
			"com_id",
			"stock_id",
			"com_name",
			"pdf_type",
			"account_date",
			"stock_type",
			"exchange",
			"frequency",
			"state"
		};
		JSONArray fields = new JSONArray();
		for (String name : fieldsName) {
			JSONObject item = new JSONObject();
			item.put("name", name);
			fields.add(item);
		}
		String[] headers = new String[]{
			"ID",
			"公司ID",
			"股票代码",
			"公司名称",
			"文档类型",
			"会计结算日期",
			"股票类型",
			"交易所",
			"计算频率（月）",
			"缺失状态"
		};
		int[] flex = {50, 150, 120, 120, 120, 120, 80, 100, 100, 100};
		
		JSONObject model = new JSONObject();
		model.put("fields", fields);
		model.put("headers", headers);
		model.put("flex", flex);
		
		mav.addObject("model", model);
		
		return mav;
	}
	
	@RequestMapping(value="/docAnalysis")
	public String showDocAnalysisPage() {
		return "docAnalysis";
	}

	@RequestMapping("/analysis/taskList")
	public ModelAndView showAnalysisTaskList() {
		ModelAndView mav = new ModelAndView();
		String[] fieldsName = {
				"doc_id",
				"com_id",
				"com_name",
				"doc_type",
				"account_date",
				"analysis_state",
				"analysis_date",
				"aor_link",
				"aor_qc_num",
				"css_link",
				"css_qc_num"
		};
		JSONArray fields = new JSONArray();
		for (String name : fieldsName) {
			JSONObject item = new JSONObject();
			item.put("name", name);
			fields.add(item);
		}
		String[] headers = {
				"文档代码",
				"公司代码",
				"公司名称",
				"文档类型",
				"会计结算日",
				"解析状态",
				"解析日期",
				"文档解析页",
				"文档解析QC",
				"别名匹配页",
				"别名匹配QC"
		};
		mav.setViewName("analysis_task_list");
		int[] flex = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
		
		JSONObject model = new JSONObject();
		model.put("fields", fields);
		model.put("headers", headers);
		model.put("flex", flex);
		mav.addObject("model", model);
		
		return mav;
	}

	@RequestMapping("/downloadProcess")
	public ModelAndView showDownloadProcess() {
		ModelAndView mav = new ModelAndView("downloadProcess");
		String todayState = RPCClient.call("GetTodayDownloadState");
		mav.addObject("todayState", todayState);
		mav.addObject("beforeTodayState", "停止");
		
		return mav;
	}
	
	@RequestMapping("/analysisProcess")
	public ModelAndView showAnalysisProcess() {
		ModelAndView mav = new ModelAndView("analysisProcess");
		String analysisState = RPCClient.call("GetAnalysisState");
		String packageJsonState = RPCClient.call("GetPackageJsonState");
		mav.addObject("analysisState", analysisState);
		mav.addObject("packageJsonState", packageJsonState);
		
		return mav;
	}
	
	@RequestMapping(value="/autoReport")
	public String showAutoReportPage() {
		return "auto-report";
	}
	
	@RequestMapping(value="/reportGenerating")
	public String showreportGeneratingPage() {
		return "report-generating";
	}
}
