package com.baiwang.load;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baiwang.bean.TestCase;
import com.baiwang.para.Global;
import com.baiwang.util.ExcelUtil;

public class LoadTestCase {
	public static List<TestCase> loader(File file)throws Exception{
		List<List<String>> rowList = ExcelUtil.readExcel(file);
		List<TestCase> caseList = new ArrayList<TestCase>();
		String global = rowList.get(1).get(1);
		if(!global.equals("")){
			String[] array=global.split("\n");
			for(String str:array){
				String[] strArry = str.split("=");
				Global.param.put(strArry[0],strArry[1]);
			}
		}
		//将excel转换成testcase
		for(List<String> celllist:rowList){
			if(!(celllist.get(0).equals("发送邮件至")||celllist.get(0).equals("全局变量")||celllist.get(0).equals("是否运行")||celllist.get(0).equals("NO"))){
				TestCase tc= new TestCase();
				tc.setCasestatus(celllist.get(0));
				tc.setCaseid(celllist.get(1));
				tc.setCasename(celllist.get(2));
				tc.setApiname(celllist.get(3));
				tc.setCasecode(celllist.get(4));
				tc.setReqtype(celllist.get(5));
				tc.setApiurl(celllist.get(6));
				tc.setRequest(celllist.get(7));
				
				//将接口输出参数的值转换成map，将全局变量map进行循环，如果存在就跳出，如果不存在就放进接口输出参数中
				//即相同的变量以接口输出参数为准，每次都会加载所以接口输出参数是全局与接口输出参数的并集
				
				if(!(celllist.get(8).equals(""))){
					
					String value = celllist.get(8);
				//	System.out.println(value);
					Map<String,String> map=getMap(value);
					/*for(Map.Entry<String, String> entry:map.entrySet()){
						System.out.println(entry.getKey()+"11111111"+entry.getValue());
					}
					for(Map.Entry<String, String> entry:Global.param.entrySet()){
						System.out.println(entry.getKey()+"22222222"+entry.getValue()+"22222222222"+Global.param.size());
					}*/
					//System.out.println(value);
					if(Global.param.size()!=0){
						for(Map.Entry<String, String> entry:Global.param.entrySet()){
							String key=entry.getKey();
							String var=entry.getValue();
							if(map.containsKey(key)){
								continue;
							}else{
								map.put(key, var);
							}
							
						}
						
					}
					/*for(Map.Entry<String, String> entry:map.entrySet()){
						System.out.println(entry.getKey()+"33333333"+entry.getValue());
					}*/
					tc.setRequestpara(map);
				}
				//System.out.println(celllist.get(9).toString());
				tc.setCheckpoint(celllist.get(9));
				try{
				//System.out.println(celllist.get(10).toString());
				if(!celllist.get(10).equals("")){
					tc.setOutpara(getMap(celllist.get(10)));
				}}catch(Exception e){
					tc.setOutpara(null);
					e.printStackTrace();
				}
				caseList.add(tc);
				
			}
		}
		return caseList;
		
	}
	
	public static Map<String,String> getMap(String value){
		Map<String,String> map = new HashMap<String,String>();
		
		String[] arr = value.split("\n");
		for(String str:arr){
			System.out.println(str);
			String[] strArry = str.split("=");
			map.put(strArry[0],strArry[1]);
		}
		return map;
	}
}
