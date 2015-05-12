/**
 * DataCollection/com.insit.controller/UtilController.java
 * 2014-6-9/上午11:07:32 by nano
 */
package com.insit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insit.util.RPCClient;

/**
 * @author nano
 *
 */


@Controller
@RequestMapping("/util")
public class UtilController {

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
