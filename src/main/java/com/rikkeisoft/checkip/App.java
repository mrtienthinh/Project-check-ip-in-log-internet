package com.rikkeisoft.checkip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.rikkeisoft.model.BlackLink;
import com.rikkeisoft.model.Employee;
import com.rikkeisoft.model.EmployeeBlack;
import com.rikkeisoft.model.EmployeeInLog;
import com.rikkeisoft.multithread.ThreadGetBlackLink;
import com.rikkeisoft.multithread.ThreadGetEmpInf;
import com.rikkeisoft.multithread.ThreadReadBlackLink;
import com.rikkeisoft.multithread.ThreadReadEmpIf;
import com.rikkeisoft.multithread.ThreadRecordProperties;
import com.rikkeisoft.ultis.LoadProperties;
import com.rikkeisoft.ultis.LogUtils;

public class App {
	public static final String FILE_LOG = LoadProperties.getProperties().getFileLog();

	public static final String FILE_CONTAIN_BLACK_WHITE_LINKS = LoadProperties.getProperties()
			.getFileContainBlackWhiteLinks();

	private static final String HOST = LoadProperties.getProperties().getMailHost();
	private static final int PORT = LoadProperties.getProperties().getMailPort();
	private static final boolean SSL_FLAG = true;

	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException,
			ParseException, ClassNotFoundException, InterruptedException {

		List<Employee> employeeListHaveExceptedLink = null;
		List<BlackLink> blackLinks = null;

		// Check file properties;
		Date dateFileBlackWhite = LoadProperties.getProperties().getModifiledBlackWhiteFile();
		File fileBlackWhite = new File(FILE_CONTAIN_BLACK_WHITE_LINKS);
		Date dateToCompare = new Date(fileBlackWhite.lastModified());

		if (dateFileBlackWhite.compareTo(dateToCompare) < 0) {
			logger.info("Run: Over date load from internet!");
			ThreadGetEmpInf threadGetEmpInf = new ThreadGetEmpInf();
			ThreadGetBlackLink threadGetBlackLink = new ThreadGetBlackLink();
			ThreadRecordProperties threadRecordProperties = new ThreadRecordProperties();
			Thread thread1 = new Thread(threadGetEmpInf);
			Thread thread2 = new Thread(threadGetBlackLink);
			Thread thread3 = new Thread(threadRecordProperties);
			thread1.start();
			thread2.start();
			thread3.start();
			thread1.join();
			thread2.join();
			thread3.join();

			employeeListHaveExceptedLink = threadGetEmpInf.getEmployeeListHaveExceptedLink();
			blackLinks = threadGetBlackLink.getBlackLinks();

			logger.info("End: Over date load from internet!");
		} else {
			logger.info("Run: Load from local!");
			ThreadReadEmpIf threadReadEmpIf = new ThreadReadEmpIf();
			ThreadReadBlackLink threadReadBlackLink = new ThreadReadBlackLink();

			Thread thread4 = new Thread(threadReadEmpIf);
			Thread thread5 = new Thread(threadReadBlackLink);

			thread4.run();
			thread5.run();
			thread4.join();
			thread5.join();

			employeeListHaveExceptedLink = threadReadEmpIf.getEmployeeListHaveExceptedLink();
			blackLinks = threadReadBlackLink.getBlackLinks();
			logger.info("End: Load from local!");
		}

		// Load log file;
		logger.info("Run: Load file log!");
		BufferedReader reader = new BufferedReader(new FileReader(FILE_LOG));
		List<EmployeeBlack> employeeBlacks = new ArrayList<>();
		String line;

		while ((line = reader.readLine()) != null) {
			EmployeeInLog employeeInLog = LogUtils.getEmployeeInLog(line);
			if (employeeInLog.getIp() == null) {
				continue;
			}

			String ipBlackLink = null;
			String domainOfBackLink = null;
			String timeAccess = null;
			String ipOfEmployee = null;
			String nameOfEmployee = "no name";

			// Check from black link;
			for (int i = 0; i < blackLinks.size(); i++) {
				for (int j = 0; j < blackLinks.get(i).getIpList().size(); j++) {
					if (blackLinks.get(i).getIpList().get(j).equals(employeeInLog.getWebsite())) {
						domainOfBackLink = blackLinks.get(i).getName();
						timeAccess = employeeInLog.getTimeAccess();
						ipBlackLink = employeeInLog.getWebsite();
						ipOfEmployee = employeeInLog.getIp();
					}
				}
			}

			// Check excepted list;
			boolean isExceptedLink = false;
			if (domainOfBackLink != null) {
				for (int i = 0; i < employeeListHaveExceptedLink.size(); i++) {
					if (employeeListHaveExceptedLink.get(i).getExceptedLinksList() != null) {
						for (int j = 0; j < employeeListHaveExceptedLink.get(i).getExceptedLinksList().size(); j++) {
							if (employeeListHaveExceptedLink.get(i).getExceptedLinksList().get(j).equals(ipBlackLink)) {
								isExceptedLink = true;
							}
						}
						if (employeeListHaveExceptedLink.get(i).getIp().equals(ipOfEmployee)) {
							nameOfEmployee = employeeListHaveExceptedLink.get(i).getName();
						}
					}
				}
			}

			// Add list bad employee;
			if (!isExceptedLink && domainOfBackLink != null) {
				employeeBlacks.add(new EmployeeBlack(nameOfEmployee, ipOfEmployee, domainOfBackLink, timeAccess));
			}
		}
		reader.close();
		logger.info("End: Load file log!");

		// Write bad employee to file!
		writeToExcel(employeeBlacks);

		// Send mail;
		sendMail();

	}

