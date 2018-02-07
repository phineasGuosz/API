package com.baiwang.apitest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.baiwang.caseinit.*;
import com.baiwang.caserun.*;
import com.baiwang.bean.TestCase;
import com.baiwang.load.LoadTestCase;
import com.baiwang.util.ExcelUtil;
import com.baiwang.util.FileUtil;
import com.baiwang.util.Mail;

public class Client {
	
	public void run(String testFilePath,String reslutFilePath) throws Exception{
		//System.out.println(testFilePath+"&&&&&&&"+reslutFilePath);
		String reMessage="";
		//行号，从第三行开始反写，索引从0开始
		int count=3;
		int success = 0;
 		int fail = 0;
 		int skip = 0;
 		int exception = 0;
 		int num = 0;
 		File[] filelist  ;
 		TestCaseInit ti = null;
		TestCaseRun tr = null;
		File testFile=new File(testFilePath);
		File resultFile=new File(reslutFilePath);
		
	//	FileUtil.copyFile(testFile, resultFile);
//		System.out.println("***********"+testFile.getAbsolutePath());
//		System.out.println("***********"+resultFile.getAbsolutePath());
		
		List<TestCase> caseList = LoadTestCase.loader(testFile);
		
		File file = FileUtil.copyFile(testFile, resultFile);
		/**
		 * 执行用例的时候先去除前三行
		 * */
		/*caseList.remove(0);
		caseList.remove(1);
		caseList.remove(2);*/
		int error = 0;
		String s = "";
		String fMessage = "";
		for(TestCase tc:caseList){
				if(tc.getCasestatus().equals("NO")){
					tc.setResponse("此用例跳过");
					tc.setResult("Skip");
					num++;
					skip++;
					continue;
				}
				
				if(tc.getReqtype().equals("SDK")){
					 ti = new HttpTestCaseInit();
					 tr = new SDKTestCaseRun();
				}
				
		//	System.out.println(tc.getApiname());
			ti.init(tc);
    		tr.run(tc);
    		
    	//	System.out.println(file.getAbsolutePath());
    		
    		ExcelUtil.writeExcel2007(file,count , 11, tc.getRequest());
    		ExcelUtil.writeExcel2007(file,count , 12, tc.getResponse());
    		ExcelUtil.writeExcel2007(file,count , 13, tc.getResponsetime());
    		ExcelUtil.writeExcel2007(file,count , 14, tc.getResult());
    		count++;
    		
    		num++;
            if(tc.getResult().equals("Pass")){
             	success++;
             }
            if(tc.getResult().equals("Failure")){
             	fail++;
             	fMessage+="*"+tc.getApiname()+"*"+tc.getCasename()+"*调用失败;";
             	error++;
             }
            if(tc.getResult().equals("Skip")){
             	skip++;
             	fMessage+="*"+tc.getApiname()+"*"+tc.getCasename()+"*调用异常;";
              }
            if(tc.getResult().equals("Exception")){
             	exception++;
             	error++;
             }
			}
		
		//发送邮件，将结果邮件发送到收件人邮箱中
	//	filelist[0]=file;
		//读取收件人
		String test="Excel/tenth.xlsx";
		File file1 = new File(test);
	//	List<List<String>> li=readExcel(file);
		List<List<String>> rowList = ExcelUtil.readExcel(file1);
	//	System.out.println(rowList.size());
	//	List<TestCase> caseList = new ArrayList<TestCase>();
	//	String global = rowList.get(1).get(1);
		Mail mail = new Mail();
		//String[] cc = getCopyTo(rowList.get(1).get(1));
		mail.send(file);
		System.out.println("已成功执行");
		
	}
	public static String[] getCopyTo(String cc){
		String[] copy=cc.split("\n");
		return copy;
	}
}
