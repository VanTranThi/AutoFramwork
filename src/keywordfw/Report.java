package keywordfw;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

public class Report {
	FileOutputStream fout;
	ByteArrayOutputStream outputStream;
	XSSFWorkbook workBook;
	XSSFSheet sheetSummary;
	XSSFSheet sheetDetails;
	XSSFRow row, rowOne;
	XSSFCell cell_summary_1, cell_summary_2, cell_summary_3, cell_summary_4
	         , cell_details_1, cell_details_2, cell_details_3, cell_details_4;
	String destinationFilePath;
	int m;

	public void createNewReport(String suitename, ArrayList ar_testsuite, ArrayList ar_status, String environment
			                    , String dateTime, String msgError, String duration){
		try{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			LocalDateTime now = LocalDateTime.now();
			String dateTimeSYS = dtf.format(now);
			
			// Directory path where the xls file will be created
			destinationFilePath = "./Automation_Test_Run_" + dateTimeSYS + ".xlsx";
			
			//File f = new File(destinationFilePath);
			// Create object of FileOutputStream
			fout = new FileOutputStream(destinationFilePath);

			// Build the Excel File
			outputStream = new ByteArrayOutputStream();
			workBook = new XSSFWorkbook();
			// Create the spreadsheet
			sheetSummary = workBook.createSheet("Summary");
			sheetDetails = workBook.createSheet("Details");

			// Create the first row
//			rowOne = sheetSummary.createRow((short) 1);
//			row = sheetDetails.createRow((short) 1);
			formatExcel();
			writeReport(suitename, ar_testsuite, ar_status, environment, dateTime, msgError, duration);
            
			closeReport();
			
		}catch(Exception e){
			System.out.println("Create report error: " + e);
		}
	}
	
	public void writeReport(String suitename, ArrayList ar_testsuite, ArrayList ar_status, String environment
			                , String dateTime, String msgError, String duration){
		try{
			rowOne = sheetSummary.createRow((short) 1);
			for(int i = 0; i < ar_testsuite.size(); i++){
				//rowOne = sheetSummary.createRow((short) i + 1);
				row = sheetDetails.createRow((short) i + 1);
				
				cell_summary_1 = rowOne.createCell(0);
				cell_summary_1.setCellValue(new XSSFRichTextString(suitename));
				cell_summary_2 = rowOne.createCell(1);
				cell_summary_2.setCellValue(new XSSFRichTextString(environment));
				cell_summary_3 = rowOne.createCell(2);
				cell_summary_3.setCellValue(new XSSFRichTextString(dateTime));
				cell_summary_4 = rowOne.createCell(3);
				cell_summary_4.setCellValue(new XSSFRichTextString(duration));
				
				String[] output = ar_testsuite.get(i).toString().split("_____");
				cell_details_1 = row.createCell(0);
				cell_details_1.setCellValue(new XSSFRichTextString(output[0].toString()));
				
				cell_details_2 = row.createCell(1);
				cell_details_2.setCellValue(new XSSFRichTextString(output[1].toString()));
				
				cell_details_3 = row.createCell(2);
				cell_details_3.setCellValue(new XSSFRichTextString(ar_status.get(i).toString()));
				
				cell_details_4 = row.createCell(3);
				if(cell_details_3.getRichStringCellValue().equals("Fail")){
					cell_details_4.setCellValue(new XSSFRichTextString(msgError));
				}
				
				
				if(ar_status.get(i).toString().equals("Fail")){
					cell_details_4 = row.createCell(3);
					cell_details_4.setCellValue(new XSSFRichTextString(msgError));
					
					CellStyle style = workBook.createCellStyle();
				    Font font = workBook.createFont();
				    font.setColor(HSSFColor.RED.index);
				    style.setFont(font);
				    cell_details_1.setCellStyle(style);
				    cell_details_2.setCellStyle(style);
				    cell_details_3.setCellStyle(style);
				    cell_details_4.setCellStyle(style);
				}
				m = i;
			}

			workBook.write(outputStream);
			outputStream.writeTo(fout);
			
		}catch(Exception e){
			System.out.println("Write report error: " + e);
		}
	}
	
