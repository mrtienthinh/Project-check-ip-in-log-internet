package com.rikkeisoft.multithread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadRecordProperties implements Runnable {
	private final String FILE_PROPERTIES = "properties.txt";
	
	@Override
	public void run() {
		System.out.println("Run: Thread write properties information to file!");
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
		System.out.println("End: Thread write properties information to file!");
	}
	
}
