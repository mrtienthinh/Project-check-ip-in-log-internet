package com.rikkeisoft.multithread;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.rikkeisoft.model.BlackLink;

public class ThreadReadBlackLink implements Runnable {
	public final String FILE_LIST_BLACK_OBJECT = "blackLink.dat";

	final static Logger logger = Logger.getLogger(ThreadReadBlackLink.class);

	private List<BlackLink> blackLinks = null;

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		logger.info("Run: Thread get black links infomation from loacal file!");
		ObjectInputStream inputObject;
		try {
			inputObject = new ObjectInputStream(new FileInputStream(FILE_LIST_BLACK_OBJECT));
			setBlackLinks((List<BlackLink>) inputObject.readObject());
			inputObject.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		logger.info("End: Thread get black links infomation from loacal file!");
	}

	public List<BlackLink> getBlackLinks() {
		return blackLinks;
	}

	public void setBlackLinks(List<BlackLink> blackLinks) {
		this.blackLinks = blackLinks;
	}

}
