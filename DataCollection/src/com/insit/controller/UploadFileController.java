/**
 * DataCollection/com.insit.controller/UploadFileController.java
 * 2014-4-16/上午10:19:31 by Administrator
 */
package com.insit.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.insit.dao.IBaseHibernateDAO;
import com.insit.model.BulletinTable;
import com.insit.util.StringOperate;

/**
 * @author zq
 *
 */
@Controller
@RequestMapping("/uploadFile")
public class UploadFileController {
	private IBaseHibernateDAO dao = null;
	//private String PDF_PATH = "/disk3/pdf";
	private String PDF_PATH = "/D/pdf2";


	@Autowired
	public void setDAO(IBaseHibernateDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping(value="/tmp")
	public @ResponseBody String uploadPdfFile(@RequestParam("file") MultipartFile file){
		String result = "";
		if (!file.isEmpty()) {
			try {
                byte[] bytes = file.getBytes();
                String fileName = "F:\\pdf_upload\\" + System.currentTimeMillis() + ".pdf";
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                stream.write(bytes);
                stream.close();
                result = "yes!";
                UtilController.uploadedFilePath = fileName;
            } catch (Exception e) {
            	result = "no! => " + e.getMessage();
            }
		}
		return result;
	}
	
	@RequestMapping(value="/pdf")
	public @ResponseBody String HandleUploadPdfFile(@RequestParam("bulletin_id") String bulletinId,@RequestParam("bulletin") CommonsMultipartFile mFile){
		String SUCCESS_FLAG = "true";
		if (!mFile.isEmpty()) {
			BulletinTable bt = (BulletinTable) dao.findById(BulletinTable.class, bulletinId);
			System.out.println("bt:"+bt);
			String origName = mFile.getOriginalFilename();
			String fileName = origName.substring(0, origName.lastIndexOf("."));
			String hql = "update BulletinTable bt set bt.downloadStatus = 2 where bt.bulletinId = '" + bulletinId +"'" ;
			dao.executeUpdate(hql);
			hql = "update BulletinTable bt set bt.path = '" +PDF_PATH + "/" + fileName + ".pdf" + "' where bt.bulletinId = '" + bulletinId +"'" ;
			dao.executeUpdate(hql);
			
			UploadAction(mFile,fileName);
			
		}
		return "{success:"+SUCCESS_FLAG+"}";
	}
	
	@RequestMapping(value = "/bulletinDetails",produces = "text/html;charset=UTF-8")
	public @ResponseBody String HandleUploadBulletinDetails(
			@RequestParam("paramsList") String bulletin,
			@RequestParam("bulletin") CommonsMultipartFile mFile) throws ParseException, IOException{
		String SUCCESS_FLAG = "true";
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" ); 
		
		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(bulletin);

		BulletinTable bt = new BulletinTable();
		bt.setStockId(jsonObj.getString("stock_id"));		
		bt.setBulletinName(StringOperate.unicodeToString(jsonObj.getString("bulletin_name")));
		bt.setBulletinType(StringOperate.unicodeToString(jsonObj.getString("bulletin_type")));
		bt.setAccountDate(sdf.parse(mappingAccountDate(jsonObj.getString("bulletin_year"),jsonObj.getString("bulletin_type"))));
		bt.setReleaseDate(sdf.parse(jsonObj.getString("release_date")));
		bt.setBulletinYear(jsonObj.getString("bulletin_year"));
		bt.setDownloadDate(java.util.Calendar.getInstance().getTime());
		bt.setUrl(jsonObj.getString("url"));
		bt.setDownloadStatus((short)2);
		bt.setAnalysisStatus((short)0);
		
		PDF_PATH += "/" + jsonObj.getString("stock_id") + "/" + jsonObj.getString("bulletin_year");
		if(!jsonObj.getString("url").equals("")){
			String urlStr = jsonObj.getString("url");
			String fileName = urlStr.substring((urlStr.lastIndexOf("/")+1), urlStr.lastIndexOf("."));
			bt.setBulletinId("DGP" +  fileName);
			bt.setPath(PDF_PATH + "/" + fileName + ".pdf");
			downloadByUrl(urlStr,fileName);
		}	
		else if(!mFile.isEmpty()){
			String origName = mFile.getOriginalFilename();
			String fileName = origName.substring(0, origName.lastIndexOf("."));
			bt.setBulletinId("DGP" + fileName);
			bt.setPath(PDF_PATH + "/" + fileName + ".pdf");
			UploadAction(mFile,fileName);
		}
		dao.save(bt);
		try{
			String hql = "delete from MissingBulletin mb where mb.stockId = '" + jsonObj.getString("stock_id") +"' " +
					"and mb.accountDate = '" + mappingAccountDate(jsonObj.getString("bulletin_year"),jsonObj.getString("bulletin_type")) + "'" ;
			dao.executeUpdate(hql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "{success:"+SUCCESS_FLAG+"}";
	}
	
	private boolean UploadAction(CommonsMultipartFile mFile,String fileName){
		File file = new File(PDF_PATH);
		if (!file.exists()) file.mkdirs();
		String fn = PDF_PATH + "/" + fileName + ".pdf";
		System.out.println(fn);
		File file1 = new File(fn); 
		try {
			mFile.getFileItem().write(file1); 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void downloadByUrl(String urlStr,String fileName) throws IOException {
		int bytesum = 0;
		int byteread = 0;
		URL url = new URL(urlStr);
		
		try {
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream inStream = conn.getInputStream();
			conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(PDF_PATH + "/" + fileName + ".pdf");
			byte[] buffer = new byte[1204*100];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
			}
			fs.close();
			inStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String mappingAccountDate(String bulletinYear, String type){
		String accountDate = "";
		String date = "";
		switch(type)
		{
			case "第一季度季报": date = "-03-31"; break;
			case "半年报": date = "-06-30"; break;
			case "第三季度季报": date = "-09-30"; break;
			case "年报": date = "-12-31"; break;
		}
		accountDate = bulletinYear + date; 
		return accountDate;
	}
	
	
}
