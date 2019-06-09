package com.rikkeisoft.model;

import java.io.Serializable;

public class EmployeeBlack implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String ip;
	private String blackDomain;
	private String accessTime;
	public EmployeeBlack(String name, String ip, String blackDomain, String accessTime) {
		super();
		this.name = name;
		this.ip = ip;
		this.blackDomain = blackDomain;
		this.accessTime = accessTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBlackDomain() {
		return blackDomain;
	}
	public void setBlackDomain(String blackDomain) {
		this.blackDomain = blackDomain;
	}
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	
	
}
