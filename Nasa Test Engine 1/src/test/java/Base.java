package test.java;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import main.java.nasaTestSuite.Appliance;
import main.java.nasaTestSuite.FrigiDriver;
import main.java.nasaTestSuite.XPath;
import main.java.nasaTestSuite.PhoneConfig;
import main.java.nasaTestSuite.Stromboli;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;
import main.java.nasaTestSuite.TestServers;

public class Base 
{	
	static int oneMinute = 60;
	private int screenshotCount = 0;

	public static FrigiDriver frigi = null;
	public static Stromboli strombo = null;
	public static Appliance app = null;
	public static TestFunctions test = null;	
	boolean testing = false;
	
	/**
	 * Launch appium, Frigidaire app, and tap the first sign in button. 
	 * @param offset
	 */
	protected static void setupApp() 
	{
		//Starting the app and pressing the first button
		try {	
			TestCapabilities capabilities = new TestCapabilities();
			PhoneConfig phone = new PhoneConfig(150, capabilities.GetDeviceName(), capabilities.GetPlatformVersion());
			System.out.println("WARNING: Temporarily removed update");
			System.out.println("WARNING: Stay Signed in is an unaccounted for possibility. Tests fail if the app is already signed in.");
			System.out.println("Capabilities: " + capabilities);
			System.out.println("Phone: " + phone);
			frigi = new FrigiDriver(TestServers.LocalServer(), capabilities.AssignAppiumCapabilities(), phone); //was from 540-890
			frigi.useWebContext(); //required for hybrid apps
			app = new Appliance(frigi);
			strombo = new Stromboli(frigi);
			test = new TestFunctions(frigi, app);	
			if(!app.isSignedIn()) {
				if(phone.isNewDevice()) {
					//s7 offset is 90 accurate.
					//s8 offset is 150? inaccurate.
					//NEXUS6P is 165 inaccurate.
					frigi.tapByXPath(XPath.signInOne, frigi.BUTTON_WAIT);
					frigi.calculateOffset();				
				} else {	
					frigi.tapByXPath(XPath.signInOne);
				}				
			}
			System.out.println("OFFSET: " + frigi.getOffset()); //change offset back to protected later
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * SetupApp overload where the app is signed in if it isn't already based on parameters.
	 * @param email
	 * @param password
	 */
	protected static void setupApp(String email, String password) {
		setupApp();

		if(!app.isSignedIn()) {
			app.signIn(email, password);
		}
	}
	
	public void fail() {
		//FAIL: screenshot fails because Android OS security prevents screenshots from being taken. 
		screenshotCount++;
		System.out.println("Screenshot #" + screenshotCount + " taken");
		frigi.useNativeContext();
		frigi.takeScreenshot("C:/temp/Screenshot.jpg");
		Assert.fail(); 
	}
	
	@AfterClass
	public static void quit(){
		System.out.println("Shutting down driver...");
		frigi.quit();
		System.out.println("Driver shut down!");
	}
}
