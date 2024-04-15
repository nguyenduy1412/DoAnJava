package demo.method;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileName {
	public static String getFileNameToDateNow()
	{
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String fileName = currentDateTime.format(formatter)+".jpg";
		return fileName;
	}
}
