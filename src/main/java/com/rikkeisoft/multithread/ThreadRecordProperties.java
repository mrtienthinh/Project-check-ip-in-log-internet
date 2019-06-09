package com.rikkeisoft.multithread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

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
			writer.append("dateFile: " + sdf.format(new Date()));
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logger.info("End: Thread write properties information to file!");
	}

}
