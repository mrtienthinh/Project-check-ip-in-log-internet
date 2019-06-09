package com.rikkeisoft.ultis;

import com.rikkeisoft.model.EmployeeInLog;

public class LogUtils {

	public static String getIpEmployee(String line) {
		String[] resultAfterSplit = line.split("proto TCP");
		if (resultAfterSplit.length >= 2) {
			return resultAfterSplit[1].split(", ")[1].split("->")[0].split(":")[0].trim();
		}
		return null;
	}
	
	public static String getWebAccess(String line) {
		String[] resultAfterSplit = line.split("proto TCP");
		if (resultAfterSplit.length >= 2) {
			return resultAfterSplit[1].split(", ")[1].split("->")[1].split(":")[0].trim();
		}
		return null;
	}

	public static String getTimeAccess(String line) {
		String[] resultAfterSplit = line.split("\\s+");
		if (resultAfterSplit.length>1) {
			return resultAfterSplit[0]+" "+resultAfterSplit[1]+" "+resultAfterSplit[2].trim();			
		}
		return null;
	}
	
	public static EmployeeInLog getEmployeeInLog(String line) {
		return new EmployeeInLog(getIpEmployee(line), getWebAccess(line), getTimeAccess(line));
	}
	

}