	public void updateReport(String suitename, ArrayList ar_testsuite, ArrayList ar_status, int check_multixls
			                 , String environment, String dateTime, String msgError, String duration){
		try{
			File fl = new File(destinationFilePath);
	        FileInputStream inputStream = new FileInputStream(fl);
	         
	        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        XSSFSheet sheet_Summary = workbook.getSheetAt(0);
	        XSSFSheet sheet_Details = workbook.getSheetAt(1);
	        check_multixls++;
	        XSSFRow rowOne = sheet_Summary.createRow(check_multixls);
	             
			for(int i = 0; i < ar_testsuite.size(); i++){ 
				XSSFRow row = sheet_Details.createRow(m + 2);
				
				cell_summary_1 = rowOne.createCell(0);
				cell_summary_1.setCellValue(new XSSFRichTextString(suitename));
				cell_summary_2 = rowOne.createCell(1);
				cell_summary_2.setCellValue(new XSSFRichTextString(environment));
				cell_summary_3 = rowOne.createCell(2);
				cell_summary_3.setCellValue(new XSSFRichTextString(dateTime));
				cell_summary_4 = rowOne.createCell(3);
				cell_summary_4.setCellValue(new XSSFRichTextString(duration));
				
				String[] output = ar_testsuite.get(i).toString().split("_____");
				cell_details_1 = row.createCell(0);
				cell_details_1.setCellValue(new XSSFRichTextString(output[0].toString()));
				
				cell_details_2 = row.createCell(1);
				cell_details_2.setCellValue(new XSSFRichTextString(output[1].toString()));
				
				cell_details_3 = row.createCell(2);
				cell_details_3.setCellValue(new XSSFRichTextString(ar_status.get(i).toString()));
				
				if(ar_status.get(i).toString().equals("Fail")){
					cell_details_4 = row.createCell(3);
					cell_details_4.setCellValue(new XSSFRichTextString(msgError));
					
					CellStyle style = workbook.createCellStyle();
				    Font font = workbook.createFont();
				    font.setColor(HSSFColor.RED.index);
				    style.setFont(font);
				    cell_details_1.setCellStyle(style);
				    cell_details_2.setCellStyle(style);
				    cell_details_3.setCellStyle(style);
				    cell_details_4.setCellStyle(style);
				}
				m++;
			}
			
//			CellStyle style = workbook.createCellStyle();
//            style.setWrapText(true);
//            cell.setCellStyle(style);
			
			inputStream.close();
			 
	         FileOutputStream outputStream = new FileOutputStream(destinationFilePath);
	         workbook.write(outputStream);
	         workbook.close();
	         outputStream.close();
		}catch(Exception e){
			System.out.println("Update report error: " + e);
		}
	}
	
	public void formatExcel(){
		try{
			// TODO Auto-generated method stub
			
			//Create Style
			XSSFCellStyle cellStyle = workBook.createCellStyle();
			//Create Border, Color of Border
			cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderColor(BorderSide.LEFT, new XSSFColor(Color.BLACK));
			cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.BLACK));
			cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderColor(BorderSide.TOP, new XSSFColor(Color.BLACK));
			cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(Color.BLACK));
			//Set Cell Fill Color
			cellStyle.setFillPattern(XSSFCellStyle.LEAST_DOTS);
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			//Set Font with Font-family : Century Gothic, Size: 10px, Bold
			XSSFFont font = workBook.createFont();
			font.setFontName("Century Gothic");
			font.setFontHeight(10);
			font.setBold(true);
			cellStyle.setFont(font);
			/**
			* Create Format for Summary Sheet
			*/
			// Row 0, Column 0 => Cell 0
			XSSFRow rowOne = sheetSummary.createRow(0);
			XSSFRow rowDetailOne = sheetDetails.createRow(0);
			/**
			* Set value, set Style, set Rotation
			*/
			//Cell 0|0
			sheetSummary.setColumnWidth(0, 12*256);
			XSSFCell cellOne = rowOne.createCell(0);
			cellOne.setCellValue("Suite Name");
			cellOne.setCellStyle(cellStyle);
			//Cell 0|1
			sheetSummary.setColumnWidth(1, 13*256);
			XSSFCell cellTwo = rowOne.createCell(1);
			cellTwo.setCellValue("Environment");
			cellTwo.setCellStyle(cellStyle);
			//Cell 0|2
			sheetSummary.setColumnWidth(2, 19*256);
			XSSFCell cellThree = rowOne.createCell(2);
			cellThree.setCellValue("Time start");
			cellThree.setCellStyle(cellStyle);
			//Cell 0|3
			sheetSummary.setColumnWidth(3, 19*256);
			XSSFCell cellFour = rowOne.createCell(3);
			cellFour.setCellValue("Duration");
			cellFour.setCellStyle(cellStyle);
			/**
			* Create Format for Detail Sheet
			*/
			//Cell 0|0
			sheetDetails.setColumnWidth(0, 12*256);
			XSSFCell cellDetailOne = rowDetailOne.createCell(0);
			cellDetailOne.setCellValue("Suite Name");
			cellDetailOne.setCellStyle(cellStyle);
			//Cell 0|1
			sheetDetails.setColumnWidth(1, 14*256);
			XSSFCell cellDetailTwo = rowDetailOne.createCell(1);
			cellDetailTwo.setCellValue("Test Case ID");
			cellDetailTwo.setCellStyle(cellStyle);
			//Cell 0|2
			sheetDetails.setColumnWidth(2, 18*256);
			XSSFCell cellDetailThree = rowDetailOne.createCell(2);
			cellDetailThree.setCellValue("Status (Pass/Fail)");
			cellDetailThree.setCellStyle(cellStyle);
			//Cell 0|3
			sheetDetails.setColumnWidth(3, 125*256);
			XSSFCell cellDetailFour = rowDetailOne.createCell(3);
			cellDetailFour.setCellValue("Remark");
			cellDetailFour.setCellStyle(cellStyle);
		}catch(Exception e){
			System.out.println("Format report error: " + e);
		}
    }
	
	public void closeReport(){
		try{
			outputStream.close();
			fout.close();
		}catch(Exception e){
			System.out.println("Close report error: " + e);
		}
	}
}
