package com.baiwang.util;

import java.lang.reflect.Method;

import com.baiwang.function.Function;

public class Parse {
	/**
	 * @para 具体引用为$set{} $get{} $Random()
	 * @return 返回映射到这些函数返回的值
	 * */
	public static String parse(String value) throws Exception{
		String functionName=value.substring(1, value.indexOf("{"));
		String para=value.substring(value.indexOf("{")+1,value.indexOf("}"));
		Class<?> c = Function.class;
		Method m = c.getMethod(functionName, String.class);
		Object ins = c.newInstance();
		String result = (String)m.invoke(ins, para);
		return result;
		
		
	}
}
