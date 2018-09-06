package keywordfw;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;

public class Action extends dwCommon {

	static WebDriver driver;
	Logger logger = LogManager.getLogger(Action.class.getName());
	String winHandleBefore;
	static readXLS FilePath = null;
	static readXLS FileResult = null;
	Robot robot;
	int r;
	String result = "Pass";
	String tc_id;
	DesiredCapabilities caps;
	String scenario;
	int checkWebView = 0;
	String msgError;
	//	String startAppium;

	public Action() {
		DOMConfigurator.configure("log4j.xml");
		PropertyConfigurator.configure("log4j.xml");
		try {
			robot = new Robot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		caps = new DesiredCapabilities();
	}
	
	public void setRowNumber(int i){
		r = i;
	}
	
	public int getRowNumber(){
		return r;
	}
	
	public void setTCID(String tcid){
		tc_id = tcid;
	}
	
	public String getTCID(){
		return tc_id;
	}
	
//	public void setDriver(){
//		if(startAppium.equals("on")){
//			AppiumDriver<WebElement> driver;
//		}else{
//			WebDriver driver;
//		}
//	}
	
	public void settingAppium_Capabilities(String deviceName, String udid, String platformName, String platformVersion
			                  , String appPackage, String appActivity, String resetKeyboard, String unicodeKeyboard
			                  , String noReset, String bundleId, String automationName, String configFile, String startIWDP){
		try{
			if(!deviceName.equals("")){
				if(!deviceName.equals("none")){
					caps.setCapability("deviceName", deviceName);
				}
			}
			if(!udid.equals("")){
				if(!udid.equals("none")){
					caps.setCapability("udid", udid);
				}
			}
			if(!platformName.equals("")){
				if(!platformName.equals("none")){
					caps.setCapability("platformName", platformName);
				}
			}
			if(!platformVersion.equals("")){
				if(!platformVersion.equals("none")){
					caps.setCapability("platformVersion", platformVersion);
				}
			}
			if(!appPackage.equals("")){
				if(!appPackage.equals("none")){
					caps.setCapability("appPackage", appPackage);
				}
			}
			if(!appActivity.equals("")){
				if(!appActivity.equals("none")){
					caps.setCapability("appActivity", appActivity);
				}
			}
			if(!resetKeyboard.equals("")){
				if(!resetKeyboard.equals("none")){
					caps.setCapability("resetKeyboard", resetKeyboard);
				}
			}
			if(!unicodeKeyboard.equals("")){
				if(!unicodeKeyboard.equals("none")){
					caps.setCapability("unicodeKeyboard", unicodeKeyboard);
				}
			}
			if(!noReset.equals("")){
				if(!noReset.equals("none")){
					caps.setCapability("noReset", noReset);
				}
			}
			if(!bundleId.equals("")){
				if(!bundleId.equals("none")){
					caps.setCapability("bundleId", bundleId);
				}
			}
			if(!automationName.equals("")){
				if(!automationName.equals("none")){
					caps.setCapability("automationName", automationName);
				}
			}
			if(!configFile.equals("")){
				if(!configFile.equals("none")){
					caps.setCapability("xcodeConfigfile", configFile);
				}
			}
			if(!startIWDP.equals("")){
				if(!startIWDP.equals("none")){
					caps.setCapability("startIWDP", startIWDP);
				}
			}
			
			logger.info(scenario + " - Setting Appium Desired Capabilities");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + e.getMessage());
			msgError = scenario + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
		
	}
	
	public void startAppium(String url, String os){
		try{
			if(os.equals("Android")){
				driver = new AndroidDriver<WebElement>(new URL(url), caps);
			}else if(os.equals("iOS")){
				driver = new IOSDriver<WebElement>(new URL(url), caps);
			}
			logger.info(scenario + " - Open App");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + e.getMessage());
			msgError = scenario + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void changeToWebView(){
		try{
			
			Set<String> availableContexts = ((AppiumDriver<WebElement>) driver).getContextHandles();
			for(String context : availableContexts) {
				if(context.contains("WEBVIEW")){
					// 4.3 Call context() method with the id of the context you want to access and change it to WEBVIEW_1
					//(This puts Appium session into a mode where all commands are interpreted as being intended for automating the web view)
					((AppiumDriver<WebElement>) driver).context(context);
					break;
				}
			}
			checkWebView++;
			if(checkWebView > 1){
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Change to WEBVIEW");
			}
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void changeToNativeApp(){
		try{
			Set<String> availableContexts = ((AppiumDriver<WebElement>) driver).getContextHandles();
			for(String context : availableContexts) {
				if(context.contains("NATIVE_APP")){
					// 4.3 Call context() method with the id of the context you want to access and change it to WEBVIEW_1
					//(This puts Appium session into a mode where all commands are interpreted as being intended for automating the web view)
					((AppiumDriver<WebElement>) driver).context("NATIVE_APP");
					break;
				}
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Change to NATIVEAPP");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void swipeRightToLeft(String typeElement, String[] element){
		try {
			int x1 = 0;
			int x2 = 0;
			int y1 = 0;
			int y2 = 0;
			int startx1 = 0;
			int starty1 = 0;
			int endx1 = 0;
			int endx2 = 0;
			switch (typeElement) {
			case "id":
				x1 = driver.findElement(By.id(element[0])).getLocation().getX();
				y1 = driver.findElement(By.id(element[0])).getLocation().getY();
				if(element.length > 1){
					x2 = driver.findElement(By.id(element[1])).getLocation().getX();
				    y2 = driver.findElement(By.id(element[1])).getLocation().getY();
				}
				break;
			case "class":
				x1 = driver.findElement(By.className(element[0])).getLocation().getX();
				y1 = driver.findElement(By.className(element[0])).getLocation().getY();
				if(element.length > 1){
					x2 = driver.findElement(By.className(element[1])).getLocation().getX();
				    y2 = driver.findElement(By.className(element[1])).getLocation().getY();
				}
				break;
			case "name":
				x1 = driver.findElement(By.name(element[0])).getLocation().getX();
				y1 = driver.findElement(By.name(element[0])).getLocation().getY();
				if(element.length > 1){
					x2 = driver.findElement(By.name(element[1])).getLocation().getX();
				    y2 = driver.findElement(By.name(element[1])).getLocation().getY();
				}
				break;
			case "xpath":
				x1 = driver.findElement(By.xpath(element[0])).getLocation().getX();
				y1 = driver.findElement(By.xpath(element[0])).getLocation().getY();
				if(element.length > 1){
					x2 = driver.findElement(By.xpath(element[1])).getLocation().getX();
				    y2 = driver.findElement(By.xpath(element[1])).getLocation().getY();
				}
				break;
			}
			
			TouchAction touchAction = new TouchAction((AppiumDriver<WebElement>) driver);
			if(element.length == 1){
				startx1 = x1;
				endx1 = x1 + 30;
				starty1 = y1;
		        touchAction.press(startx1, starty1).waitAction(300).moveTo(endx1, starty1).release().perform();
			}else{
				startx1 = x1;
				endx2 = x2;
				starty1 = y1;
				touchAction.press(startx1, starty1).waitAction(300).moveTo(endx2, starty1).release().perform();
			}
			
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Swipe right to left ");
		} catch (Exception e) {
			result = "Fail";
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
			driver.quit();
		}
	}

	public void openBrowser(String url, String browser) {
		try {
			if (browser.equals("Chrome")) {
				driver = new ChromeDriver();
				driver.get(url);
				driver.manage().window().maximize();
			}
			logger.info("############################################################################");
			logger.info(scenario + " - " + " - Open " + url);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + " - " + e.getMessage());
			msgError = scenario + " - " + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void getPageSource(){
		try{
			String page_source = driver.getPageSource();
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Get page source");
			logger.info(page_source);
		}catch(Exception e){
			result = "Fail";
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
			driver.quit();
		}
	}

	public void inputData(String typeElement, String element, String text) {
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				element_1.sendKeys(text);
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				element_2.sendKeys(text);
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				element_3.sendKeys(text);
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				element_4.sendKeys(text);
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				element_5.sendKeys(text);
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Input " + text);
		} catch (Exception e) {
			result = "Fail";
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
			driver.quit();
		}
	}

	public void clickElement(String typeElement, String element) {
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				element_1.click();
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				element_2.click();
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				element_3.click();
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				element_4.click();
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				element_5.click();
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void waitElementVisible(String typeElement, String element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			switch (typeElement) {
			case "id":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element)));
				break;
			case "class":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));
				break;
			case "name":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
				break;
			case "xpath":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
				break;
			case "css":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element)));
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void waitElementClickable(String typeElement, String element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			switch (typeElement) {
			case "id":
				wait.until(ExpectedConditions.elementToBeClickable(By.id(element)));
				break;
			case "class":
				wait.until(ExpectedConditions.elementToBeClickable(By.className(element)));
				break;
			case "name":
				wait.until(ExpectedConditions.elementToBeClickable(By.name(element)));
				break;
			case "xpath":
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
				break;
			case "css":
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(element)));
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element + " clickable");
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void Clear(String typeElement, String element) {
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				element_1.clear();
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				element_2.clear();
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				element_3.clear();
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				element_4.clear();
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				element_5.clear();
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Clear data at element " + element);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void selectDDByIndex(String typeElement, String element, int index) {
		try {
			switch (typeElement) {
			case "id":
				Select element_1 = new Select(driver.findElement(By.id(element)));
				element_1.selectByIndex(index);
				break;
			case "class":
				Select element_2 = new Select(driver.findElement(By.className(element)));
				element_2.selectByIndex(index);
				break;
			case "name":
				Select element_3 = new Select(driver.findElement(By.name(element)));
				element_3.selectByIndex(index);
				break;
			case "xpath":
				Select element_4 = new Select(driver.findElement(By.xpath(element)));
				element_4.selectByIndex(index);
				break;
			case "css":
				Select element_5 = new Select(driver.findElement(By.cssSelector(element)));
				element_5.selectByIndex(index);
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Select data by index " + index);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void switchNewWindow() {
		try {
			winHandleBefore = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Switched to new window");
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void switchParentWindow() {
		try {
			driver.switchTo().window(winHandleBefore);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Switched to parent window");
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void Sleep(int timeSleep) throws InterruptedException {
		Thread.sleep(timeSleep);
		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Sleep " + timeSleep);
	}

	public void verifyResult(String typeElement, String element, String text_expected) {
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				if (element_1.getText().equals(text_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " OK");
					logger.info("   Actual:" + element_1.getText());
					logger.info("   Expected:" + text_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_1.getText());
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				if (element_2.getText().equals(text_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " OK");
					logger.info("   Actual:" + element_2.getText());
					logger.info("   Expected:" + text_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_2.getText());
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				if (element_3.getText().equals(text_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " OK");
					logger.info("   Actual:" + element_3.getText());
					logger.info("   Expected:" + text_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_3.getText());
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				if (element_4.getText().equals(text_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " OK");
					logger.info("   Actual:" + element_4.getText());
					logger.info("   Expected:" + text_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_4.getText());
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				if (element_5.getText().equals(text_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " OK");
					logger.info("   Actual:" + element_5.getText());
					logger.info("   Expected:" + text_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_5.getText());
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public void verifyElementExist(String typeElement, String element) {
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				if (element_1.isDisplayed()) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is existed OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				if (element_2.isDisplayed()) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is existed OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				if (element_3.isDisplayed()) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is existed OK");
				} else {
					//result = "Fail";
					logger.error(getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				if (element_4.isDisplayed()) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is existed OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				if (element_5.isDisplayed()) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is existed OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " is not existed";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void verifyElementNotExist(String typeElement, String element){
		try {
			switch (typeElement) {
			case "id":
				Boolean exist = driver.findElements(By.id(element)).size() == 0;
				if (exist.equals(true)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "class":
				Boolean exist2 = driver.findElements(By.className(element)).size() == 0;
				if (exist2.equals(true)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "name":
				Boolean exist3 = driver.findElements(By.name(element)).size() == 0;
				if (exist3.equals(true)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist OK");
				} else {
					//result = "Fail";
					logger.error(getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "xpath":
				Boolean exist4 = driver.findElements(By.xpath(element)).size() == 0;
				if (exist4.equals(true)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				if (element_5.isDisplayed()) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist OK");
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify element: " + element + " not exist NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void deleteValueUseKeyboard(String typeElement, String element){
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				int count = element_1.getAttribute("value").length();
				for(int i = 0; i <= count; i++){
				    element_1.sendKeys(Keys.BACK_SPACE);
				}
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				int count2 = element_2.getAttribute("value").length();
				for(int i = 0; i <= count2; i++){
				    element_2.sendKeys(Keys.BACK_SPACE);
				}
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				int count3 = element_3.getAttribute("value").length();
				for(int i = 0; i <= count3; i++){
				    element_3.sendKeys(Keys.BACK_SPACE);
				}
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				int count4 = element_4.getAttribute("value").length();
				for(int i = 0; i <= count4; i++){
				    element_4.sendKeys(Keys.BACK_SPACE);
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				int count5 = element_5.getAttribute("value").length();
				for(int i = 0; i <= count5; i++){
				    element_5.sendKeys(Keys.BACK_SPACE);
				}
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Delete Value Use Keyboard");
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
    }
	
	public void chooseImageNativeByIndex(String typeElement, String element, int index){
		try {
			switch (typeElement) {
			case "id":
				String[] p = element.split(",");
				List<WebElement> childElements = driver.findElements(By.id(p[0]));
				WebElement mainElement = childElements.get(index);
				mainElement.findElement(By.className(p[1])).click();
				break;
			case "class":
				String[] p2 = element.split(",");
				List<WebElement> childElements2 = driver.findElements(By.className(p2[0]));
				WebElement mainElement2 = childElements2.get(index);
				mainElement2.findElement(By.className(p2[1])).click();
				break;
			case "name":
				String[] p3 = element.split(",");
				List<WebElement> childElements3 = driver.findElements(By.name(p3[0]));
				WebElement mainElement3 = childElements3.get(index);
				mainElement3.findElement(By.className(p3[1])).click();
				break;
			case "xpath":
				String[] p4 = element.split(",");
				List<WebElement> childElements4 = driver.findElements(By.xpath(p4[0]));
				WebElement mainElement4 = childElements4.get(index);
				mainElement4.findElement(By.className(p4[1])).click();
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Choose image native by index " + index);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void verifyElementExistByIndex(String typeElement, String element, int index) {
		try {
			switch (typeElement) {
			case "id":
				int i = 0;
				List<WebElement> links = driver.findElements(By.id(element));
				Iterator<WebElement> iter = links.iterator();
				while(iter.hasNext()) {
				    WebElement we = iter.next();
				    if(i == index){
				    	if(we.isDisplayed()){
				    		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is existed OK");
				    	}else{
				    		//result = "Fail";
				    		logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed");
				    		msgError = getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed";
							String[] array = msgError.split("\\(", -1);
							msgError = array[0];
							//driver.quit();
				    	}
				    }
				    i++;
				}
				break;			
			case "class":
				int y = 0;
				List<WebElement> links2 = driver.findElements(By.className(element));
				Iterator<WebElement> iter2 = links2.iterator();
				while(iter2.hasNext()) {
				    WebElement we = iter2.next();
				    if(y == index){
				    	if(we.isDisplayed()){
				    		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is existed OK");
				    	}else{
				    		//result = "Fail";
				    		logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed");
				    		msgError = getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed";
							String[] array = msgError.split("\\(", -1);
							msgError = array[0];
							//driver.quit();
				    	}
				    }
				    y++;
				}
				break;			
			case "name":
				int m = 0;
				List<WebElement> links3 = driver.findElements(By.name(element));
				Iterator<WebElement> iter3 = links3.iterator();
				while(iter3.hasNext()) {
				    WebElement we = iter3.next();
				    if(m == index){
				    	if(we.isDisplayed()){
				    		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is existed OK");
				    	}else{
				    		//result = "Fail";
				    		logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed");
				    		msgError = getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed";
							String[] array = msgError.split("\\(", -1);
							msgError = array[0];
							//driver.quit();
				    	}
				    }
				    m++;
				}
				break;		
			case "xpath":
				int n = 0;
				List<WebElement> links4 = driver.findElements(By.xpath(element));
				Iterator<WebElement> iter4 = links4.iterator();
				while(iter4.hasNext()) {
				    WebElement we = iter4.next();
				    if(n == index){
				    	if(we.isDisplayed()){
				    		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is existed OK");
				    	}else{
				    		//result = "Fail";
				    		logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed");
				    		msgError = getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed";
							String[] array = msgError.split("\\(", -1);
							msgError = array[0];
							//driver.quit();
				    	}
				    }
				    n++;
				}
				break;
			case "css":
				int v = 0;
				List<WebElement> links5 = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter5 = links5.iterator();
				while(iter5.hasNext()) {
				    WebElement we = iter5.next();
				    if(v == index){
				    	if(we.isDisplayed()){
				    		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is existed OK");
				    	}else{
				    		//result = "Fail";
				    		logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed");
				    		msgError = getTCID() + " - "+ getRowNumber() + " - Verify by index element: " + element + " is not existed";
							String[] array = msgError.split("\\(", -1);
							msgError = array[0];
							//driver.quit();
				    	}
				    }
				    v++;
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void verifyBackgroundColor(String typeElement, String element, String background_color_expected) {
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				if (element_1.getText().equals(background_color_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " OK");
					logger.info("   Actual:" + element_1.getText());
					logger.info("   Expected:" + background_color_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK");
					logger.error("   Actual:" + element_1.getText());
					logger.error("   Expected:" + background_color_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				if (element_2.getText().equals(background_color_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " OK");
					logger.info("   Actual:" + element_2.getText());
					logger.info("   Expected:" + background_color_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK");
					logger.error("   Actual:" + element_2.getText());
					logger.error("   Expected:" + background_color_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				if (element_3.getText().equals(background_color_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " OK");
					logger.info("   Actual:" + element_3.getText());
					logger.info("   Expected:" + background_color_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK");
					logger.error("   Actual:" + element_3.getText());
					logger.error("   Expected:" + background_color_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				if (element_4.getCssValue("background-color").equals(background_color_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify background-color: " + background_color_expected + " OK");
					logger.info("   Actual:" + element_4.getCssValue("background-color"));
					logger.info("   Expected:" + background_color_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify background-color: " + background_color_expected + " NOT OK");
					logger.error("   Actual:" + element_4.getCssValue("background-color"));
					logger.error("   Expected:" + background_color_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				if (element_5.getText().equals(background_color_expected)) {
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " OK");
					logger.info("   Actual:" + element_5.getText());
					logger.info("   Expected:" + background_color_expected);
				} else {
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK");
					logger.error("   Actual:" + element_5.getText());
					logger.error("   Expected:" + background_color_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify text: " + background_color_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void moveToElement(String typeElement, String element){
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_1);
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Move to element: " + element_1);
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_2);
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Move to element: " + element_2);
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_3);
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Move to element: " + element_3);
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_4);
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Move to element: " + element_4);
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_5);
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Move to element: " + element_5);
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pasteText(String text_input){
		try{
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection stringSelection = new StringSelection(text_input);
			clipboard.setContents(stringSelection, null);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Paste text: " + text_input);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressEnter(){
		try{
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press Enter");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void verifyImage(String typeElement, String element){
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				URL obj = new URL(element_1.getAttribute("src"));
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				int responseCode = con.getResponseCode();
				if(responseCode == 200){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " OK");
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				URL obj2 = new URL(element_2.getAttribute("src"));
				HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
				con2.setRequestMethod("GET");
				int responseCode2 = con2.getResponseCode();
				if(responseCode2 == 200){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " OK");
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				URL obj3 = new URL(element_3.getAttribute("src"));
				HttpURLConnection con3 = (HttpURLConnection) obj3.openConnection();
				con3.setRequestMethod("GET");
				int responseCode3 = con3.getResponseCode();
				if(responseCode3 == 200){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " OK");
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				URL obj4 = new URL(element_4.getAttribute("src"));
				HttpURLConnection con4 = (HttpURLConnection) obj4.openConnection();
				con4.setRequestMethod("GET");
				int responseCode4 = con4.getResponseCode();
				if(responseCode4 == 200){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " OK");
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				URL obj5 = new URL(element_5.getAttribute("src"));
				HttpURLConnection con5 = (HttpURLConnection) obj5.openConnection();
				con5.setRequestMethod("GET");
				int responseCode5 = con5.getResponseCode();
				if(responseCode5 == 200){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " OK");
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK");
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify image element " + element + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void verifyAttributeValue(String typeElement, String element, String text_expected){
		try {
			switch (typeElement) {
			case "id":
				WebElement element_1 = driver.findElement(By.id(element));
				if(element_1.getAttribute("value").equals(text_expected)){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + element + " is OK");
					logger.info("   Actual:" + element_1.getAttribute("value"));
					logger.info("   Expected:" + text_expected);
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_1.getAttribute("value"));
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "class":
				WebElement element_2 = driver.findElement(By.className(element));
				if(element_2.getAttribute("value").equals(text_expected)){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + element + " is OK");
					logger.info("   Actual:" + element_2.getAttribute("value"));
					logger.info("   Expected:" + text_expected);
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_2.getAttribute("value"));
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "name":
				WebElement element_3 = driver.findElement(By.name(element));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_3);
				if(element_3.getAttribute("value").equals(text_expected)){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + element + " is OK");
					logger.info("   Actual:" + element_3.getAttribute("value"));
					logger.info("   Expected:" + text_expected);
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_3.getAttribute("value"));
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "xpath":
				WebElement element_4 = driver.findElement(By.xpath(element));
				if(element_4.getAttribute("value").equals(text_expected)){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + element + " is OK");
					logger.info("   Actual:" + element_4.getAttribute("value"));
					logger.info("   Expected:" + text_expected);
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_4.getAttribute("value"));
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			case "css":
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				if(element_5.getAttribute("value").equals(text_expected)){
					logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + element + " is OK");
					logger.info("   Actual:" + element_5.getAttribute("value"));
					logger.info("   Expected:" + text_expected);
				}else{
					//result = "Fail";
					logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK");
					logger.error("   Actual:" + element_5.getAttribute("value"));
					logger.error("   Expected:" + text_expected);
					msgError = getTCID() + " - "+ getRowNumber() + " - Verify value: " + text_expected + " NOT OK";
					String[] array = msgError.split("\\(", -1);
					msgError = array[0];
					//driver.quit();
				}
				break;
			}
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void captureWindow(String imagePath){
		try{
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(imagePath));
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Capture image & save at: " + imagePath);
		}catch(Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void refreshBrowser(){
		try{
			driver.navigate().refresh();
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Refresh browser");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void waitTextVisible(String typeElement, String element, int timeout){
		try {
			By byXpath = By.xpath(element); 
			WebElement myDynamicElement = (new WebDriverWait(driver, timeout))
			  .until(ExpectedConditions.presenceOfElementLocated(byXpath));
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait text " + element + " display");
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressDown(){
		try{
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press Down");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void clickByIndex(String typeElement, String element, int index){
		try{
			int i = 0;
			int y = 0;
			switch (typeElement) {
			case "css":
				List<WebElement> links = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter = links.iterator();

				while(iter.hasNext()) {
				    WebElement we = iter.next();
				    if(i == index){
				    	we.click();
				    }
				    i++;
				}
				break;
				
			case "class":
				List<WebElement> links2 = driver.findElements(By.className(element));
				Iterator<WebElement> iter2 = links2.iterator();

				while(iter2.hasNext()) {
				    WebElement we2 = iter2.next();
				    if(i == index){
				    	we2.click();
				    }
				    i++;
				}
				break;
				
			case "xpath":
				List<WebElement> links3 = driver.findElements(By.xpath(element));
				Iterator<WebElement> iter3 = links3.iterator();

				while(iter3.hasNext()) {
				    WebElement we2 = iter3.next();
				    if(i == index){
				    	we2.click();
				    }
				    i++;
				}
				break;
				
			case "id":
				List<WebElement> links4 = driver.findElements(By.id(element));
				Iterator<WebElement> iter4 = links4.iterator();

				while(iter4.hasNext()) {
				    WebElement we2 = iter4.next();
				    if(i == index){
				    	we2.click();
				    }
				    i++;
				}
				break;
				
			case "name":
				List<WebElement> links5 = driver.findElements(By.name(element));
				Iterator<WebElement> iter5 = links5.iterator();

				while(iter5.hasNext()) {
				    WebElement we2 = iter5.next();
				    if(i == index){
				    	we2.click();
				    }
				    i++;
				}
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click by index " + element);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void clickByText(String typeElement, String element, String text){
		try{
			switch (typeElement) {
			case "css":
				List<WebElement> links = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter = links.iterator();

				while(iter.hasNext()) {
				    WebElement we = iter.next();
				    if(we.equals(text)){
				    	we.click();
				    	break;
				    }
				}
				break;
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click by text " + element);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void moveToElementByIndex(String typeElement, String element, int index){
		try {
			int i = 0;
			switch (typeElement) {
			case "css":
				List<WebElement> links = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter = links.iterator();

				while(iter.hasNext()) {
				    WebElement we = iter.next();
				    if(i == index){
				    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
				    }
				    i++;
				}
				break;
			case "xpath":
				List<WebElement> links2 = driver.findElements(By.xpath(element));
				Iterator<WebElement> iter2 = links2.iterator();

				while(iter2.hasNext()) {
				    WebElement we = iter2.next();
				    if(i == index){
				    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
				    }
				    i++;
				}
				break;
		    case "id":
			    List<WebElement> links3 = driver.findElements(By.id(element));
			    Iterator<WebElement> iter3 = links3.iterator();

			    while(iter3.hasNext()) {
			    	WebElement we = iter3.next();
			        if(i == index){
			        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
			        }
			        i++;
			}
			break;
		    case "name":
			    List<WebElement> links4 = driver.findElements(By.name(element));
			    Iterator<WebElement> iter4 = links4.iterator();

			    while(iter4.hasNext()) {
			    	WebElement we = iter4.next();
			        if(i == index){
			        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
			        }
			        i++;
			}
			break;
		    case "class":
			    List<WebElement> links5 = driver.findElements(By.className(element));
			    Iterator<WebElement> iter5 = links5.iterator();

			    while(iter5.hasNext()) {
			    	WebElement we = iter5.next();
			        if(i == index){
			        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
			        }
			        i++;
			}
			break;
		}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Move to element by index: " + element);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
//	public void inputDataByIndex(String typeElement, String element, String value, int index) {
//		try{
//			int i = 0;
//			switch (typeElement) {
//			case "css":
//				List<WebElement> links = driver.findElements(By.cssSelector(element));
//				Iterator<WebElement> iter = links.iterator();
//
//				while(iter.hasNext()) {
//				    WebElement we = iter.next();
//				    if(i == index){
//				    	we.sendKeys(value);;
//				    }
//				    i++;
//				}
//				break;
//			}
//			logger.info(getTCID() + " - "+ getRowNumber() + " - Click by index " + element);
//		}catch(Exception e){
//			result = "Fail";
//			driver.quit();
//			logger.error(getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
//		}
//	}
	
	public void pressBackspace(){
		try{
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press Backspace");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	//static keyword
	public void clickToEndItemNextAuction(){
		try{
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 2);
			dt = c.getTime();
			DateFormat df = new SimpleDateFormat("d MMM yyyy", Locale.US);
			String date = df.format(dt);
			
			WebDriverWait wait = new WebDriverWait(driver, 5);
			WebElement element_5 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.right-icon.button-visible")));
			element_5.click();
			WebElement element_8 = driver.findElement(By.cssSelector("div.right-icon.button-visible"));
			for(int i = 0; i < 100; i++){
				if(element_8.isDisplayed()){
					element_8.click();
				}else{
					break;
				}
				
			}
			
			WebElement element_6 = driver.findElement(By.cssSelector("div.left-icon.button-visible"));
			for(int i = 0; i < 100; i++){
				if(element_6.isDisplayed()){
					element_6.click();
					WebElement element_7 = driver.findElement(By.cssSelector("div.calendar div")); 
					if(element_7.getText().equals(date.toUpperCase())){
						break;
					}
				}else{
					break;
				}
			}
			
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click to end item at Next Auction area");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	//static keyword
	public void clickAuctionList(String typeElement, String element){
		try{
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 2);
			dt = c.getTime();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String date = df.format(dt);
			
			int i = 6;
			int y = 0;
			switch (typeElement) {
			case "css":	
				List<WebElement> links = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter = links.iterator();
				
				List<WebElement> links2 = driver.findElements(By.cssSelector(".material-icons"));
				List<WebElement> links3 = driver.findElements(By.cssSelector("span[data-badge-caption='publish']"));
				List<WebElement> links4 = driver.findElements(By.cssSelector("div.col.s3.truncate"));
				Iterator<WebElement> iter4 = links4.iterator();

				List<WebElement> links5 = driver.findElements(By.cssSelector("span[data-badge-caption='new']"));
				
//				while(iter.hasNext()) {
//				    WebElement we = iter.next();
//				    if(links3.get(y).isDisplayed()){
//				    	links3.get(y).click();
//				    	i = i + 7;
//				    }
//				    if(we.isDisplayed()){
//				    	we.click();
//				    	Thread.sleep(2000);
//				    	System.out.println("i se la : " + i);
//					    links2.get(i).click();
//					    i = i + 7;
//					    break;
//				    }
//				    y++;
//				    
//				}
				
				while(iter4.hasNext()) {
					WebElement we4 = iter4.next();
					//System.out.println("Du lieu la: " + we4.getText());
				    if(we4.getText().contains(date)){
				    	y++;
				    }
				}
				
				//System.out.println("y la: " + y);
				
				for(int n = 0; n < y; n++){
					if(n == y - 1){
						int x = (7 * n) + 6;
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", links4.get(n));
						links4.get(n).click();
						Thread.sleep(2000);
						links2.get(x).click();
					}
				}
				
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click auction: " + element);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	//static keyword
	public void publishAuction(String typeElement, String element){
		try{
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 2);
			dt = c.getTime();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String date = df.format(dt);
			
			int i = 6;
			int y = 0;
			switch (typeElement) {
			case "css":	
				List<WebElement> links = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter = links.iterator();
				
				List<WebElement> links2 = driver.findElements(By.cssSelector(".material-icons"));
				List<WebElement> links3 = driver.findElements(By.cssSelector("span[data-badge-caption='publish']"));
				List<WebElement> links4 = driver.findElements(By.cssSelector("div.col.s3.truncate"));
				Iterator<WebElement> iter4 = links4.iterator();

				List<WebElement> links5 = driver.findElements(By.cssSelector("span[data-badge-caption='new']"));
				
//				while(iter.hasNext()) {
//				    WebElement we = iter.next();
//				    if(links3.get(y).isDisplayed()){
//				    	links3.get(y).click();
//				    	i = i + 7;
//				    }
//				    if(we.isDisplayed()){
//				    	we.click();
//				    	Thread.sleep(2000);
//				    	System.out.println("i se la : " + i);
//					    links2.get(i).click();
//					    i = i + 7;
//					    break;
//				    }
//				    y++;
//				    
//				}
				
				while(iter4.hasNext()) {
					WebElement we4 = iter4.next();
				    if(we4.getText().contains(date)){
				    	y++;
				    }
				}
				
				for(int n = 0; n < y; n++){
					if(n == y - 1){
						int x = (7 * n) + 7;
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", links4.get(n));
						links4.get(n).click();
						Thread.sleep(2000);
						links2.get(x).click();
					}
				}
				
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Publish auction: " + element);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	//static keyword
	public void deleteAuctionNotPublish(String typeElement, String element){
		try{
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 2);
			dt = c.getTime();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String date = df.format(dt);
			
			int i = 6;
			int y = 0;
			switch (typeElement) {
			case "css":	
				List<WebElement> links = driver.findElements(By.cssSelector(element));
				Iterator<WebElement> iter = links.iterator();
				
				List<WebElement> links2 = driver.findElements(By.cssSelector(".material-icons"));
				List<WebElement> links3 = driver.findElements(By.cssSelector("span[data-badge-caption='publish']"));
				List<WebElement> links4 = driver.findElements(By.cssSelector("div.col.s3.truncate"));
				Iterator<WebElement> iter4 = links4.iterator();

				List<WebElement> links5 = driver.findElements(By.cssSelector("span[data-badge-caption='new']"));
				
				WebElement element_delete = driver.findElement(By.cssSelector("div.toolbar > div > ul > li:nth-child(3) > a"));
				WebElement button_yes = driver.findElement(By.cssSelector("#modal-deleteSchedule > form > div.modal-footer > button:nth-child(2)"));
				
				while(iter4.hasNext()) {
					WebElement we4 = iter4.next();
				    if(we4.getText().contains(date)){
				    	y++;
				    }
				}
				
				for(int n = 0; n < y; n++){
					if(n == y - 1){
						int x = (7 * n) + 7;
						links4.get(n).click();
						Thread.sleep(4000);
						element_delete.click();
						Thread.sleep(2000);
						button_yes.click();
					}
				}
				
			}
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Delete auction: " + element);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressRightByAction(){
		try{
			Actions ac_builder = new Actions(driver);
			ac_builder.sendKeys(Keys.ARROW_RIGHT).build().perform();
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press right key by action");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressEnterByAction(){
		try{
			Actions ac_builder = new Actions(driver);
			ac_builder.sendKeys(Keys.ENTER).build().perform();
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press enter key by action");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressDownByAction(){
		try{
			Actions ac_builder = new Actions(driver);
			ac_builder.sendKeys(Keys.ARROW_DOWN).build().perform();
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press down key by action");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressTabByAction(){
		try{
			Actions ac_builder = new Actions(driver);
			ac_builder.sendKeys(Keys.TAB).build().perform();
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press tab key by action");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void goToURL(String url){
		try{
			driver.navigate().to(url);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Navigate to " + url);
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void pressEsc(){
		try{
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Press Escape");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void closeTab(){
		try{
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Close tab");
		}catch(Exception e){
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void closeBrowser() {
		driver.quit();
		logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Close browser ");
	}

//	public String verifyResult(String typeElement, String valueElement, String expected) {
//		String result = null;
//		try{
//			switch (typeElement) {
//			case "css":
//				String actual1 = driver.findElement(By.cssSelector(valueElement)).getText();
//				if (actual1.equals(expected)) {
//					result = "Pass";
//					logger.info("Verify text: " + expected + " OK");
//				} else {
//					result = "Fail";
//					logger.info("Verify text: " + expected + " NOT OK");
//				}
//				break;
//			case "xpath":
//				String actual2 = driver.findElement(By.xpath(valueElement)).getText();
//				if (actual2.equals(expected)) {
//					result = "Pass";
//					logger.info("Verify text: " + expected + " OK");
//				} else {
//					result = "Fail";
//					logger.info("Verify text: " + expected + " NOT OK");
//				}
//				break;
//			case "id":
//				String actual3 = driver.findElement(By.id(valueElement)).getText();
//				if (actual3.equals(expected)) {
//					result = "Pass";
//					logger.info("Verify text: " + expected + " OK");
//				} else {
//					result = "Fail";
//					logger.info("Verify text: " + expected + " NOT OK");
//				}
//				break;
//			case "class":
//				String actual4 = driver.findElement(By.className(valueElement)).getText();
//				if (actual4.equals(expected)) {
//					result = "Pass";
//					logger.info("Verify text: " + expected + " OK");
//				} else {
//					result = "Fail";
//					logger.info("Verify text: " + expected + " NOT OK");
//				}
//				break;
//			case "name":
//				String actual5 = driver.findElement(By.name(valueElement)).getText();
//				if (actual5.equals(expected)) {
//					result = "Pass";
//					logger.info("Verify text: " + expected + " OK");
//				} else {
//					result = "Fail";
//					logger.info("Verify text: " + expected + " NOT OK");
//				}
//				break;
//			}
//		}catch(Exception e){
//			driver.quit();
//			logger.error(e.getMessage());
//		}
//		
//		return result;
//	}
	
	public void waitElementVisibleWithoutQuit(String typeElement, String element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			switch (typeElement) {
			case "id":
				try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(element)));
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element + " Element not found. Skip.");
				}
				break;
			case "class":
				try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element + " Element not found. Skip.");	
				}
				break;
			case "name":
				try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element + " Element not found. Skip.");
				}
				break;
			case "xpath":
				try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element + " Element not found. Skip.");	
				}
				break;
			case "css":
				try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element)));
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element + " Element not found. Skip.");	
				}
				break;
			}
			//logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Wait " + element);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}
	
	public void clickElementWithoutQuit(String typeElement, String element) {
		try {
			switch (typeElement) {
			case "id":
				try {
				WebElement element_1 = driver.findElement(By.id(element));
				element_1.click();
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element + " Element not found. Skip.");	
				}
				break;
			case "class":
				try {
				WebElement element_2 = driver.findElement(By.className(element));
				element_2.click();
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element + " Element not found. Skip.");	
				}
				break;
			case "name":
				try {
				WebElement element_3 = driver.findElement(By.name(element));
				element_3.click();
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element + " Element not found. Skip.");	
				}
				break;
			case "xpath":
				try {
				WebElement element_4 = driver.findElement(By.xpath(element));
				element_4.click();
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element + " Element not found. Skip.");	
				}
				break;
			case "css":
				try {
				WebElement element_5 = driver.findElement(By.cssSelector(element));
				element_5.click();
				logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
				}
				catch (Exception ex)  {
				logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element + " Element not found. Skip.");	
				}
				break;
			}
			//logger.info(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - Click " + element);
		} catch (Exception e) {
			result = "Fail";
			driver.quit();
			logger.error(scenario + " - " + getTCID() + " - "+ getRowNumber() + " - " + e.getMessage());
			msgError = getTCID() + " - "+ getRowNumber() + " - " + e.getMessage();
			String[] array = msgError.split("\\(", -1);
			msgError = array[0];
		}
	}

	public static void writeResult(String result, String id) throws Exception {
		init();
		FilePath = HANDLE_READ_XLS;
		initResult(handleXLS.GetDataToExcel(FilePath, URL_WS_SUMMARY, FIELD_DESCRIPTION, FIELD_TESTCASE));
		FileResult = HANDLE_WRITE_XLS;
		System.out.println(FileResult + " " + SHEET_NAME + " " + FIELD_STATUS + " " + id + " " + result);
		handleXLS.WriteResultUtility(FileResult, SHEET_NAME, FIELD_STATUS, id, result);
	}

	public void sendMail() throws Exception {
		// TODO Auto-generated method stub
		String to = "khailt.cntt@gmail.com";
	    String subject = "AUTOMATION RESULT";
	    String msg ="email text....";
	    final String from ="automation.infor@gmail.com";
	    final  String password ="muvsolution";


	    Properties props = new Properties();  
	    props.setProperty("mail.transport.protocol", "smtp");     
	    props.setProperty("mail.smtp.host", "smtp.gmail.com");  
	    props.put("mail.smtp.auth", "true");  
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.debug", "true");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    props.put("mail.smtp.socketFactory.fallback", "false");  
	    Session session = Session.getDefaultInstance(props,  
	    new javax.mail.Authenticator() {
	       protected PasswordAuthentication getPasswordAuthentication() {  
	       return new PasswordAuthentication(from,password);  
	   }  
	   });  

	   //session.setDebug(true);  
	   Transport transport = session.getTransport();  
	   InternetAddress addressFrom = new InternetAddress(from);  
	   Report r = new Report();
	   
	   
	   MimeMessage message = new MimeMessage(session);  
	   message.setSender(addressFrom);  
	   message.setSubject(subject);  
	   message.setContent(msg, "text/plain");  
	   
	   message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  
	   MimeBodyPart messageBodyPart = new MimeBodyPart();
//	   String suitname = r.cell_details_1.getStringCellValue();
//	   String caseID = r.cell_details_2.getStringCellValue();
//	   String stt = r.cell_details_3.getStringCellValue();
//	   String rm  = r.cell_details_4.getStringCellValue();
//	   
	   
        messageBodyPart.setText("Notification Report Automation");
        messageBodyPart.setText("-------------------------------");
        messageBodyPart.setText("  Suite Name  |" + " Testcase_ID | " + " Status |" + " Remark");
      //  messageBodyPart.setText(" "+suitname+ "|" + " "+caseID+ " | " + " "+stt+" |" + " "+ rm);
        
        
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         
//         String file = r.destinationFilePath;
//         messageBodyPart = new MimeBodyPart();
//         String filename = file;
////         DataSource source = new FileDataSource(filename);
////         messageBodyPart.setDataHandler(new DataHandler(source));
////         messageBodyPart.setFileName(filename);
//         messageBodyPart.attachFile(filename);
//         multipart.addBodyPart(messageBodyPart);
         

         // Send the complete message parts
         message.setContent(multipart );
	   
	            
	   transport.connect();  
	   Transport.send(message);  
	   transport.close();
	}
}
