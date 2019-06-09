package com.rikkeisoft.model;

import java.io.Serializable;
import java.util.List;

public class BlackLink implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private List<String> ipList;
	
	public BlackLink(String name, List<String> ipList) {
		super();
		this.name = name;
		this.ipList = ipList;
	}

	public BlackLink() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}

	@Override
	public String toString() {
		return "BlackListLink [name=" + name + ", ipList=" + ipList + "]";
	}
}
