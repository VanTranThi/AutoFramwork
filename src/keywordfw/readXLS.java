
package keywordfw;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readXLS {
	public String filelocation;
	public FileInputStream ipstr = null;
	public FileOutputStream opstr = null;
	private XSSFWorkbook wb = null;
	private XSSFSheet ws = null;

	public readXLS(String filelocation) {
		// TODO Auto-generated constructor stub
		this.filelocation = filelocation;
		try {
			ipstr = new FileInputStream(filelocation);
			wb = new XSSFWorkbook(ipstr);
			ws = wb.getSheetAt(0);
			ipstr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public readXLS() {

	}

	public int retrieveNoOfRows(String wsName) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return 0;
		else {
			ws = wb.getSheetAt(sheetIndex);
			int rowCount = ws.getLastRowNum() + 1;
			return rowCount;
		}
	}

	public int retrieveNoOfCols(String wsName) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return 0;
		else {
			ws = wb.getSheetAt(sheetIndex);
			int colCount = ws.getRow(0).getLastCellNum();
			return colCount;
		}
	}

	public int retrieveNoOfColsScriptFile(String wsName) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return 0;
		else {
			ws = wb.getSheetAt(sheetIndex);
			int colCount = ws.getRow(1).getLastCellNum();
			return colCount;
		}
	}

	/*
	 * 
	 */
	@SuppressWarnings("deprecation")
	public Object[][] retrieveTestData(String wsName, String TestcaseName) {
		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			Object data[][] = new Object[rowNum][colNum - 1];
			for (int i = 0; i < rowNum; i++) {
				XSSFRow Suiterow = ws.getRow(i);
				if (Suiterow.getCell(0).getStringCellValue().equals(TestcaseName.trim())) {
					int colNumber = i;
					XSSFRow Row = ws.getRow(colNumber);
					for (int j = 0; j < colNum - 1; j++) {
						if (Suiterow == null) {
							data[i][j] = "";
						} else {
							XSSFCell cell = Row.getCell(j);

							if (cell == null) {
								data[i][j] = "";
							} else {
								cell.setCellType(Cell.CELL_TYPE_STRING);
								String value = cellToString(cell);
								data[i][j] = value;
							}
						}
					}
				}
			}
			int col = 0;
			for (int i = 0; i < rowNum; i++) {
				if (data[i][0] != null) {
					col += 1;
				}
			}
			Object[][] newArray = new Object[col][colNum - 1];
			int tmp = -1;
			for (int i = 0; i < rowNum; i++) {
				tmp++;
				if (data[i][0] == null) {
					tmp--;
				}
				for (int j = 0; j < colNum - 1; j++) {
					if (data[i][0] != null) {
						newArray[tmp][j] = data[i][j];
					}
				}
			}
			return newArray;
		}

	}

	/*
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static String cellToString(XSSFCell cell) {
		int type;
		Object result;
		type = cell.getCellType();
		switch (type) {
		case 0:
			result = cell.getNumericCellValue();
			break;

		case 1:
			result = cell.getStringCellValue();
			break;

		default:
			throw new RuntimeException("Unsupportd cell.");
		}
		return result.toString();
	}

	/*
	 * 
	 */
	@SuppressWarnings("deprecation")
	public boolean writeResult(String wsName, String colName, String rowName, String Result) {
		try {
			int rowNum = retrieveNoOfRows(wsName);
			int rowNumber = -1;
			int sheetIndex = wb.getSheetIndex(wsName);
			if (sheetIndex == -1)
				return false;
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return false;
			}

			for (int i = 0; i < rowNum - 1; i++) {
				XSSFRow row = ws.getRow(i + 1);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cellToString(cell);
				if (value.equals(rowName)) {
					rowNumber = i + 1;
					break;
				}
			}

			XSSFRow Row = ws.getRow(rowNumber);
			XSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
				cell = Row.createCell(colNumber);

			cell.setCellValue(Result);

			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 
	 */
	public String retrieveDataToExcel(String wsName, String colName, String rowName) {

		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;
			int rowNumber = -1;

			XSSFRow Suiterow = ws.getRow(0);

			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}

			if (colNumber == -1) {
				return "";
			}

			for (int j = 0; j < rowNum; j++) {
				XSSFRow Suitecol = ws.getRow(j);
				if (Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())) {
					rowNumber = j;
				}
			}

			if (rowNumber == -1) {
				return "";
			}

			XSSFRow row = ws.getRow(rowNumber);
			XSSFCell cell = row.getCell(colNumber);
			if (cell == null) {
				return "";
			}
			String value = cellToString(cell);
			return value;
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<String> retrieveColumnTestData(String wsName, String colName) {

		int sheetIndex = wb.getSheetIndex(wsName);
		if (sheetIndex == -1)
			return null;
		else {
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber = -1;
			XSSFRow Suiterow = ws.getRow(0);
			ArrayList data = new ArrayList();
			for (int i = 0; i < colNum; i++) {
				if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
					colNumber = i;
				}
			}
			if (colNumber == -1) {
				return null;
			}
			for (int j = 0; j < rowNum - 1; j++) {
				XSSFRow Row = ws.getRow(j + 1);
				if (Row == null)
					break;
				XSSFCell cell = Row.getCell(colNumber);
				if (Row != null) {
					if (cell.getStringCellValue().trim().length() > 0) {
						String value = cellToString(cell);
						data.add(value);
					}
				}
			}
			return data;
		}
	}

}