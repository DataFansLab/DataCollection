/**
 * 
 */
package com.insit.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author zq
 *
 */

@Controller
@RequestMapping("/excelAnalysis")
public class ExcelToJsonController {
	
	@RequestMapping("")
	public String storeFile(@RequestParam("file") CommonsMultipartFile mFile) throws IOException{
		System.out.println("biu biu");
		if (!mFile.isEmpty()) {
			System.out.println("�ϴ��ļ������֣�" + mFile.getOriginalFilename());

			// ��ȡ���ش洢·��
			File file = new File("D:/myfile");
			if (!file.exists()) file.mkdirs();
			String fn = "d:/myfile/" + new Date().getTime() + ".xls";
			File file1 = new File(fn); // �½�һ���ļ�
			try {
				mFile.getFileItem().write(file1); // ���ϴ����ļ�д���½����ļ���
				System.out.println("=======�ļ��ϴ��ɹ�====");				
				excelToJson(1,10,fn);			
			} catch (Exception e) {
				System.out.println("=======�ļ��ϴ�ʧ��====");
				e.printStackTrace();
			}
		}
		return "affairsAssign";
	}
	
	private static Connection getConnection(){
		Connection connSQL = null;
		try {
            Class.forName("com.mysql.jdbc.Driver");
            connSQL = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_collection", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();    
        }
		return connSQL;
	}
	
	private static void excelToJson(int fromRow, int toRow, String file){
		
		if ( checkFileExists(file) ){ //����ļ�·��������		
			try {
				FileInputStream fio = new FileInputStream(file);
				POIFSFileSystem fs = new POIFSFileSystem(fio);
				HSSFWorkbook workBook = new HSSFWorkbook(fs);

				Connection conn = getConnection();	//�������Ӷ���
				if (conn == null) {
					System.out.println("conn is null");
					return;
				}
				else { //��ݿ������ѽ���
					Statement st = conn.createStatement();
					for(int page=0;page<1;page++){
					    HSSFSheet sheet = workBook.getSheetAt(page);
					    Iterator iter = sheet.rowIterator();
				    				   				    
						int lastRowNum = sheet.getLastRowNum();
						toRow = lastRowNum;
						int i=0;						
						if(fromRow<=1) fromRow=1;
						if(fromRow==1)	//����ͷ	
							if (iter.hasNext()){
								iter.next();
								i++;							
						}		

						while (iter.hasNext()) {//�д���						
							if(i<fromRow-1){
								iter.next();
								i++;
							}						
							else if((fromRow-1 <= i) && ( i <toRow ) && (toRow <= lastRowNum )){
								//**********����������***********	
								HSSFRow xslrow = (HSSFRow) iter.next();
								if (xslrow.getCell((short)(0)) == null) return;
								
								String companyId = getStringOfCell(xslrow.getCell((short)0));
								String stockCode = getStringOfCell(xslrow.getCell((short)1));
								String emailAddr = getStringOfCell(xslrow.getCell((short)2));
								
								String sqlStr;
								sqlStr = "INSERT INTO affairs (email_addr,company_id,stock_code) VALUES ("
										 +"'"+emailAddr+"',"+"'"+companyId + "',"+"'"+stockCode +"')";
								System.out.println(sqlStr + ";");
								st.executeUpdate(sqlStr);
								
								String jsonStr = "{companyId:"+"\'"+companyId+"\',"+"stockCode:"+"\'"+stockCode+"\',emailAddr:"+"\'"+emailAddr+"\'}";
								System.out.println(jsonStr);
								i++;
												
							}
							else return;			
						}
					
					}//page
					st.close();          
					conn.close();        
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("wrong filename");
		}
	}
	
	public static boolean checkFileExists(String file){
		return true;
	}
	
	private static String getStringOfCell(HSSFCell cell) {
		String value = null;
		if(cell!= null){
			try{		
			    if(cell.getCellType() == 0) {
				    int num = (int) cell.getNumericCellValue();
				    value = num + "";
			    } else {
			    	value = cell.getStringCellValue();	
			    }  
			    if(value.contains("'")) {
				    value = value.replace("'", "''");
			    }		
			}catch(Exception e) {
				e.printStackTrace();
			}		
		}
		else{
			value="";
		}
		return value;
	}
	
}


