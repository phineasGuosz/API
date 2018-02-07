package com.baiwang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.baiwang.bean.TestCase;

public class ExcelUtil {
	private static Logger logger = Logger.getLogger(ExcelUtil.class.getName());
	public static void main(String[] args) throws Exception{
		String testFile="D:\\workspacesold\\apitest\\Excel\\second.xlsx";
		File file = new File(testFile);
	//	List<List<String>> li=readExcel(file);
		List<List<String>> rowList = ExcelUtil.readExcel(file);
	//	System.out.println(rowList.size());
	//	List<TestCase> caseList = new ArrayList<TestCase>();
	//	String global = rowList.get(1).get(1);
		//String output = rowList.get(3).get(10);
		//System.out.println(global +"======="+output);
		/*for(List<String> a:li){
			for(String b:a){
				System.out.println(b);
			}
		}*/
	}
	//读取excel
	public static List<List<String>> readExcel(File file) throws Exception{
		List<List<String>> li = new ArrayList<List<String>>();;
		String filename = file.getName();
		//设置文件名后缀，判断是xls还是xlsx,为了防止出现空指针赋初值""
		String suffix="";
		suffix=filename.substring(filename.lastIndexOf(".") + 1);
		if(suffix != ""){
			if(suffix.equals("xls")){
				li = readExcel2003(file);
			}else{
				li = readExcel2007(file);
			}
		}else{
			logger.info("测试文件不是excel文件");
		}
		return li;
	} 
	/**
	 * @param 测试案例
	 * @return 将列值装入list中，每一行为一个testcase，再将list装入list中即所有的testcase装入一个List中，可以循环测试
	 * @throws IOException 
	 * 
	 * */
	private static List<List<String>> readExcel2003(File file) throws IOException{
		//取出每行值，每一个行为一个List<String>
		List<String> row = new ArrayList<String>();
		//取出的每行值装入allrows中代表取出所有的测试用例
		List<List<String>> allrows = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet    hssfsheet= workbook.getSheetAt(0);
		int rowsize= hssfsheet.getLastRowNum()+1;
		
		try{
			
			//从第三行开始（索引为0），第0行为邮件收发，第1行为全局变量，第2行为列名，单取值
			for(int j=0;j<rowsize;j++){
				HSSFRow rowlist = hssfsheet.getRow(j);
				if(rowlist==null){
					continue;
				}
				int cellsize=rowlist.getLastCellNum();
				for(int i=0;i<cellsize;i++){
					String value="";
					HSSFCell cell = rowlist.getCell(i);
					if(cell!=null){
						value = cell.toString();
					}
					row.add(value);
				}
				allrows.add(row);
			}
			return allrows;
		}catch(Exception e){
			logger.info("读取测试文件错误");
		}finally{
			if(workbook != null){
				workbook.close();
			}
			if(is != null){
				is.close();
			}
		}
		return null;
	}
	/**
	 * @param 测试案例
	 * @return 将列值装入list中，每一行为一个testcase，再将list装入list中即所有的testcase装入一个List中，可以循环测试
	 * @throws IOException 
	 * 
	 * */
	private static List<List<String>> readExcel2007(File file) throws IOException{
		//取出每行值，每一个行为一个List<String>
		//List<String> rowlist = new ArrayList<String>();
		//取出的每行值装入allrows中代表取出所有的测试用例
		List<List<String>> allrows = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		XSSFSheet    sheet= workbook.getSheetAt(0);
		//定义row,cell
		XSSFRow row;
		String cell;
		//getFirstRowNum  返回存在值的第一行的行号按索引数从0开始
		//getLastRowNum   返回存在值的最后一行的行号按索引数从0开始
		//getPhysicalNumberOfRows 返回存在值的所有行数按索引数从1开始，即实际行数。
	//	System.out.println(sheet.getFirstRowNum()+"============="+sheet.getLastRowNum()+"=============="+sheet.getPhysicalNumberOfRows());
		try{
			for(int i=0;i<=sheet.getLastRowNum();i++){
				List<String> rowlist = new ArrayList<String>();
				
				XSSFRow testrow=sheet.getRow(0);
			//	System.out.println(testrow.getPhysicalNumberOfCells()+"===="+testrow.getLastCellNum());
				
				
				
				
				row=sheet.getRow(i);
				for(int j=0;j<(row.getLastCellNum());j++){
					
					//getFirstCellNum  返回存在值的第一个单元格，索引从0开始
				//getLastCellNum   返回存在值的最后一个单元格，索引从1开始
				//getPhysicalNumberOfCells 返回存在值的最后一个单元格的索引n，以及空白单元格的索引m的差n-m，索引从1开始
				//	System.out.println(row.getFirstCellNum()+"++++++++++++"+row.getLastCellNum()+"++++++++++++"+row.getPhysicalNumberOfCells());
					
					cell=row.getCell(j).toString();
				//	System.out.println(cell);
					if(cell!=null){
					
					rowlist.add(cell);
					}
						
				}
				allrows.add(rowlist);
				//rowlist.clear();
			}
			
			return allrows;
		}catch(Exception e){
			logger.info("failure");;
		}finally{
			if(workbook != null){
				workbook.close();
			}
			if(is != null){
				is.close();
			}
		}
		return null;
	}
	/**
	 * @param File 返写结果的文件
	 * @param rownum 要写入的行数
	 * @throws IOException 
	 * @para  clonum 要写入的列数
	 * */
	public static void writeExcel2007(File file,int rownum,int colnum,String value) throws IOException{
		InputStream is = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		XSSFSheet    sheet= workbook.getSheetAt(0);
		XSSFRow      row  = sheet.getRow(rownum);
		XSSFCell     cell = row.createCell(colnum);
		//设置单元格格式
		cell.setCellType(CellType.STRING);
		
		//设置单元格文本值
		cell.setCellValue(value);
		//向文件中写入，否则文件中不会有写入的值
		FileOutputStream os = new FileOutputStream(file);
		workbook.write(os);
		workbook.close();
		os.close();
	} 
	public static void writeExcel2003(File file,int rownum,int colnum,String value) throws IOException{
		InputStream is = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet    sheet= workbook.getSheetAt(0);
		HSSFRow      row  = sheet.getRow(rownum);
		HSSFCell     cell = row.createCell(colnum);
		//设置单元格格式
		cell.setCellType(CellType.STRING);
		
		//设置单元格文本值
		cell.setCellValue(value);
		//向文件中写入，否则文件中不会有写入的值
		FileOutputStream os = new FileOutputStream(file);
		workbook.write(os);
		workbook.close();
		os.close();
	}
	//测试
	/*public static void main(String[] args) throws IOException{
		//String separator = java.io.File.separator;
		//System.out.println(separator);
		String filepath="C:\\Users\\lenovo\\Desktop\\test.xlsx";
		File file = new File(filepath);
		List<List<String>> test=readExcel2007(file);
	//	writeExcel2007(file,3,9,"a");
	//	System.out.println("test");
		for(List<String> rowlist:test){
			for(String collist:rowlist){
		//		System.out.println(collist);
			}
		}
	}*/
}
