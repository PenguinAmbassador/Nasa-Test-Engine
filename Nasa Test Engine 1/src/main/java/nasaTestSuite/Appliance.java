package main.java.nasaTestSuite;

import static org.junit.Assert.fail;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Appliance {
	public final int OPEN_WAIT = 120;
	public final int UPDATE_WAIT = 240;
	public final int POWER_SECS = 20;
	public final int BUTTON_WAIT = 5;
	public final int SIGN_IN_WAIT = 120;
	public final int TOGGLE_SECS = 2000;//ms
	public final int SHORT_WAIT = 1;
	
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
		typeField(XPath.emailField, email);
		typePassword(password);
		d.tapByXPath(XPath.signInTwo);	
		System.out.println("Signed into " + email);
	}
	
	public void signIn()
	{
		//DAVID: recommend deletion, but if this is easier then just delete this comment
		signIn("eluxtester1@gmail.com", "123456");
	}
	
	public void signOut() 
	{
		//TODO: Bad Assumption - Assumes you have appliances provisioned
		System.out.println("SIGN OUT");
		System.out.println("Inefficientlly does not check to see if you are already on an appliance. Back button not necessary most of the time.");
		d.tapByXPath(XPath.backButton, BUTTON_WAIT);
		openSettings();
		System.out.println("native scroll");
		WebElement element = d.findByXPaths(XPath.phoneLabels, 2, BUTTON_WAIT);
		System.out.println("Phone Label Element: " + element);
		d.scrollDown(-300);
		d.tapByXPath(XPath.signOutButton, BUTTON_WAIT);
		d.tapByXPath(XPath.signInOne, BUTTON_WAIT);
	}
	
	public void openSettings()
	{
		d.tapByXPath(XPath.settingsButton, BUTTON_WAIT);
	}

	public boolean isPowerOn() 
	{
		boolean powerOn = !d.searchForText("Off", XPath.offStatus, d.TEXT_SEARCH_WAIT);
		System.out.println("isPowerOn: " + powerOn);
		return powerOn;
	}
	
	public void openControls(String applianceName) 
	{
		if(d.xPathIsDisplayed(XPath.getControlApplianceName(applianceName), SHORT_WAIT)) {
			System.out.println("Controls already open: " + applianceName);
		}else {
			System.out.println("WIP: Inefficiently doesn't check whether the appliance has already been chosen");
			d.tapByXPath(XPath.backButton, d.BUTTON_WAIT);
			d.tapByXPath(XPath.getListApplianceName(applianceName), d.BUTTON_WAIT);			
		}
	}

	/**
	 * Check whether or not the user is signed in or not. 
	 * @return True if signed in. False if signed out.
	 */
	public boolean isSignedIn() {
		System.out.println("Checking if Signed in...");
		boolean result = false;
		boolean signedIn = d.xPathIsDisplayed(XPath.settingsButton);
		//Using two variables instead of one to check for an unexpected screen. A false signedOut does not imply a successful sign in, so each screen is checked separately. 
		if(signedIn) {
			System.out.println("Already Signed In");
			result = true;
		} else {
			boolean signedOut = d.xPathIsDisplayed(XPath.signInTwo, d.SHORT_WAIT) || d.xPathIsDisplayed(XPath.signInOne, d.SHORT_WAIT);
			if(signedOut){		
				System.out.println("Currently Signed In");
				result = false;		
			} else {
				//if both are false then there is an unexpected screen. 
				System.out.println("isSignedIn ERROR: Unexpected outcome");
			}
		}
		return result; 
	}
	public void relaunchApp() {
		System.out.println("Closing app");
		d.closeApp();
		d.launchApp();
		d.useWebContext();
		System.out.println("App has been relaunched");
	}

	public int getSpeed() 
	{
		int speed = -1;
		speed = Integer.parseInt(d.findByXPath(XPath.currentFanSpeed, BUTTON_WAIT).getAttribute("data-value"));
		return speed;
	}

	//Speed Down
	public void clickSpeedUp() 
	{
		WebElement speedElm = d.findByXPath(XPath.speedUp, BUTTON_WAIT);
		speedElm.click();
		d.thinkWait();
	}
	
	//Speed Down
	public void clickSpeedDown() 
	{
		WebElement speedElm = d.findByXPath(XPath.speedDown, BUTTON_WAIT);
		speedElm.click();
		d.thinkWait();
	}
}
