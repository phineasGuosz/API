package com.baiwang.function;

import java.util.logging.Logger;

import com.baiwang.para.Global;

public class Function {
	private static Logger logger = Logger.getLogger(Function.class.getName());
	/**
	 * @param num 指定随机数位数
	 * @return String 返回生成的随机数
	 * */
	public String getRandomNum(String num){
		int num2 = Integer.valueOf(num);
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<num2;i++){
			int a = (int)(Math.random()*10);
			sb.append(a);
		}
		return sb.toString();
	}
	/**
	 * @param value 取到的值均为key,value形式
	 * @retrun 返回成功或者失败
	 * */
	public String set(String value){
		String[] args=value.split(",");
		if(args.length==1){
			logger.info("将变量"+args[0]+"放入全局变量失败");
			return "failure";
		}else{
			Global.param.put(args[0], args[1]);
			return "success";
		}
	}
	/**
	 * @para key,用于方法映射时调用get方法，从全局变量中取值
	 * @return 返回要取得全局变量
	 * */
	public String get(String key){
		return Global.param.get(key);
	}
}
