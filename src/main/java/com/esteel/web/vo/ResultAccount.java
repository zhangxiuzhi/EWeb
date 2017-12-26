package com.esteel.web.vo;

public class ResultAccount {
	private int indexId;
	private int account;
	private int name;
	private int department;
	private int job;
	private int email;
	public ResultAccount() {
		super();
	}
	public ResultAccount(int indexId, int account, int name, int department, int job, int email) {
		super();
		this.indexId = indexId;
		this.account = account;
		this.name = name;
		this.department = department;
		this.job = job;
		this.email = email;
	}
	@Override
	public String toString() {
		return "ResultAccount [indexId=" + indexId + ", account=" + account + ", name=" + name + ", department="
				+ department + ", job=" + job + ", email=" + email + "]";
	}
	public int getIndexId() {
		return indexId;
	}
	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	public int getEmail() {
		return email;
	}
	public void setEmail(int email) {
		this.email = email;
	}
	
	
}
