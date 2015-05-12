/**
 * DataCollection/com.insit.service/QueryService.java
 * 2014-3-27/下午5:27:51 by nano
 */
package com.insit.service;


/**
 * @author nano
 *
 */

public interface IQueryService {
	
	public String bulletinMissingQuery(String params, int start, int limit);
	public String uploadBulletinQuery(String params,int start, int limit);
	public String downloadQcLogQuery(String params, int start, int limit);
	public String downloadQCPromptsQuery(String params,int start,int limit);

	public String downloadTaskLogsQuery(String params,int start,int limit);
	public String companyListQuery(String params,int start,int limit);

	public String analysisTaskListQuery(String params,int start,int limit);
	public String analysisQcPromptsQuery(String params,int start,int limit);
	public String analysisLogsQuery(String params,int start,int limit);

	public String findBulletinFilePath(String docId);
	
	public String affairsAssignQuery(String emailAddr,String stockCode, int start, int limit);
	public String qcRulesQuery(String type,int start,int limit);
	public String companyInfoQuery(String stockId, String catagory);
	public String downloadHomeQuery(String status);
	public String exchangeWeeklyQuery();
	public String homeMissingQuery();
	public String analysisHomeQuery(String status);
}
