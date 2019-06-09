package com.rikkeisoft.ultis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadProperties {
	private final String FILE_PROPERTIES = "properties.txt";
	private Date modifiledBlackWhiteFile;
	private String fileContainBlackWhiteLinks;
	private String sheetContainBlackLinks;
	private String fileListBlackObject;
	private String fileContainEmp;
	private String sheetContainEmp;
	private String sheetContainExceptedLinks;
	private String fileListEmpObject;
	private String fileLog;
	private String mailHost;
	private int mailPort;
	private String mailUserName;
	private String mailPassword;
	private String mailFromAddress;
	private String mailToAddress;

	private static LoadProperties properties = null;

	public static LoadProperties getProperties() {
		if (properties == null) {
			properties = new LoadProperties();
		}
		return properties;
	}

	private LoadProperties() {
		System.out.println("run load properties");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(FILE_PROPERTIES));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				if (line.contains("last.time.load.from.internet")) {
					this.modifiledBlackWhiteFile = sdf.parse(line.split("=")[1]);
				} else if (line.contains("file.contain.black.white.links")) {
					this.fileContainBlackWhiteLinks = line.split("=")[1];
				} else if (line.contains("sheet.contain.black.links")) {
					this.sheetContainBlackLinks = line.split("=")[1];
				} else if (line.contains("file.list.black.object")) {
					this.fileListBlackObject = line.split("=")[1];
				} else if (line.contains("file.contain.emp")) {
					this.fileContainEmp = line.split("=")[1];
				} else if (line.contains("sheet.contain.emp")) {
					this.sheetContainEmp = line.split("=")[1];
				} else if (line.contains("sheet.contain.excepted.links")) {
					this.sheetContainExceptedLinks = line.split("=")[1];
				} else if (line.contains("file.list.emp.object")) {
					this.fileListEmpObject = line.split("=")[1];
				} else if (line.contains("file.log")) {
					this.fileLog = line.split("=")[1];
				} else if (line.contains("mail.host")) {
					this.mailHost = line.split("=")[1];
				} else if (line.contains("mail.port")) {
					this.mailPort = Integer.parseInt(line.split("=")[1]);
				} else if (line.contains("mail.username")) {
					this.mailUserName = line.split("=")[1];
				} else if (line.contains("mail.password")) {
					this.mailPassword = line.split("=")[1];
				} else if (line.contains("mail.from.address")) {
					this.mailFromAddress = line.split("=")[1];
				} else if (line.contains("mail.to.address")) {
					this.mailToAddress = line.split("=")[1];
				}
			}
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getModifiledBlackWhiteFile() {
		return modifiledBlackWhiteFile;
	}

	public void setModifiledBlackWhiteFile(Date modifiledBlackWhiteFile) {
		this.modifiledBlackWhiteFile = modifiledBlackWhiteFile;
	}

	public String getFileContainBlackWhiteLinks() {
		return fileContainBlackWhiteLinks;
	}

	public void setFileContainBlackWhiteLinks(String fileContainBlackWhiteLinks) {
		this.fileContainBlackWhiteLinks = fileContainBlackWhiteLinks;
	}

	public String getSheetContainBlackLinks() {
		return sheetContainBlackLinks;
	}

	public void setSheetContainBlackLinks(String sheetContainBlackLinks) {
		this.sheetContainBlackLinks = sheetContainBlackLinks;
	}

	public String getFileListBlackObject() {
		return fileListBlackObject;
	}

	public void setFileListBlackObject(String fileListBlackObject) {
		this.fileListBlackObject = fileListBlackObject;
	}

	public String getFileContainEmp() {
		return fileContainEmp;
	}

	public void setFileContainEmp(String fileContainEmp) {
		this.fileContainEmp = fileContainEmp;
	}

	public String getSheetContainEmp() {
		return sheetContainEmp;
	}

	public void setSheetContainEmp(String sheetContainEmp) {
		this.sheetContainEmp = sheetContainEmp;
	}

	public String getSheetContainExceptedLinks() {
		return sheetContainExceptedLinks;
	}

	public void setSheetContainExceptedLinks(String sheetContainExceptedLinks) {
		this.sheetContainExceptedLinks = sheetContainExceptedLinks;
	}

	public String getFileListEmpObject() {
		return fileListEmpObject;
	}

	public void setFileListEmpObject(String fileListEmpObject) {
		this.fileListEmpObject = fileListEmpObject;
	}

	public String getFileLog() {
		return fileLog;
	}

	public void setFileLog(String fileLog) {
		this.fileLog = fileLog;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public int getMailPort() {
		return mailPort;
	}

	public void setMailPort(int mailPort) {
		this.mailPort = mailPort;
	}

	public String getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String getMailFromAddress() {
		return mailFromAddress;
	}

	public void setMailFromAddress(String mailFromAddress) {
		this.mailFromAddress = mailFromAddress;
	}

	public String getMailToAddress() {
		return mailToAddress;
	}

	public void setMailToAddress(String mailToAddress) {
		this.mailToAddress = mailToAddress;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

}
