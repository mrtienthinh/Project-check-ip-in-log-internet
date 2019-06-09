package com.rikkeisoft.model;

import java.io.Serializable;
import java.util.List;

public class ExceptedLink implements Serializable{
	private static final long serialVersionUID = 1L;
	private String domain;
	private List<String> iplist;
	
	public ExceptedLink(String domain, List<String> iplist) {
		super();
		this.domain = domain;
		this.iplist = iplist;
	}

	public ExceptedLink() {
		super();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<String> getIplist() {
		return iplist;
	}

	public void setIplist(List<String> iplist) {
		this.iplist = iplist;
	}

	@Override
	public String toString() {
		return "LinkExcepted [domain=" + domain + ", iplist=" + iplist + "]";
	}
	
	
}
