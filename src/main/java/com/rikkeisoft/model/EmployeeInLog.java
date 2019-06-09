package com.rikkeisoft.model;

public class EmployeeInLog {
	private String ip;
	private String website;
	private String timeAccess;
	
	public EmployeeInLog() {
		super();
	}

	public EmployeeInLog(String ip, String website, String timeAccess) {
		super();
		this.ip = ip;
		this.website = website;
		this.timeAccess = timeAccess;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTimeAccess() {
		return timeAccess;
	}

	public void setTimeAccess(String timeAccess) {
		this.timeAccess = timeAccess;
	}

	@Override
	public String toString() {
		return "Employee [ip=" + ip + ", website=" + website + ", timeAccess=" + timeAccess + "]";
	}
	
	
	
	
}
