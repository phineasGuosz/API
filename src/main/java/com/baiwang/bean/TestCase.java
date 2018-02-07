package com.baiwang.bean;

import java.util.Map;

public class TestCase {
	//是否运行
	private String casestatus;
	//案例编号
	private String caseid;
	//案例名称
	private String casename;
	//接口名称
	private String apiname;
	
	//服务代码
	private String casecode;
	//请求类型
	private String reqtype;
	//请求地址
	private String apiurl;
	//请求报文
	private String request;
	//请求报文
	private String response;
	//接口参数
	private Map<String,String> requestpara;
	//断言设置
	private String checkpoint;
	//输出参数
	private Map<String,String> outpara;
	//响应时间
	private String responsetime;
	//运行结果
	private String result;
	
	
	public String getCasestatus() {
		return casestatus;
	}
	public void setCasestatus(String casestatus) {
		this.casestatus = casestatus;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public String getCasecode() {
		return casecode;
	}
	public void setCasecode(String casecode) {
		this.casecode = casecode;
	}
	public String getReqtype() {
		return reqtype;
	}
	public void setReqtype(String reqtype) {
		this.reqtype = reqtype;
	}
	public String getApiurl() {
		return apiurl;
	}
	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Map<String, String> getRequestpara() {
		return requestpara;
	}
	public void setRequestpara(Map<String, String> requestpara) {
		this.requestpara = requestpara;
	}
	public String getCheckpoint() {
		return checkpoint;
	}
	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}
	public Map<String, String> getOutpara() {
		return outpara;
	}
	public void setOutpara(Map<String, String> outpara) {
		this.outpara = outpara;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getApiname() {
		return apiname;
	}
	public void setApiname(String apiname) {
		this.apiname = apiname;
	}
	
	
	

}
