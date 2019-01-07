package main.java.nasaTestSuite;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class Dehum extends Appliance
{
	private boolean powerOn;
	private Date timer;
	private int humidity;//0 means cont
	private int fanSpeed; //0 lo 1 hi
	private int notifications; //0 off 1 on
	private String timeZone;
	private static AndroidDriver driver;
	private String name = "dehum";
	
	
	public Dehum(FrigiDriver frigi) {
		super(frigi);
	}
	

//
//	//Humidity PLUS
//	public void clickHumidPlus() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.humidPlusButton)));
//		WebElement humidPlusElm = d.findByXPath(XPath.humidPlusButton, false, driver);
//		humidPlusElm.click();
//		System.out.println("recently added thinkwait");
//		d.thinkWait();
//	}
//	
//	//Humidity MINUS
//	public void clickHumidMinus() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.humidMinusButton)));
//		WebElement humidPlusElm = d.findByXPath(XPath.humidMinusButton, false, driver);
//		humidPlusElm.click();
//		d.thinkWait();
//	}
//	
//	//Humidity Speed Down
//	public void clickHumidSpeedUp() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.dSpeedUp)));
//		WebElement humidPlusElm = d.findByXPath(XPath.dSpeedUp, false, driver);
//		humidPlusElm.click();
//		d.thinkWait();
//	}
//	
//	//Humidity Speed Down
//	public void clickHumidSpeedDown() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.racSpeedDown)));
//		WebElement humidPlusElm = d.findByXPath(XPath.racSpeedDown, false, driver);
//		humidPlusElm.click();
//		d.thinkWait();
//	}
//	
//	//State
//	public void refreshHumidity() {
//		//THIS IS A TEST
//		
//		//atttempt 3
//		System.out.println("RefreshHumidity: Work in Progress");
//		WebElement elem3 = driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\""+ "CONT" +"\")");
//		System.out.println("CONT: " + d.searchForText("CONT", SIGN_IN_WAIT));
//		boolean contHumidity = d.searchForText("CONT", SIGN_IN_WAIT);
//		if(contHumidity) 
//		{
//			
//		}
//		for(int i = 35; i <= 85; i = i + 5) 
//		{
//			String tempHumidity = String.valueOf(i);
//			System.out.println("Looking for " + tempHumidity);
//			boolean humidityFound = d.searchForText(tempHumidity, SIGN_IN_WAIT);
//			if(humidityFound) 
//			{
//				System.out.println("Humidity Found: " + tempHumidity);
//			}
//		}
//	}
//	
//	public void setDriver(AndroidDriver driver) 
//	{
//		this.driver = driver;
//	}
//
//	public Date getTimer() {
//		return timer;
//	}
//
//	public int getHumidity() {
//		return humidity;
//	}
//
//	public int getFanSpeed() {
//		return fanSpeed;
//	}
//
//
//	public int getNotifications() {
//		return notifications;
//	}
//
//	public String getTimeZone() {
//		return timeZone;
//	}
//
//
//	public void setPowerOn(boolean powerOn) {
//		this.powerOn = powerOn;
//	}
//	
	
	
	
}
