package keywordfw;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelProcess extends Action{
	
	static Action ac = new Action();
	static ArrayList<String> list_value = new ArrayList<String>();
	static ArrayList<String> list_action = new ArrayList<String>();
	static ArrayList<String> list_element = new ArrayList<String>();
	static ArrayList<String> list_typeElement = new ArrayList<String>();
	static ArrayList<String> list_summary_label = new ArrayList<String>();
	static ArrayList<String> list_summary = new ArrayList<String>();
	static ArrayList<String> list_summary2 = new ArrayList<String>();
	static ArrayList<String> list_automated = new ArrayList<String>();
	static ArrayList<String> list_id = new ArrayList<String>();
	static ArrayList<String> list_result2_save = new ArrayList<String>();
	static ArrayList<String> list_result3_save = new ArrayList<String>();
	
	static ArrayList<String> list_result2_update = new ArrayList<String>();
	static ArrayList<String> list_result3_update = new ArrayList<String>();
	static ArrayList<String> list_mail = new ArrayList<String>();
	static ArrayList<String> list_mail_update = new ArrayList<String>();
	
	static ArrayList<String> b = new ArrayList<String>();
	static ArrayList<String> list_result_mail = new ArrayList<String>();
//	static ArrayList<String> actualResult = new ArrayList<String>();
	static ArrayList<String> list_tcid = new ArrayList<String>();
	static ArrayList<String> list_result = new ArrayList<String>();
	
	static ArrayList<String> list_result2 = new ArrayList<String>();
	static ArrayList<String> list_result3 = new ArrayList<String>();
//	static ArrayList<String> list_result_noauto = new ArrayList<String>();
	static ArrayList<String> all_tc = new ArrayList<String>();
	static ArrayList<String> multiple_xls = new ArrayList<String>();
	static ArrayList<String> object_key = new ArrayList<String>();
	static ArrayList<String> object_value = new ArrayList<String>();
	static ArrayList<String> variable_key = new ArrayList<String>();
	static ArrayList<String> variable_value = new ArrayList<String>();
	static ArrayList<String> list_result_tcid = new ArrayList<String>();
	static int r = 2;
	static int check_multixls = 0;
	static DateFormat dateFormat;
	static Date date;
	static long startTime, endTime, duration;
	static String duration_final;
	static Report rp;
	static String swipeDimension;
	static Mail m;
	
	public ExcelProcess(){
		
	}
	
	public static void readExcel(ArrayList<String> ar){
		for(int mul = 0; mul < ar.size(); mul++){
			list_value.clear();
			list_action.clear();
			list_element.clear();
			list_typeElement.clear();
			list_summary_label.clear();
			list_summary.clear();
			list_summary2.clear();
			list_automated.clear();
			list_id.clear();
			list_tcid.clear();
			list_result.clear();
			list_result2.clear();
		    list_result3.clear();
			all_tc.clear();
			r = 2;
			ac.setRowNumber(r);
//			ac.result = "Pass";
			object_key.clear();
			object_value.clear();
			variable_key.clear();
			variable_value.clear();
			ac.result = "Pass";
			
			try{
//				String excelFilePath = "C:\\Users\\toan\\workspace\\KeywordFramework\\bin\\Auto-Script.xlsx";
				String excelFilePath = ar.get(mul);
				File fl = new File(excelFilePath);
		        FileInputStream inputStream = new FileInputStream(fl);
		        ac.scenario = fl.getName();
		         
		        Workbook workbook = new XSSFWorkbook(inputStream);
		        Sheet twoSheet = workbook.getSheetAt(1);
		        Iterator<Row> iterator = twoSheet.iterator();
		        
		        Sheet firstSheet = workbook.getSheetAt(0);
		        Iterator<Row> iterator1 = firstSheet.iterator();
		        
		        Sheet threeSheet = workbook.getSheetAt(2);
		        Iterator<Row> iterator3 = threeSheet.iterator();
		        
		        Sheet fourSheet = workbook.getSheetAt(3);
		        Iterator<Row> iterator4 = fourSheet.iterator();
		        
		        while (iterator1.hasNext()) {
		            Row nextRow = iterator1.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		             
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                
		                if(cell.getColumnIndex()==0){
		                	list_summary_label.add(cell.getStringCellValue());
		                }
		                
		                if(cell.getColumnIndex()==1){
		                	list_summary.add(cell.getStringCellValue());
		                }
		                
		                if(cell.getColumnIndex()==2){
		                	list_summary2.add(cell.getStringCellValue());
		                }
		            }
		        }
		         
		        while (iterator.hasNext()) {
		            Row nextRow = iterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		             
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                
		                if(cell.getColumnIndex()==5){
		                	if(!cell.getStringCellValue().equals("Value")){
		                		list_value.add(cell.getStringCellValue());
		                	}
		                }
		                
		                if(cell.getColumnIndex()==4){
		                	if(!cell.getStringCellValue().equals("Element")){
		                		list_element.add(cell.getStringCellValue());
		                	}
		                }
		                
		                if(cell.getColumnIndex()==3){
		                	if(!cell.getStringCellValue().equals("Type")){
		                		list_typeElement.add(cell.getStringCellValue());
		                	}
		                }
		                
		                if(cell.getColumnIndex()==2){
		                	if(!cell.getStringCellValue().equals("Action")){
		                		list_action.add(cell.getStringCellValue());
		                	}
		                }
		                
		                if(cell.getColumnIndex()==1){
		                	if(!cell.getStringCellValue().equals("Automated")){
		                		list_automated.add(cell.getStringCellValue());
		                	}
		                }
		                
		                if(cell.getColumnIndex()==0){
		                	if(!cell.getStringCellValue().equals("ID")){
		                		list_tcid.add(cell.getStringCellValue());
		                	}
		                }
		            }
		        }
		        
		        while (iterator3.hasNext()) {
		            Row nextRow = iterator3.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		             
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                
		                if(cell.getColumnIndex()==0){
		                	object_key.add(cell.getStringCellValue());
		                }
		                
		                if(cell.getColumnIndex()==1){
		                	object_value.add(cell.getStringCellValue());
		                }
		            }
		        }
		        
		        while (iterator4.hasNext()) {
		            Row nextRow = iterator4.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		             
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                
		                if(cell.getColumnIndex()==0){
		                	variable_key.add(cell.getStringCellValue());
		                }
		                
		                if(cell.getColumnIndex()==1){
		                	variable_value.add(cell.getStringCellValue());
		                }
		            }
		        }
		        
		        if(list_summary.get(1).equals("iOS") || list_summary.get(1).equals("Android")){
		        	dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        	date = new Date();
		        	startTime = System.currentTimeMillis();
		        	
		        	ac.settingAppium_Capabilities(list_summary.get(4), list_summary.get(5), list_summary.get(6), list_summary.get(7)
		        			                      , list_summary.get(8), list_summary.get(9), list_summary.get(10), list_summary.get(11)
		        			                      , list_summary.get(12), list_summary.get(13), list_summary.get(14), list_summary.get(15)
		        			                      , list_summary.get(16));
		        	ac.startAppium(list_summary.get(3), list_summary.get(1));
		        	ac.changeToWebView();
		        }else{
		        	ac.setRowNumber(0);
		        	dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        	date = new Date();
		        	startTime = System.currentTimeMillis();
		        	ac.openBrowser(list_summary.get(3), list_summary.get(1));
		        }
		        
		        for(int y = 0; y < list_element.size(); y++){
		        	for(int  m = 0; m < object_key.size(); m++){
		        		if(list_element.get(y).equals(object_key.get(m))){
		        			list_element.remove(y);
		        			list_element.add(y, object_value.get(m));
		        		}
		        	}
		        }
		        
		        for(int y = 0; y < list_value.size(); y++){
		        	for(int  m = 0; m < variable_key.size(); m++){
		        		if(list_value.get(y).equals(variable_key.get(m))){
		        			list_value.remove(y);
		        			list_value.add(y, variable_value.get(m));
		        		}
		        	}
		        }
		        
		        for(int i = 0; i < list_action.size(); i++){
		        	if(list_automated.get(i).equals("Y")){
		        		if(ac.result.equals("Pass")){
		        			ac.setRowNumber(r);
			        		ac.setTCID(list_tcid.get(i));
			        		if(list_action.get(i).equals("Send Keys")){
				        		ac.inputData(list_typeElement.get(i), list_element.get(i), list_value.get(i));
				        	}else if(list_action.get(i).equals("Close Browser")){
				        		ac.closeBrowser();
				        	}else if(list_action.get(i).equals("Delay Sleep")){
				        		ac.Sleep(Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Click")){
				        		ac.clickElement(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Delay Wait")){
				        		ac.waitElementVisible(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Verify Result")){
				        		ac.verifyResult(list_typeElement.get(i), list_element.get(i), list_value.get(i));
				        	}else if(list_action.get(i).equals("Verify Element Exist")){
				        		ac.verifyElementExist(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Clear")){
				        		ac.Clear(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Select DD By Index")){
				        		ac.selectDDByIndex(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Switch New Window")){
				        		ac.switchNewWindow();
				        	}else if(list_action.get(i).equals("Switch Parent Window")){
				        		ac.switchParentWindow();
				        	}else if(list_action.get(i).equals("Move To Element")){
				        		ac.moveToElement(list_typeElement.get(i), list_element.get(i));;
				        	}else if(list_action.get(i).equals("Verify Attribute Value")){
				        		ac.verifyAttributeValue(list_typeElement.get(i), list_element.get(i), list_value.get(i));
				        	}else if(list_action.get(i).equals("Paste Text")){
				        		ac.pasteText(list_value.get(i));
				        	}else if(list_action.get(i).equals("Press Enter")){
				        		ac.pressEnter();
				        	}else if(list_action.get(i).equals("Verify Image")){
				        		ac.verifyImage(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Capture")){
				        		ac.captureWindow(list_value.get(i));
				        	}else if(list_action.get(i).equals("Refresh Browser")){
				        		ac.refreshBrowser();
				        	}else if(list_action.get(i).equals("Wait Text Visible")){
				        		ac.waitTextVisible(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Press Down")){
				        		ac.pressDown();
				        	}else if(list_action.get(i).equals("Press Escape")){
				        		ac.pressEsc();
				        	}else if(list_action.get(i).equals("Click By Index")){
				        		ac.clickByIndex(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Move To Element By Index")){
				        		ac.moveToElementByIndex(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Click By Text")){
				        		ac.clickByText(list_typeElement.get(i), list_element.get(i), list_value.get(i));
				        	}else if(list_action.get(i).equals("Press Backspace")){
				        		ac.pressBackspace();
				        	}else if(list_action.get(i).equals("Click To End Item Next Auction")){
				        		ac.clickToEndItemNextAuction();
				        	}else if(list_action.get(i).equals("Go To URL")){
				        		ac.goToURL(list_value.get(i));
				        	}else if(list_action.get(i).equals("Close Tab")){
				        		ac.closeTab();
				        	}else if(list_action.get(i).equals("Click Auction List")){
				        		ac.clickAuctionList(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Publish Auction")){
				        		ac.publishAuction(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Delete Auction Not Publish")){
				        		ac.deleteAuctionNotPublish(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Press Right by Action")){
				        		ac.pressRightByAction();
				        	}else if(list_action.get(i).equals("Press Enter by Action")){
				        		ac.pressEnterByAction();
				        	}else if(list_action.get(i).equals("Press Down by Action")){
				        		ac.pressDownByAction();
				        	}else if(list_action.get(i).equals("Press Tab by Action")){
				        		ac.pressTabByAction();
				        	}else if(list_action.get(i).equals("Change To WEBVIEW")){
				        		ac.changeToWebView();
				        	}else if(list_action.get(i).equals("Change To NATIVEAPP")){
				        		ac.changeToNativeApp();
				        	}else if(list_action.get(i).equals("Verify Element Exist By Index")){
				        		ac.verifyElementExistByIndex(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Verify Background Color")){
				        		ac.verifyBackgroundColor(list_typeElement.get(i), list_element.get(i), list_value.get(i));
				        	}else if(list_action.get(i).equals("Wait Element Clickable")){
				        		ac.waitElementClickable(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Verify Element Not Exist")){
				        		ac.verifyElementNotExist(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Delete Value Use Keyboard")){
				        		ac.deleteValueUseKeyboard(list_typeElement.get(i), list_element.get(i));
				        	}else if(list_action.get(i).equals("Choose Image Native By Index")){
				        		ac.chooseImageNativeByIndex(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Swipe Right To Left")){
				        		String[] swipe_param = list_element.get(i).split(",");
				        		ac.swipeRightToLeft(list_typeElement.get(i), swipe_param);
				        	}else if(list_action.get(i).equals("Get Page Source")){
				        		ac.getPageSource();
				        	}else if(list_action.get(i).equals("Delay Wait IF")){
				        		ac.waitElementVisibleWithoutQuit(list_typeElement.get(i), list_element.get(i), Integer.valueOf(list_value.get(i)));
				        	}else if(list_action.get(i).equals("Click IF")){
				        		ac.clickElementWithoutQuit(list_typeElement.get(i), list_element.get(i));
					        }
			        		
//			        		System.out.println(list_tcid.get(i) + "-Pass");

			        		
//				        	else if (list_action.get(i).equals("Verify Result")) {
//				        	     String result = ac.verifyResult(list_typeElement.get(i), list_element.get(i), list_value.get(i));
//				        	     actualResult.add(result);
//				        	}else if (list_action.get(i).equals("Write Result")) {
//				        	     String TestCase = list_id.get(i);
//				        	     if (actualResult.size() > 0) {
//				        	      boolean result = true;
//				        	      for (int j = 0; j < actualResult.size(); j++) {
//				        	       if (actualResult.get(j).equals("Fail")) {
//				        	        result = false;
//				        	        break;
//				        	       }
//				        	      }
//				        	      if (result) {
//				        	       writeResult("Pass", TestCase);
//				        	      } else {
//				        	       writeResult("Fail", TestCase);
//				        	      }
//				        	     } else {
//				        	      System.out.println("Expected result don't have result!!!");
//				        	     }
//				        	     actualResult.clear();
//				        	}
//			        		if(list_action.get(i).equals("Open Browser")){
//			        		ac.openBrowser(list_value.get(i).toString(), list_summary.get(0));
//			        	}
		        		}else{
//		        			list_result.clear();
//		        			list_result.add(list_tcid.get(i) + "-Fail");
//		        			System.out.println(list_tcid.get(i) + "-Fail");
		        		}
		        		list_result.add(fl.getName() + "_____" + list_tcid.get(i) + "-----" + ac.result);
		        	}
		        	r++;
		        }
		        
		        endTime = System.currentTimeMillis();
		        duration = endTime - startTime;
		        Instant instant = Instant.ofEpochMilli ( duration );
		        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("HH:mm:ss");
		        duration_final = formatter.format ( zdt );
		        
		        HashSet hs = new HashSet();
		        hs.addAll(list_result);
		        list_result.clear();
		
		        list_result.addAll(hs);
		        
		        Collections.sort(list_result);
		 	    for(String counter: list_result){
		 	    	String[] parts = counter.split("-----");
		 	    	list_result2.add(parts[0]);
		 			list_result3.add(parts[1]);
		 		}
		 	    
		 	    for(int v = 0; v < list_result2.size(); v++){
		        	if(v + 1 < list_result2.size()){
		        		if(list_result2.get(v).equals(list_result2.get(v + 1))){
		        			list_result2.remove(v + 1 );
		        			list_result3.remove(v + 1 );
		        		}
		        	}
		        }
		        
		        workbook.close();
		        inputStream.close();
		        
//		        if(!list_summary.get(2).equals("No")){
//		        	
//		        }
//		        
		        if(!list_summary.get(2).equals("No")){
		        if((check_multixls > 0)){
		        	//list_result2_update.addAll(list_result2_save);
		        	list_result2_update.addAll(list_result2);
		        	//list_result3_update.addAll(list_result3_save);
		        	list_result3_update.addAll(list_result3);
		        	rp.updateReport(fl.getName(), list_result2, list_result3, check_multixls, list_summary.get(1)
		        			        , dateFormat.format(date), ac.msgError, duration_final);
		        	list_result2.clear();
		        	list_result3.clear();
		        	list_result2.addAll(list_result2_update);
		        	list_result3.addAll(list_result3_update);
		        	list_mail_update.clear();
		        	list_mail_update.add(list_summary2.get(2));
		        	
		        	m.sendMail(list_mail_update, ac.scenario, list_result2, list_result3);
		        }else{
		        	rp = new Report();
		        	rp.createNewReport(fl.getName(), list_result2, list_result3, list_summary.get(1)
		        			           , dateFormat.format(date), ac.msgError, duration_final);
		        	
		        	list_result2_save.addAll(list_result2);
		        	list_result3_save.addAll(list_result3);
		        	list_mail.add(list_summary2.get(2));
		        	list_mail_update.addAll(list_mail);	 
		        	m = new Mail();
		        	m.sendMail(list_mail_update, ac.scenario, list_result2_save ,list_result3_save);
		        }
		        }
		        else{
		        	if((check_multixls > 0)){
		        		rp.updateReport(fl.getName(), list_result2, list_result3, check_multixls, list_summary.get(1)
	        			        , dateFormat.format(date), ac.msgError, duration_final);
		        	}else{
		        		rp = new Report();
			        	rp.createNewReport(fl.getName(), list_result2, list_result3, list_summary.get(1)
			        			           , dateFormat.format(date), ac.msgError, duration_final);
		        	}
		        }
		        check_multixls++;     
			}catch(Exception ex){
				System.out.print("Error " + ex.getMessage());
			}	
		}
//		StringBuilder getdata = new StringBuilder(list_mail_update.toString());
//		getdata.deleteCharAt(0);
//		getdata.deleteCharAt(getdata.length()-1);	
//		String[] output = getdata.toString().split(",");
//		for(int i = 0; i < list_mail_update.size();i++){
//			for(int j = 0; j < list_mail_update.size(); j++){
//			if(!output[i].toString().equals(output[j+1].toString())){
//				m = new Mail();
//				//m.sendMail(output[i].toString(), ac.scenario , list_result2, list_result3);
//				m.sendMail(output[j+1].toString(), ac.scenario , list_result2, list_result3);
//			}
//			else{
//				m.sendMail(output[i].toString(), ac.scenario , list_result2, list_result3);
//			}
//			}
//			
//			}
		
	}
	
//	public static void sendMail(String address) throws Exception {
//		// TODO Auto-generated method stub
//		String to = address;
//	    String subject = "AUTOMATION RESULT";
//	    String msg ="email text....";
//	    final String from ="automation.infor@gmail.com";
//	    final  String password ="muvsolution";
//
//
//	    Properties props = new Properties();  
//	    props.setProperty("mail.transport.protocol", "smtp");     
//	    props.setProperty("mail.smtp.host", "smtp.gmail.com");  
//	    props.put("mail.smtp.auth", "true");  
//	    props.put("mail.smtp.port", "465");  
//	    props.put("mail.debug", "true");  
//	    props.put("mail.smtp.socketFactory.port", "465");  
//	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
//	    props.put("mail.smtp.socketFactory.fallback", "false");  
//	    Session session = Session.getDefaultInstance(props,  
//	    new javax.mail.Authenticator() {
//	       protected PasswordAuthentication getPasswordAuthentication() {  
//	       return new PasswordAuthentication(from,password);  
//	   }  
//	   });  
//
//	   //session.setDebug(true);  
//	   Transport transport = session.getTransport();  
//	   InternetAddress addressFrom = new InternetAddress(from);  
//	   
//	   
//	   MimeMessage message = new MimeMessage(session);  
//	   message.setSender(addressFrom);  
//	   message.setSubject(subject);  
//	   message.setContent(msg, "text/plain");  
//	   
//	   message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  
//	   
//	   String suitname = rp.cell_details_1.getStringCellValue();
//	   String caseID = rp.cell_details_2.getStringCellValue();
//	   String stt = rp.cell_details_3.getStringCellValue();
//	   String rm  = rp.cell_details_4.getStringCellValue();
////	   
//	   
//      
//      //  messageBodyPart.setText(" "+suitname+ "|" + " "+caseID+ " | " + " "+stt+" |" + " "+ rm);
//        
//        
//         Multipart multipart = new MimeMultipart();
//         
//         MimeBodyPart messageBodyPart = new MimeBodyPart();
//         messageBodyPart.setText("Notification Report Automation");
//         multipart.addBodyPart(messageBodyPart);
//         MimeBodyPart messageBodyPart1 = new MimeBodyPart();
//         messageBodyPart1.setText("-------------------------------");
//         multipart.addBodyPart(messageBodyPart1);
//         MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//         messageBodyPart2.setText("     Suite Name       |" + "    Testcase_ID      | " + "    Status      |" + "    Remark");
//         multipart.addBodyPart(messageBodyPart2);
//         
//         MimeBodyPart messageBodyPart3 = new MimeBodyPart();
//         messageBodyPart3.setText("------------------------------------------------------------------------------------------");
//         multipart.addBodyPart(messageBodyPart3);
//         
//         MimeBodyPart messageBodyPart4 = new MimeBodyPart();
//         messageBodyPart4.setText("     "+  suitname+"       |" + "      "+caseID+"        | " + "      "+stt+"          |" + "     "+ rm);
//         multipart.addBodyPart(messageBodyPart4);
//         
//         // Part two is attachment
//         
//         String file = rp.destinationFilePath;
//         messageBodyPart = new MimeBodyPart();
//         String filename = file;
//////         DataSource source = new FileDataSource(filename);
//////         messageBodyPart.setDataHandler(new DataHandler(source));
//////         messageBodyPart.setFileName(filename);
//         messageBodyPart.attachFile(filename);
//         multipart.addBodyPart(messageBodyPart);
//         
//
//         // Send the complete message parts
//         message.setContent(multipart );
//	   
//	            
//	   transport.connect();  
//	   Transport.send(message);  
//	   transport.close();
//	}
	
	
	
	
	
//	public static void writeExcelReport(String excelReport_Path, String sheet_Name){
//		try{
//			FileInputStream inputStream2 = new FileInputStream(new File(excelReport_Path));
//			XSSFWorkbook workbook2 = new XSSFWorkbook(inputStream2);
//			XSSFSheet sheet2 = workbook2.getSheet(sheet_Name);
//			
//            Iterator<Row> iterator3 = sheet2.iterator();
//			Cell cell2 = null;
//			int r = 1;
//	        
//	        while (iterator3.hasNext()) {
//	            Row nextRow3 = iterator3.next();
//	            Iterator<Cell> cellIterator3 = nextRow3.cellIterator();
//	             
//	            while (cellIterator3.hasNext()) {
//	                Cell cell3 = cellIterator3.next();
//	                
//	                if(cell3.getColumnIndex()==0){
//	                	if(!cell3.getStringCellValue().equals("ID")){
//	                		if(!cell3.getStringCellValue().equals("")){
//	                			all_tc.add(cell3.getStringCellValue());
//	                		}
//	                	}
//	                }
//	                
//	            }
//	        }
//	        
//	        for(int al = 0; al < all_tc.size(); al++){
//	        	for(int p = 0; p < list_result2.size(); p++){
//	        		if(all_tc.get(al).equals(list_result2.get(p))){
//	        			cell2 = sheet2.getRow(al + 1).getCell(6);
//						cell2.setCellValue(list_result3.get(p));
//	        		}
//	        	}
//	        }
//	        
//			
//			inputStream2.close();
//			FileOutputStream os = new FileOutputStream(excelReport_Path);
//			workbook2.write(os);
//	        workbook2.close();
//	        os.close();
//	        
//		}catch(Exception e){
//			System.out.print("Error excel report: " + e.getMessage());
//		}
//	}
	
	public static void main(String[] args){
//		System.out.println("demo " + args[0]);
//		readExcel();
		for(int i = 0; i < args.length; i++){
			multiple_xls.add(args[i]);
		}
		//readExcel(args[0]);
		readExcel(multiple_xls);
	}
}
