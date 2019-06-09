package com.rikkeisoft.multithread;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.rikkeisoft.model.Employee;
import com.rikkeisoft.ultis.LoadProperties;

public class ThreadReadEmpIf implements Runnable {
	private final String FILE_LIST_EMPLOYEE_OBJECT = LoadProperties.getProperties().getFileListEmpObject();
	private List<Employee> employeeListHaveExceptedLink = null;

	final static Logger logger = Logger.getLogger(ThreadReadEmpIf.class);

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		logger.info("Run: Thread get employee infomation from loacal file!");
		ObjectInputStream inputObject;
		try {
			inputObject = new ObjectInputStream(new FileInputStream(FILE_LIST_EMPLOYEE_OBJECT));
			setEmployeeListHaveExceptedLink((List<Employee>) inputObject.readObject());
			inputObject.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("End: Thread get employee infomation from loacal file!");
	}

	public List<Employee> getEmployeeListHaveExceptedLink() {
		return employeeListHaveExceptedLink;
	}

	public void setEmployeeListHaveExceptedLink(List<Employee> employeeListHaveExceptedLink) {
		this.employeeListHaveExceptedLink = employeeListHaveExceptedLink;
	}

}
