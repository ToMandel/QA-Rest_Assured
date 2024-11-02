package tests_rest_assured;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

public class Excel {

	public static int rowCount;
	public static Sheet guru99Sheet;
	File txtFile = new File("C:\\Users\\Bored\\Downloads" + "\\" + "log.txt");

	public Excel() {

	}

	public void readExcel(String filePath, String fileName, String sheetName) throws IOException {
		// Create an object of File class to open xlsx file
		File file = new File(filePath + "\\" + fileName);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook guru99Workbook = null;
		// Find the file extension by splitting file name in substring and getting only
		// extension name
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		// Check condition if the file is xls file
		if (fileExtensionName.equals(".xls")) {
			// If it is xls file then create object of HSSFWorkbook class
			guru99Workbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		guru99Sheet = guru99Workbook.getSheet(sheetName);
		// Find number of rows in excel file
		rowCount = guru99Sheet.getLastRowNum() - guru99Sheet.getFirstRowNum();
	}

	public void writeToFile(String str) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter(txtFile, true));
		out.write(str);
		out.close();

	}

	public static int getRowcount() {
		return rowCount;
	}

	public static Sheet getsheet() {
		return guru99Sheet;
	}
}
