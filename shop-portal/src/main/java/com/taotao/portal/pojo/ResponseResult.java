package com.taotao.portal.pojo;

public class ResponseResult {
	private String output;
	private String langid;
	private String code;
	private String errors;
	private String time;
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getLangid() {
		return langid;
	}
	public void setLangid(String langid) {
		this.langid = langid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
