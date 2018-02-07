package com.baiwang.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	private static String presuffix;
	private static String postsuffix;
	
	/**
	 * 需要将测试文件复制一份，将需要反写的信息反写到复制的文件中，该文件已时间戳为后缀进行命名
	 * @param fromFile 测试文件
	 * @param toFile   结果文件
	 * */
	public static File copyFile(File fromFile,File toFile) throws IOException{
		if(fromFile.getName().endsWith("xlsx")){
			postsuffix=".xlsx";
		}else{
			postsuffix=".xls";
		}
		String mainname = fromFile.getName().substring(0,fromFile.getName().indexOf("."));
		String resultname = mainname+"_result_"+getSuffix();
		//构造输出文件
		String tofilepath = toFile.getAbsolutePath()+File.separator+resultname+postsuffix;
	//	System.out.println(tofilepath);
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out= new FileOutputStream(tofilepath);
		byte[] b = new byte[1024];
		int n=0;
		while((n=ins.read(b))!=-1){
			out.write(b, 0, n);
		}
		ins.close();
		out.close();
		return  new File(tofilepath);
		
	}
	/**
	 * @return suffix 返回根据当前时间生成的时间戳
	 * 
	 * */
	private static String getSuffix(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String suffix = sdf.format(now);
		return suffix;
	}
	/**
	 * @param 获取文件名后缀
	 * 
	 * */
	
	public static void main(String[] args) throws IOException{
		File fromFile = new File("Excel/eighth.xlsx");
		File toFile   = new File("Result");
		copyFile(fromFile,toFile);
		//System.out.println("1");
		}

}
