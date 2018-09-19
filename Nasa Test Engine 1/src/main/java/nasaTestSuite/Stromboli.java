package main.java.nasaTestSuite;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class Stromboli extends FrigiDriver
{
	private int mode;
	private Date timer;
	private int targTemp;
	private int currentTemp;
	private int speed; //0 low 1 medium 2 high 3 auto
	private boolean notifications; //0 off 1 on
	private boolean isCelsius;
	private String timeZone;
	private static AndroidDriver driver;
	private String name = "strombo";
	//TODO timer, speed, notifications, timezone all need to be abstracted
	
	
//	public Dehum(AndroidDriver driver, int implicitTime) {
//		this.driver = driver;
//		// TODO do i need to update appstart boolean?
//	}
	
	//Actions
	
	public void openControls() 
	{
		openControls("strombo");
	}

	//temp PLUS
	public void clickTempPlus() 
	{
		WebDriverWait wait = new WebDriverWait(driver, BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.stromboTempUp)));
		WebElement tempPlusElm = findByXPath(MyXPath.stromboTempUp, false, driver);
		tempPlusElm.click();
		thinkWait();
	}
	
	//temp MINUS
	public void clickTempMinus() 
	{
		WebDriverWait wait = new WebDriverWait(driver,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.stromboTempDown)));
		WebElement tempMinusElm = findByXPath(MyXPath.stromboTempDown, false, driver);
		tempMinusElm.click();
		thinkWait();
	}
	
	//Humidity Speed Down
	public void clickSpeedUp() 
	{
		WebDriverWait wait = new WebDriverWait(driver,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.stromboSpeedup)));
		WebElement speedElm = findByXPath(MyXPath.stromboSpeedup, false, driver);
		speedElm.click();
		thinkWait();
	}
	
	//Humidity Speed Down
	public void clickSpeedDown() 
	{
		WebDriverWait wait = new WebDriverWait(driver,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.racSpeedDown)));
		WebElement speedElm = findByXPath(MyXPath.racSpeedDown, false, driver);
		speedElm.click();
		thinkWait();
	}
	
	public void clickModeUp() 
	{

		WebDriverWait wait = new WebDriverWait(driver,BUTTON_WAIT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MyXPath.stromboModeUp)));
		WebElement modeElm = findByXPath(MyXPath.stromboModeUp, false, driver);
		modeElm.click();
		thinkWait();	
	}
	
	//States
	public void refreshTargTemp() 
	{
		//TODO xbug: refresh will probably be confused by current room temp until we get IDS
		//TODO add logic for isCelsius
		boolean searching = true;
		if(isCelsius) 
		{
			for(int i = 16; i <=  32 && searching; i++) 
			{
				System.out.println("temp range 16 - 32?");
				if(searchForText(String.valueOf(i), SIGN_IN_WAIT)) 
				{
					targTemp = i;
					System.out.println("Found target temp: " + targTemp);
					searching = false;
				}
			}
		} 
		else 
		{
			for(int i = 60; i <=  90 && searching; i++) 
			{
				boolean tempFound = searchForText(String.valueOf(i), SIGN_IN_WAIT);
				if(tempFound) 
				{
					targTemp = i;
					System.out.println("Found target temp: " + targTemp);
					//searching = false;
				}
			}
		}
	}
	
	public void refreshMode() 
	{
		//0 econ 1 Dry 2 Fan 3 Cool
		boolean searching = true;
		if(searchForText("Econ", BUTTON_WAIT) && searching) 
		{
			mode = 0;
			searching = false;
		} 
		else if(searchForText("Dry", BUTTON_WAIT) && searching) 
		{
			mode = 1;
			searching = false;
		} 
		else if(searchForText("Fan Only", BUTTON_WAIT) && searching) 
		{
			mode = 2;
			searching = false;
		} 
		else if(searchForText("Cool", BUTTON_WAIT) && searching) 
		{
			mode = 3;
			searching = false;
		}
		System.out.println("RefreshMode: " + mode);//DebugDelete
	}
	
	public void refreshSpeed() 
	{
		System.out.println("high,med,low, spelt wrong in code");
		//0 econ 1 cool 2 fan 3 dry
		boolean searching = true;
		if(searchForText("Low", BUTTON_WAIT) && searching) 
		{
			mode = 0;
			searching = false;
		} 
		else if(searchForText("Medium", BUTTON_WAIT) && searching) 
		{
			mode = 1;
			searching = false;
		} 
		else if(searchForText("High", BUTTON_WAIT) && searching) 
		{
			mode = 2;
			searching = false;
		} 
		else if(searchForText("Auto", BUTTON_WAIT) && searching) 
		{
			mode = 3;
			searching = false;
		}
		System.out.println("refreshSpeed: " + mode);//DebugDelete
	}
	
	public void verifyApplianceName() 
	{
		clickOffSettings();
		
		//need ids
		String fake_applianceNameHead = "";
		String fake_applianceNameField = "";
		
		
		
		boolean applianceNameHeadShowing = searchForText("Appliance Name", BUTTON_WAIT);
	}
	
	public void setDriver(AndroidDriver driver) 
	{
		this.driver = driver;
	}

	public int getTargTemp() 
	{
		return targTemp;
	}

	public int getMode() 
	{
		return mode;
	}
	
	public int getSpeed() 
	{
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