package com.baiwang.caserun;

import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baiwang.baiwangcloud.client.BaiwangCouldAPIClient;
import com.baiwang.bean.TestCase;
import com.baiwang.para.Global;
import com.baiwang.util.Parse;


public class SDKTestCaseRun extends TestCaseRun{
	private String url;
	private String baowen;
	private String nsrsbh;
	private String jrdm;
	private String checkpoint;
	private BaiwangCouldAPIClient client;
	private String response;
	private String result;
	private Map<String,String> outpara;
	private static Logger logger = Logger.getLogger(SDKTestCaseRun.class.getName());
	@Override
	public void run(TestCase tc){
		if(beforeTest(tc)){
			try{
				baowen=tc.getRequest();
				long startTime = System.currentTimeMillis();
				//System.out.println(baowen);
				response=client.rpc(baowen);
				//System.out.println(response);
				client.logout();
				long endTime = System.currentTimeMillis();
				tc.setResponsetime(String.valueOf(endTime - startTime)+"ms");
				tc.setResponse(response);
				if(response.contains(checkpoint)){
					tc.setResult("Pass");
				}else{
					tc.setResult("Fail");
				}
				afterTest(tc);
			}catch(Exception e){
				e.printStackTrace();
				tc.setResponse("百望云接口调用异常："+e.toString());
				tc.setResult("Exception");
			}
		}else{
			logger.info("百望云接口登录失败");
			tc.setResponse("百望云接口登陆失败"+response);
			tc.setResult("Exception");
		}
	}
	private boolean beforeTest(TestCase tc){
		boolean flag=false;
		try{//每个用例的接口参数都已全局变量进行整合，已接口参数为主，如果接口参数中没有那么以全局变量中的为准
			url=tc.getApiurl();
			//System.out.println(url);
			if(tc.getRequestpara().containsKey("NSRSBH")){
				nsrsbh=tc.getRequestpara().get("NSRSBH");
			}else{
				nsrsbh=Global.param.get("NSRSBH");
			}
			
			//System.out.println(nsrsbh+"-----");
			if(tc.getRequestpara().containsKey("JRDM")){
				jrdm=tc.getRequestpara().get("JRDM");
			}else{
				jrdm=Global.param.get("JRDM");
			}
			client=new BaiwangCouldAPIClient();
			checkpoint=tc.getCheckpoint();
			outpara=tc.getOutpara();
		//	System.out.println(jrdm+"=====");
			//client.login("http://123.56.92.221/api/service/bwapi", "500102020170810", "1234567890abc");
			client.login(url, nsrsbh, jrdm);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			response = e.toString();
		}
		return flag;
	}
	private void afterTest(TestCase tc){
		try{
			if(outpara!=null){
				for(Map.Entry<String, String> entry:outpara.entrySet()){
					String key=entry.getKey();
					String value=entry.getValue();//$set{<fphm>(.*?)</fphm>}
				//	System.out.println(key+value+"guoshenzhen");
					if(value.startsWith("$")){
						//变量名形式为${set,fphm}  value=${set,fphm}
						//fpdm=$set{<fphm>(.*?)</fphm>}
						//re=<fphm>(.*?)</fphm>
						String re = value.substring(value.indexOf("{")+1,value.indexOf("}"));
					//	System.out.println(re);
						//String pattern="<"+re+">"+"(\\d+)(.*)";
						Pattern p = Pattern.compile(re);
						Matcher m = p.matcher(response);
					//	System.out.println(m.find()+"9999999999"+m.group(1));
						if(m.find()){
							String val = m.group(1);
							//System.out.println(val+"00000000000");  使用sys打印的时候要关闭，否则打印会改变变量的值
							value = value.replace(re,key+","+val);
							//System.out.println(value+"1111111111");
						}
						value = Parse.parse(value);//$set{key,val}
					}else{
						Global.param.put(key, value);
					}
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}
			
		
	
	

	
