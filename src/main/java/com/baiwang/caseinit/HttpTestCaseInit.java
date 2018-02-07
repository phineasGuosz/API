package com.baiwang.caseinit;

import java.util.Map;

import org.apache.log4j.Logger;

import com.baiwang.bean.TestCase;
import com.baiwang.para.Global;
import com.baiwang.util.Parse;

public class HttpTestCaseInit extends TestCaseInit {
	private static Logger logger = Logger.getLogger(HttpTestCaseInit.class.getName());
	@Override
	public void init(TestCase tc){
		Map<String,String> reqpara = tc.getRequestpara();
		if(reqpara!=null){
			for(Map.Entry<String, String> entry:reqpara.entrySet()){
				String key=entry.getKey();
				String value=entry.getValue();
				if(value.startsWith("$")){
					try{
						value = Parse.parse(value);
					}catch(Exception e){
						e.printStackTrace();
					}
					reqpara.put(key,value);
					Global.param.put(key, value);
				}
			}
			tc.setRequestpara(reqpara);
			replacePara(tc);
		}
	}
	private void replacePara(TestCase tc){
		Map<String,String> reqpara=tc.getRequestpara();
		Map<String,String> outpara=tc.getOutpara();
		String baowen=tc.getRequest();
		String url   =tc.getApiurl();
		String checkpoint=tc.getCheckpoint();
		for(String key:reqpara.keySet()){
			if(reqpara.get(key)!=null){
				baowen=baowen.replace("${"+key+"}", reqpara.get(key));
				url=url.replace("${"+key+"}", reqpara.get(key));
				checkpoint=checkpoint.replace("${"+key+"}",reqpara.get(key));
				if(outpara!=null){
					for(String s:outpara.keySet()){
						//上个请求参数如果需要输出，就在全局变量里找，然后放进输出参数 引用规则${}
						if(("${"+key+"}").equals(outpara.get(s))){
							outpara.put(s,outpara.get(key));
						}
					}
				}
			}else{
				logger.info("从全局变量中获取："+key+" 失败");
			}
		}
		
		tc.setRequest(baowen);
		tc.setApiurl(url);
		tc.setCheckpoint(checkpoint);
	}

}
