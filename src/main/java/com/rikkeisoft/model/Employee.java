package com.rikkeisoft.model;

import java.io.Serializable;
import java.util.List;

public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;

	public Employee() {
		super();
	}
	public Employee(String name, String ip) {
		super();
		this.name = name;
		this.ip = ip;
	}
	
	private String name;
	private String ip;
	private List<ExceptedLink> exceptedLinksList;
	
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
	
	public List<ExceptedLink> getExceptedLinksList() {
		return exceptedLinksList;
	}
	public void setExceptedLinksList(List<ExceptedLink> exceptedLinksList) {
		this.exceptedLinksList = exceptedLinksList;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", ip=" + ip + ", exceptedLinksList=" + exceptedLinksList + "]";
	}
	
	
}