	private static void sendMail() {
		logger.info("Run: Send Email!");
		String userName = LoadProperties.getProperties().getMailUserName();
		String password = LoadProperties.getProperties().getMailPassword();

		String fromAddress = LoadProperties.getProperties().getMailFromAddress();
		String toAddress = LoadProperties.getProperties().getMailToAddress();
		String subject = "Test Mail";
		String message = "Hello from Apache Mail";

		try {
			// Create the attachment;
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath("save.xlsx");
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Save List Bad Employee");
			attachment.setName("save.xlsx");
			// Send email;
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName(HOST);
			email.setSmtpPort(PORT);
			email.setAuthenticator(new DefaultAuthenticator(userName, password));
			email.setSSLOnConnect(SSL_FLAG);
			email.setFrom(fromAddress);
			email.setSubject(subject);
			email.setMsg(message);
			email.attach(attachment);
			email.addTo(toAddress);
			email.send();
		} catch (Exception ex) {
			System.out.println("Unable to send email");
			System.out.println(ex);
		}

		logger.info("Done: Send email!");
	}

	public static void writeToExcel(List<EmployeeBlack> employeeBlacks)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		logger.info("Run: Write list bad employee to file!");
		try {
			FileInputStream fileInputStream = new FileInputStream("save.xlsx");
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			Sheet sheet = workbook.createSheet("thinh" + System.nanoTime());
			for (int i = 0; i < employeeBlacks.size(); i++) {
				Row row = sheet.createRow(i);
				row.createCell(0).setCellValue(employeeBlacks.get(i).getName());
				row.createCell(1).setCellValue(employeeBlacks.get(i).getIp());
				row.createCell(2).setCellValue(employeeBlacks.get(i).getBlackDomain());
				row.createCell(3).setCellValue(employeeBlacks.get(i).getAccessTime());
			}
			fileInputStream.close();

			FileOutputStream fileOutputStream = new FileOutputStream("save.xlsx");
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			workbook.close();

		} catch (Exception e) {
			System.out.println("exception");
		}

		logger.info("End: Write list bad employee to file!");
	}
}
