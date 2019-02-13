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

	public int getTargHumidity() 
	{
		int targHumidity = -1;
		try {
			targHumidity = Integer.parseInt(d.findByXPath(XPath.dehumTargetHumidity, BUTTON_WAIT).getAttribute("data-value"));
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return targHumidity;
	}

	//humid PLUS
	public void clickHumidPlus() 
	{
		WebElement element = d.findByXPath(XPath.dehumHumidityUp, BUTTON_WAIT);
		element.click();
		d.thinkWait();
	}
	
	//humid MINUS
	public void clickHumidMinus() 
	{
		WebElement element = d.findByXPath(XPath.dehumHumidityDown, BUTTON_WAIT);
		element.click();
		d.thinkWait();
	}
	
	/**
	 * Returns the expected fan speed after input. Copied from Stromboli code hence the switch statement.
	 * @return
	 */
	public int getNextExpectedSpeed() {
		int speed = -1;
		speed = Integer.parseInt(d.findByXPath(XPath.currentFanSpeed, BUTTON_WAIT).getAttribute("data-value"));
		System.out.println("Speed by data value" + speed);
		int nextExpectedSpeed = -1;
		switch (speed) {
	        case 1:  nextExpectedSpeed = 4;
	        	break;
	        case 4:  nextExpectedSpeed = 1;
	        	break;
			default: System.out.println("Unknown speed: " + speed);
				break;
		}
		return nextExpectedSpeed;
	}

	//Can copy the other method since there are only two options
	public int getPrevExpectedSpeed() {
		return getNextExpectedSpeed();
	}
//OUTDATED CODEvvvvvv
//	//Humidity PLUS
//	public void clickHumidPlus() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.humidPlusButton)));
//		WebElement humidPlusElm = d.findByXPath(MyXPath.humidPlusButton, false, driver);
//		humidPlusElm.click();
//		System.out.println("recently added thinkwait");
//		d.thinkWait();
//	}
//	
//	//Humidity MINUS
//	public void clickHumidMinus() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.humidMinusButton)));
//		WebElement humidPlusElm = d.findByXPath(MyXPath.humidMinusButton, false, driver);
//		humidPlusElm.click();
//		d.thinkWait();
//	}
//	
//	//Humidity Speed Down
//	public void clickHumidSpeedUp() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.dSpeedUp)));
//		WebElement humidPlusElm = d.findByXPath(MyXPath.dSpeedUp, false, driver);
//		humidPlusElm.click();
//		d.thinkWait();
//	}
//	
//	//Humidity Speed Down
//	public void clickHumidSpeedDown() {
//		WebDriverWait wait = new WebDriverWait(driver,20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.racSpeedDown)));
//		WebElement humidPlusElm = d.findByXPath(MyXPath.racSpeedDown, false, driver);
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
