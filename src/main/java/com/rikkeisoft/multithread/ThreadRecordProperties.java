package com.rikkeisoft.multithread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rikkeisoft.ultis.LoadProperties;

public class ThreadRecordProperties implements Runnable {
	private final String FILE_PROPERTIES = "properties.txt";

	final static Logger logger = Logger.getLogger(ThreadGetBlackLink.class);

	@Override
	public void run() {
		logger.info("Run: Thread write properties information to file!");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(FILE_PROPERTIES));
			writer.append("last.time.load.from.internet=" + sdf.format(new Date()) + "\n");
			writer.append("file.contain.black.white.links="
					+ LoadProperties.getProperties().getFileContainBlackWhiteLinks() + "\n");
			writer.append(
					"sheet.contain.black.links=" + LoadProperties.getProperties().getSheetContainBlackLinks() + "\n");
			writer.append("file.list.black.object=" + LoadProperties.getProperties().getFileListBlackObject() + "\n");
			writer.append("file.contain.emp=" + LoadProperties.getProperties().getFileContainEmp() + "\n");
			writer.append("sheet.contain.emp=" + LoadProperties.getProperties().getSheetContainEmp() + "\n");
			writer.append("sheet.contain.excepted.links="
					+ LoadProperties.getProperties().getSheetContainExceptedLinks() + "\n");
			writer.append("file.list.emp.object=" + LoadProperties.getProperties().getFileListEmpObject() + "\n");
			writer.append("file.log=" + LoadProperties.getProperties().getFileLog() + "\n");
			writer.append("mail.host=" + LoadProperties.getProperties().getMailHost() + "\n");
			writer.append("mail.port=" + LoadProperties.getProperties().getMailPort() + "\n");
			writer.append("mail.username=" + LoadProperties.getProperties().getMailUserName() + "\n");
			writer.append("mail.password=" + LoadProperties.getProperties().getMailPassword() + "\n");
			writer.append("mail.from.address=" + LoadProperties.getProperties().getMailFromAddress() + "\n");
			writer.append("mail.from.address=" + LoadProperties.getProperties().getMailFromAddress() + "\n");
			writer.append("mail.to.address=" + LoadProperties.getProperties().getMailToAddress() + "\n");
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logger.info("End: Thread write properties information to file!");
	}

}
