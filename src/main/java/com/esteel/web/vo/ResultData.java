package com.esteel.web.vo;

public class ResultData {
	private int status;
	private String message;
	private String backStr;
	public ResultData() {
		super();
	}
	public ResultData(int status) {
		super();
		this.status = status;
	}
	public ResultData(int status, String message, String backStr) {
		super();
		this.status = status;
		this.message = message;
		this.backStr = backStr;
	}
	public ResultData(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public ResultData(String message, String backStr) {
		super();
		this.message = message;
		this.backStr = backStr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBackStr() {
		return backStr;
	}
	public void setBackStr(String backStr) {
		this.backStr = backStr;
	}
	@Override
	public String toString() {
		return "ResultDate [status=" + status + ", message=" + message + ", backStr=" + backStr + "]";
	}
	
}
