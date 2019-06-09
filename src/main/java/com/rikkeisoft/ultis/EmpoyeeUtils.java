package com.rikkeisoft.ultis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.rikkeisoft.model.Employee;

public class EmpoyeeUtils {
	private String pathFile;
	private String nameSheet;
	
	public EmpoyeeUtils(String pathFile, String nameSheet) {
		super();
		this.pathFile = pathFile;
		this.nameSheet = nameSheet;
	}

	public EmpoyeeUtils() {
		super();
	}
	
	public List<Employee> getListEmpInfo() throws EncryptedDocumentException, InvalidFormatException, IOException{
		File file = new File(pathFile);
		long dateModify = file.lastModified();
		Workbook workbook = WorkbookFactory.create(file);
		List<Employee> empInfoList = new ArrayList<>();
		
		//Get data information;
    	workbook.forEach(sheet -> {
    		if (sheet.getSheetName().equals(nameSheet)) {
    	    	sheet.forEach(row -> {
    	    		String employeeInfoString = row.getCell(0).toString();
    	    		String[] empInfoArr = employeeInfoString.split("\\s+");
					if (empInfoArr.length >2) {
						empInfoList.add(new Employee(empInfoArr[2].trim(), empInfoArr[0].trim()));
					}else {
						empInfoList.add(new Employee("no name", empInfoArr[0].trim()));						
					}
    	    	});
			}
    	});    	
    	workbook.close();
    	file.setLastModified(dateModify);
		return empInfoList;
	}

}
