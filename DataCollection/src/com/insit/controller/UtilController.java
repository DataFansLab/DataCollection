/**
 * DataCollection/com.insit.controller/UtilController.java
 * 2014-6-9/上午11:07:32 by nano
 */
package com.insit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bupt.extract.example.MainProcess;

import com.insit.util.RPCClient;

/**
 * @author nano
 *
 */


@Controller
@RequestMapping("/util")
public class UtilController {
	public static String uploadedFilePath = "";
	
	@RequestMapping(value = "/analysisPdf", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String analysisPdf() {
		String response = "";
		if (!uploadedFilePath.equals("")) {
			JSONObject object = new JSONObject();
			//解析公司信息
			try {
	            FileInputStream in = new FileInputStream(new File(uploadedFilePath));
	            PDFParser parser = new PDFParser(in);
	            parser.parse();
	            PDDocument doc = parser.getPDDocument();
	            PDFTextStripper stripper = new PDFTextStripper();
	            stripper.setStartPage(1);
	            stripper.setEndPage(1);
	            String result = stripper.getText(doc);

//	            JSONObject info = new JSONObject();
	            //处理时间
	            if (result.contains("一季度报告"))
	            	object.put("date", "一季度报告");
	            else if (result.contains("三季度报告"))
	            	object.put("date", "三季度报告");
	            else if (result.contains("年度报告"))
	            	object.put("date", "年度报告");
	            else if (result.contains("半年度报告"))
	            	object.put("date", "半年度报告");
	            else object.put("date", "null");
	            //处理股票代码
	            if (result.contains("代码")) {
	                int i = result.indexOf("代码");
	                object.put("code", result.substring(i + 3, i + 9));
	            } else object.put("code", "null");
	            //处理公司名称
	            if (result.contains("有限公司")) {
	                int i = result.indexOf("有限公司");
	                object.put("company", result.substring(result.substring(0, i).lastIndexOf("\n") + 1, i + 2));
	            } else object.put("company", "null");

	            in.close();
	        } catch (JSONException e) {
	            System.out.println("JSONException");
	        } catch (IOException e) {
	            System.out.println("IOException");
	        }
			
			//解析内容
			String[] three_tables = new MainProcess().getPDFFormBulletin(uploadedFilePath);
			
			object.put("fuzhai", JSONObject.fromObject(three_tables[0]));
			object.put("lirun", JSONObject.fromObject(three_tables[1]));
			object.put("xianjin", JSONObject.fromObject(three_tables[2]));
			response = object.toString();
		}
		
		return response;
	}

	@RequestMapping(value = "/downloadProcess/{operation}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String operationsOnDownloadProcess(@PathVariable("operation") String operation) {
		String response;
		if (operation.equals("startToday"))
			response = RPCClient.call("StartTodayDownload");
		else if (operation.equals("stopToday"))
			response = RPCClient.call("StopTodayDownload");
		else return "error";
		
		return response;
	}
	
	@RequestMapping(value = "/analysisProcess/{operation}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String operationsOnAnalysisProcess(@PathVariable("operation") String operation) {
		String response;
		if (operation.equals("startAnalysis"))
			response = RPCClient.call("StartAnalysis");
		else if (operation.equals("stopAnalysis"))
			response = RPCClient.call("StopAnalysis");
		else if (operation.equals("startJSON"))
			response = RPCClient.call("StartPackageJson");
		else if (operation.equals("stopJSON"))
			response = RPCClient.call("StopPackageJson");
		else return "error";
		
		return response;
	}
}
