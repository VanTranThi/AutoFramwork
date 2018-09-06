package keywordfw;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;


public class Mail extends ExcelProcess {
	
	static ArrayList<String> list_suitname = new ArrayList<String>();
	public void sendMail(ArrayList<String> list_mail_update, String suitename, ArrayList ar_testsuite ,ArrayList ar_status) {
		// TODO Auto-generated method stub
		//String to = list_mail_update.toString();
		StringBuilder getdata = new StringBuilder(list_mail_update.toString());
						getdata.deleteCharAt(0);
						getdata.deleteCharAt(getdata.length()-1);		
		String to = getdata.toString();
		System.out.println(getdata.toString());
		
		StringBuilder mess = new StringBuilder();
	    String subject = "AUTOMATION RESULT";
	    mess.append("Notification Report Automation" + "<br />");
	    mess.append("<br />");
	    mess.append("             Suite Name                   " + "             Testcase_ID               " + "            Status            "+ "<br />") ;
	    for(int i = 0; i< ar_testsuite.size(); i++){
	    	String[] output = ar_testsuite.get(i).toString().split("_____");
	    mess.append(
	    	"                  "+output[0].toString()+ "                                   " 
			    + "                  "+output[1].toString()+ "                                 "  
				+ "                  "+ar_status.get(i).toString()+"                                 "
			+	"<br />");
	    }
	    String msg = mess.toString();
	    
	  
        // Send the complete message parts
	    final String from ="automation.infor@gmail.com";
	    final  String password ="muvsolution";     
     try{   
    	
	    Properties props = new Properties();  
	    props.setProperty("mail.transport.protocol", "smtp");     
	    props.setProperty("mail.smtp.host", "smtp.gmail.com");  
	    props.put("mail.smtp.auth", "true");  
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.debug", "false");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    props.put("mail.smtp.socketFactory.fallback", "false");  
	    Session session = Session.getDefaultInstance(props,  
	    new javax.mail.Authenticator() {
	       protected PasswordAuthentication getPasswordAuthentication() {  
	       return new PasswordAuthentication(from,password);  
	   }  
	   }); 
	    
	    String file = rp.destinationFilePath;
	    Multipart multipart = new MimeMultipart(); 
        MimeBodyPart messageBodyPart5 = new MimeBodyPart();
        messageBodyPart5.attachFile(file);
        multipart.addBodyPart(messageBodyPart5);
        
	   Transport transport = session.getTransport();  
	   InternetAddress addressFrom = new InternetAddress(from);  

	   MimeMessage message = new MimeMessage(session);  
	   message.setSender(addressFrom);  
	   message.setSubject(subject);  
	   message.setContent(msg,"text/html;charset=utf-8");
	   
	   message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  
	   //Setting Mail
        
//         MimeBodyPart messageBodyPart = new MimeBodyPart();
//         messageBodyPart.setText("Notification Report Automation");
//         
//         multipart.addBodyPart(messageBodyPart);
//         
//         MimeBodyPart messageBodyPart1 = new MimeBodyPart();
//         messageBodyPart1.setText("");
//         multipart.addBodyPart(messageBodyPart1);
//         
//         MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//         messageBodyPart2.setText("                   Suite Name                             " 
//        		 				+ "                   Testcase_ID                            " 
//        		                + "                   Status                                 " );
//         multipart.addBodyPart(messageBodyPart2);
//         
//         MimeBodyPart messageBodyPart3 = new MimeBodyPart();
//         messageBodyPart3.setText("");
//         multipart.addBodyPart(messageBodyPart3);
//         
//         for(int i = 0; i< ar_testsuite.size(); i++){
//         String[] output = ar_testsuite.get(i).toString().split("_____");
//         MimeBodyPart messageBodyPart4 = new MimeBodyPart();
//         messageBodyPart4.setText("                  "+output[0].toString()+ "                                   " 
//        		 			    + "                  "+output[1].toString()+ "                                 "  
//        		 				+ "                  "+ar_status.get(i).toString()+"                                 ");
//         multipart.addBodyPart(messageBodyPart4);
//         } 
         
         // Part two is attachment
//         String file = rp.destinationFilePath;
//         FileDataSource fileDataSource = new FileDataSource(new File(file));
//         DataHandler dataHandler = new DataHandler(fileDataSource);
//         Multipart multipart = new MimeMultipart(); 
//         MimeBodyPart messageBodyPart5 = new MimeBodyPart();
//         messageBodyPart5.setDataHandler(dataHandler);
//         multipart.addBodyPart(messageBodyPart5);
         // Send the complete message parts
//         message.setContent(multipart);
	       
	   transport.connect();  
	   Transport.send(message);  
	   //transport.send(message1); 
	   transport.close();
	}  
     catch(Exception e){
    	 System.out.println("Mail report error: " + e);
    	 
     }
	}
	}	
	





