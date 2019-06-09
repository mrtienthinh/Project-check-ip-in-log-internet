package com.rikkeisoft.ultis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadProperties {
	private Date modifiledBlackWhiteFile;

	public LoadProperties(String fileName) throws IOException, ParseException {
		BufferedReader reader = 
				  new BufferedReader(new FileReader(fileName));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String line;
		
		while ((line = reader.readLine())!=null) {
			if (line.contains("dateFile")) {
				this.modifiledBlackWhiteFile = sdf.parse(line.split(": ")[1]);
			}
		}
		reader.close();
	}

	public Date getModifiledBlackWhiteFile() {
		return modifiledBlackWhiteFile;
	}

	public void setModifiledBlackWhiteFile(Date modifiledBlackWhiteFile) {
		this.modifiledBlackWhiteFile = modifiledBlackWhiteFile;
	}
}
