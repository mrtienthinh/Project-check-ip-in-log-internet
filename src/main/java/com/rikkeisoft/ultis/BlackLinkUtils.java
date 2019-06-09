package com.rikkeisoft.ultis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.rikkeisoft.model.BlackLink;

public class BlackLinkUtils {
	private String pathFile;
	private String nameSheet;
	
	public BlackLinkUtils(String pathFile, String nameSheet) {
		super();
		this.pathFile = pathFile;
		this.nameSheet = nameSheet;
	}
	
	public List<BlackLink> getListBlackLink() throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<BlackLink> blackLinksList = new ArrayList<BlackLink>();
		List<String> domainList = new ArrayList<String>();
		File file = new File(pathFile);
		long dateModify = file.lastModified();
		Workbook workbook = WorkbookFactory.create(file);
		workbook.forEach(sheet -> {
			if (sheet.getSheetName().equals(nameSheet)) {
				sheet.forEach(row -> {
					String domainName = row.getCell(1).getStringCellValue();
					domainList.add(domainName);
//					try {
//						blackLinksList.add(new BlackLink(domainName, DomainUtils.getIpList(domainName)));
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				});
			}
		});
		workbook.close();
		file.setLastModified(dateModify);
		// Create thread
		ThreadBlackLink[] t = new ThreadBlackLink[domainList.size()];
		for (int i = 0; i < domainList.size(); i++) {
			t[i] = new ThreadBlackLink(domainList.get(i));
			t[i].start();
		}
		// Wait all threads;
		for (int i = 0; i < domainList.size(); i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			blackLinksList.add(t[i].getBlackLink());			
		}
		
		return blackLinksList;
	}
}
