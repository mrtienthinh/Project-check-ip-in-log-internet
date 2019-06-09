package com.rikkeisoft.ultis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.rikkeisoft.model.Employee;
import com.rikkeisoft.model.ExceptedLink;

public class ExceptedLinkUtils {
	private String pathName;
	private String nameSheet;
	
	public ExceptedLinkUtils(String pathName, String nameSheet) {
		super();
		this.pathName = pathName;
		this.nameSheet = nameSheet;
	}

	public ExceptedLinkUtils() {
		super();
	}
	
	public List<Employee> addExceptedLinkToEmp(List<Employee> empInfList) throws EncryptedDocumentException, InvalidFormatException, IOException {
		File file = new File(pathName);
		long dataModify = file.lastModified();
		Map<String, String> ipAndExceptedDomain = new HashedMap<>();
		Map<String, List<ExceptedLink>> domainExceptedAndListIp = new HashedMap<>();
		
		Workbook workbook = WorkbookFactory.create(file);
		workbook.forEach(sheet -> {
			if (sheet.getSheetName().equals(nameSheet)) {
				sheet.forEach(row -> {
					String ipEmp = row.getCell(2).getStringCellValue().trim();
					String exceptedDomain = row.getCell(4).getStringCellValue().trim();
					ipAndExceptedDomain.put(ipEmp, exceptedDomain);
				});
			}
		});
		workbook.close();
		file.setLastModified(dataModify);
		
		empInfList.forEach(emp ->{
			if (ipAndExceptedDomain.containsKey(emp.getIp())) {
				String domainExcepted = ipAndExceptedDomain.get(emp.getIp());
				if (!domainExceptedAndListIp.containsKey(domainExcepted)) {
					try {
						domainExceptedAndListIp.put(domainExcepted, getExceptedLinks(domainExcepted));
					} catch (IOException e) {
						e.printStackTrace();
					}
					emp.setExceptedLinksList(domainExceptedAndListIp.get(domainExcepted));
				} else {
					emp.setExceptedLinksList(domainExceptedAndListIp.get(domainExcepted));
				}
			};
		});
		return empInfList;
	}
	
	public List<ExceptedLink> getExceptedLinks(String domainName) throws IOException{
		List<ExceptedLink>  exceptedLinks = new ArrayList<ExceptedLink>();
		if (domainName.contains(", ")) {
			String[] domainSplited = domainName.split(", ");
			for (String domainSingle : domainSplited) {
				if (domainSingle.contains("://")) {
					domainSingle = domainSingle.split("://")[1];
				}
				ExceptedLink exceptedLink = new ExceptedLink();
				exceptedLink.setDomain(domainSingle);
				exceptedLink.setIplist(DomainUtils.getIpList(domainSingle));
				exceptedLinks.add(exceptedLink);
			}
			
		} else {
			if (domainName.contains("://")) {
				domainName = domainName.split("://")[1];
			}
			ExceptedLink exceptedLink = new ExceptedLink();
			exceptedLink.setDomain(domainName);
			exceptedLink.setIplist(DomainUtils.getIpList(domainName));
			exceptedLinks.add(exceptedLink);
		}
		return exceptedLinks;
	}
	
	
	
	
	
}
