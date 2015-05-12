/**
 * DataCollection/com.insit.controller/PDFManagementController.java
 * 2014-6-5/上午10:20:28 by nano
 */
package com.insit.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.dao.impl.BaseHibernateDAO;
import com.insit.service.IQueryService;
import com.insit.service.impl.QueryService;

/**
 * @author nano
 *
 */

@Controller
@RequestMapping("/pdf")
public class PDFManagementController {
	
	private IQueryService queryService = null;
	
	@Autowired
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	@RequestMapping("/open/{docId}")
	public void openBulletinPDFFile(HttpServletResponse response, @PathVariable("docId") String docId) {
		try {
			byte[] pdfData = null;
			String filePath = (queryService.findBulletinFilePath(docId));
			
			File pdfFile = new File(filePath);
			FileInputStream fis = new FileInputStream(pdfFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1)
				bos.write(b, 0, n);
			fis.close();
			bos.close();
			pdfData = bos.toByteArray();
			
			//response.addHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/pdf");
			response.addHeader("content-disposition", "inline;filename=" + docId + ".pdf");
			response.getOutputStream().write(pdfData, 0, pdfData.length);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
