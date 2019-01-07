package main.java.nasaTestSuite;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Appliance {
	public final int OPEN_WAIT = 120;
	public final int UPDATE_WAIT = 240;
	public final int POWER_SECS = 20;
	public final int BUTTON_WAIT = 20;
	public final int SIGN_IN_WAIT = 120;
	public final int TOGGLE_SECS = 2000;//ms
	
	protected static FrigiDriver d;
	public Appliance(FrigiDriver driver) 
	{
		this.d = driver;
	}

	public void UpdateApp() 
	{
		System.out.println("UNIMPLEMENTED");
	}
	
	public void typeField(String xpath, String text) 
	{		
		WebElement elem = d.findByXPath(xpath, BUTTON_WAIT);
		elem.clear();
		elem.sendKeys(text);
	}
	
	public void typePassword(String password) 
	{
		d.tapByXPath(XPath.passField, BUTTON_WAIT);
		WebElement elem = d.findByXPath(XPath.passField, BUTTON_WAIT);
		elem.clear();
		elem.sendKeys(password);
	}
	
	public void signIn(String email, String password) 
	{
		//TODO move the email.clear into here
		typeField(XPath.emailField, email);
		typePassword(password);
		d.tapByXPath(XPath.signInTwo);	
		System.out.println("Signed into " + email);
	}
	
	public void signIn()
	{
		typeField(XPath.emailField, "eluxtester1@gmail.com");
		typeField(XPath.passField,"123456");
		d.tapByXPath(XPath.signInTwo);	
		System.out.println("Signed into " + "eluxtester1@gmail.com");
	}
	
	public void signOut() 
	{
		System.out.println("SIGN OUT");
		d.tapByXPath(XPath.backButton, BUTTON_WAIT);
		openSettings();
		System.out.println("native scroll");
		d.useNativeContext();
		d.scrollDown();
		d.useWebContext();
		d.tapByXPath(XPath.signOutButton, BUTTON_WAIT);
		d.tapByXPath(XPath.signInOne, BUTTON_WAIT);
	}
	
	public void openSettings()
	{
		d.tapByXPath(XPath.settingsButton, BUTTON_WAIT);
	}

	public boolean isPowerOn() 
	{
		boolean powerOn = !d.searchForText("Off", XPath.offStatus, BUTTON_WAIT);
		System.out.println("isPowerOn: " + powerOn);
		return powerOn;
	}
	
	public void openControls(String applianceName) 
	{
		//TODO implement map navigation
		//Tap Back
		d.tapByXPath(XPath.backButton, d.BUTTON_WAIT);
		d.tapByXPath(XPath.getListApplianceName(applianceName), d.BUTTON_WAIT);
		
	}
}
