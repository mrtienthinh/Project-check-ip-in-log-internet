package com.rikkeisoft.multithread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.rikkeisoft.model.Employee;
import com.rikkeisoft.ultis.EmpoyeeUtils;
import com.rikkeisoft.ultis.ExceptedLinkUtils;
import com.rikkeisoft.ultis.LoadProperties;

public class ThreadGetEmpInf implements Runnable {
	private String FILE_CONTAIN_EMPLOYEE_IP = LoadProperties.getProperties().getFileContainEmp();
	private String SHEET_CONTAIN_EMPLOYEE = LoadProperties.getProperties().getSheetContainEmp();
	private String FILE_CONTAIN_BLACK_WHITE_LINKS = LoadProperties.getProperties().getFileContainBlackWhiteLinks();
	private String SHEET_CONTAIN_EXCEPTED_LINKS = LoadProperties.getProperties().getSheetContainExceptedLinks();
	private String FILE_LIST_EMPLOYEE_OBJECT = LoadProperties.getProperties().getFileListEmpObject();

	final static Logger logger = Logger.getLogger(ThreadGetEmpInf.class);

	private List<Employee> employeeListHaveExceptedLink = null;

	public List<Employee> getEmployeeListHaveExceptedLink() {
		return employeeListHaveExceptedLink;
	}

	public void setEmployeeListHaveExceptedLink(List<Employee> employeeListHaveExceptedLink) {
		this.employeeListHaveExceptedLink = employeeListHaveExceptedLink;
	}

	@Override
	public void run() {
		logger.info("Run: Thread get employee infomation and crawle data from internet!");
		// Load employee from excel;
		EmpoyeeUtils empoyeeUtils = new EmpoyeeUtils(FILE_CONTAIN_EMPLOYEE_IP, SHEET_CONTAIN_EMPLOYEE);
		List<Employee> employeeList = null;
		try {
			employeeList = empoyeeUtils.getListEmpInfo();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
			e1.printStackTrace();
		}

		// Load excepted link for employee;
		ExceptedLinkUtils exceptedLinkUtils = new ExceptedLinkUtils(FILE_CONTAIN_BLACK_WHITE_LINKS,
				SHEET_CONTAIN_EXCEPTED_LINKS);
		try {
			employeeListHaveExceptedLink = exceptedLinkUtils.addExceptedLinkToEmp(employeeList);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		// Write object to file;
		ObjectOutputStream outObject = null;
		try {
			outObject = new ObjectOutputStream(new FileOutputStream(FILE_LIST_EMPLOYEE_OBJECT));
			outObject.writeObject(employeeListHaveExceptedLink);
			outObject.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		logger.info("End: Thread get employee infomation and crawle data from internet!");
	}

}
