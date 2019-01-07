package main.java.nasaTestSuite;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class Stromboli extends Appliance
{
	private int mode;
	private Date timer;
	private int targTemp;
	private int currentTemp;
	private int speed; //0 low 1 medium 2 high 3 auto
	private boolean notifications; //0 off 1 on
	private boolean isCelsius;
	private String timeZone;
	private String name = "strombo";
	//TODO timer, speed, notifications, timezone all need to be abstracted
	
	public Stromboli(FrigiDriver frigi) {
		super(frigi);
	}
	//Actions
	

	//temp PLUS
	public void clickTempPlus() 
	{
		WebDriverWait wait = new WebDriverWait(d, BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.stromboTempUp)));
		WebElement tempPlusElm = d.findByXPath(XPath.stromboTempUp, BUTTON_WAIT);
		tempPlusElm.click();
		d.thinkWait();
	}
	
	//temp MINUS
	public void clickTempMinus() 
	{
		WebDriverWait wait = new WebDriverWait(d, BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.stromboTempDown)));
		WebElement tempMinusElm = d.findByXPath(XPath.stromboTempDown, false, d);
		tempMinusElm.click();
		d.thinkWait();
	}
	
	//Humidity Speed Down
	public void clickSpeedUp() 
	{
		WebDriverWait wait = new WebDriverWait(d,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.stromboSpeedup)));
		WebElement speedElm = d.findByXPath(XPath.stromboSpeedup, false, d);
		speedElm.click();
		d.thinkWait();
	}
	
	//Humidity Speed Down
	public void clickSpeedDown() 
	{
		WebDriverWait wait = new WebDriverWait(d,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.stromboSpeedDown)));
		WebElement speedElm = d.findByXPath(XPath.stromboSpeedDown, false, d);
		speedElm.click();
		d.thinkWait();
	}
	
	public void clickModeUp() 
	{
		WebDriverWait wait = new WebDriverWait(d,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.stromboModeUp)));
		WebElement modeElm = d.findByXPath(XPath.stromboModeUp, false, d);
		modeElm.click();
		d.thinkWait();	
	}
	
	public void clickModeDown() 
	{
		WebDriverWait wait = new WebDriverWait(d,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPath.stromboModeDown)));
		WebElement modeElm = d.findByXPath(XPath.stromboModeDown, false, d);
		modeElm.click();
		d.thinkWait();	
	}

	public void changeModeToCoolorEcon(){
		//Change mode until you reach a mode that can change the temperature
		int tempMode = getMode();
		while(tempMode==3 || tempMode==5) 
		{
			clickModeUp();
			tempMode = getMode();
		}
	}

	public int getTargTemp() 
	{
		try {
			targTemp = Integer.parseInt(d.findByXPath(XPath.stromboTargetTemp, BUTTON_WAIT).getAttribute("data-value"));
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		return targTemp;
	}

	public int getNextExpectedMode() {
		try {
			mode = Integer.parseInt(d.findByXPath(XPath.stromboCurrentMode, BUTTON_WAIT).getAttribute("data-value"));
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		int nextExpectedMode = -1;
		switch (mode) {
	        case 4:  nextExpectedMode = 5;
	        	break;
	        case 5:  nextExpectedMode = 3;
	        	break;
	        case 3:  nextExpectedMode = 1;
	        	break;
			case 1:  nextExpectedMode = 4;
		    	break;
			default: System.out.println("Unknown mode: " + mode);
				break;
		}
		return nextExpectedMode;
	}

	public int getPrevExpectedMode() {
		mode = Integer.parseInt(d.findByXPath(XPath.stromboCurrentMode, BUTTON_WAIT).getAttribute("data-value"));
		int nextExpectedMode = -1;
		switch (mode) {
	        case 4:  nextExpectedMode = 1; //econ
	        	break;
	        case 5:  nextExpectedMode = 4;
	        	break;
	        case 3:  nextExpectedMode = 5;
	        	break;
			case 1:  nextExpectedMode = 3;
		    	break;
			default: System.out.println("Unknown mode: " + mode);
				break;
		}
		return nextExpectedMode;
	}

	public int getNextExpectedSpeed() {
		speed = Integer.parseInt(d.findByXPath(XPath.stromboCurrentFanSpeed, BUTTON_WAIT).getAttribute("data-value"));
		System.out.println("Speed by data value" + speed);
		int nextExpectedSpeed = -1;
		switch (speed) {
	        case 1:  nextExpectedSpeed = 2;
	        	break;
	        case 2:  nextExpectedSpeed = 4;
	        	break;
	        case 4:  nextExpectedSpeed = 7;
	        	break;
			case 7:  nextExpectedSpeed = 1;
		    	break;
			default: System.out.println("Unknown speed: " + speed);
				break;
		}
		return nextExpectedSpeed;
	}

	public int getPrevExpectedSpeed() {
		speed = Integer.parseInt(d.findByXPath(XPath.stromboCurrentFanSpeed, BUTTON_WAIT).getAttribute("data-value"));
		int prevExpectedSpeed = -1;
		switch (speed) {
	        case 1:  prevExpectedSpeed = 7;
	        	break;
	        case 2:  prevExpectedSpeed = 1;
	        	break;
	        case 4:  prevExpectedSpeed = 2;
	        	break;
			case 7:  prevExpectedSpeed = 4;
		    	break;
			default: System.out.println("Unknown speed: " + speed);
				break;
		}
		return prevExpectedSpeed;
	}
	
	public int getMode() 
	{
		mode = Integer.parseInt(d.findByXPath(XPath.stromboCurrentMode, BUTTON_WAIT).getAttribute("data-value"));
		return mode;
	}
	
	public int getSpeed() 
	{
		speed = Integer.parseInt(d.findByXPath(XPath.stromboCurrentFanSpeed, BUTTON_WAIT).getAttribute("data-value"));
		return speed;
	}

	public Date getTimer() 
	{
		return timer;
	}


	public String getTimeZone() 
	{
		return timeZone;
	}
	public String getName() {
		return name;
	}
	
	
	
}
