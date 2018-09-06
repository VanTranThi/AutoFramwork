package keywordfw;

import java.util.ArrayList;

public class handleXLS {

	public static Object[][] GetTestDataUtility(readXLS xls, String sheetName, String TestCaseName) {
		return xls.retrieveTestData(sheetName, TestCaseName);
	}

	public static void WriteResultUtility(readXLS xls, String sheetName, String colName, String rowName,
			String Result) {
		xls.writeResult(sheetName, colName, rowName, Result);
	}

	public static String GetDataToExcel(readXLS xls, String sheetName, String colName, String rowName) {
		return xls.retrieveDataToExcel(sheetName, colName, rowName);
	}

	public static ArrayList<String> GetDataColumnExcel(readXLS xls, String sheetName, String ColName) {
		return xls.retrieveColumnTestData(sheetName, ColName);
	}
}
