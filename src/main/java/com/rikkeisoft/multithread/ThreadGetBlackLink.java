package com.rikkeisoft.multithread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.rikkeisoft.model.BlackLink;
import com.rikkeisoft.ultis.BlackLinkUtils;

public class ThreadGetBlackLink implements Runnable {
	public final String FILE_CONTAIN_BLACK_WHITE_LINKS = "DsDuAn.xlsx";
	public final String SHEET_CONTAIN_BLACK_LINKS = "Ten_trang_cam";
	public final String FILE_LIST_BLACK_OBJECT = "blackLink.dat";

	private List<BlackLink> blackLinks = null;

	public List<BlackLink> getBlackLinks() {
		return blackLinks;
	}

	public void setBlackLinks(List<BlackLink> blackLinks) {
		this.blackLinks = blackLinks;
	}

	@Override
	public void run() {
		System.out.println("Run: Thread get black link update file and crawle data from internet");
		// Load black list;
		BlackLinkUtils blackLinkUtils = new BlackLinkUtils(FILE_CONTAIN_BLACK_WHITE_LINKS, SHEET_CONTAIN_BLACK_LINKS);
		try {
			blackLinks = blackLinkUtils.getListBlackLink();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		ObjectOutputStream outObject = null;
		// Write object to file;
		try {
			outObject = new ObjectOutputStream(new FileOutputStream(FILE_LIST_BLACK_OBJECT));
			outObject.writeObject(blackLinks);
			outObject.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End: Thread get black link update file and crawle data from internet");
	}
}
