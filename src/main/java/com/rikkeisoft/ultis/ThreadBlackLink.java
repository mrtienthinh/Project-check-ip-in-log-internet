package com.rikkeisoft.ultis;

import java.io.IOException;

import com.rikkeisoft.model.BlackLink;

public class ThreadBlackLink extends Thread{
	private BlackLink blackLink;
	private String domainName;
	
	public ThreadBlackLink(String domainName) {
		super();
		this.domainName = domainName;
	}

	@Override
	public void run() {
		try {
			this.setBlackLink(new BlackLink(domainName,DomainUtils.getIpList(domainName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BlackLink getBlackLink() {
		return blackLink;
	}

	public void setBlackLink(BlackLink blackLink) {
		this.blackLink = blackLink;
	}
}
