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
import main.java.nasaTestSuite.MyXPath;
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
	
	//TODO overload and if a offset is not used and the device is new then calculate a new offset.
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
			System.out.println("Capabilities: " + capabilities);
			System.out.println("Phone: " + phone);
			System.out.println("Temporarily removed update");
			frigi = new FrigiDriver(TestServers.LocalServer(), capabilities.AssignAppiumCapabilities(), phone); //was from 540-890
			frigi.useWebContext(); //required for hybrid apps
			app = new Appliance(frigi);
			strombo = new Stromboli(frigi);
			test = new TestFunctions(frigi, app);	
			if(phone.isNewDevice()) {
				//s7 offset is 90 accurate.
				//s8 offset is 150? inaccurate.
				//NEXUS6P is 165 inaccurate.
				frigi.tapByXPath(MyXPath.signInOne, frigi.BUTTON_WAIT);
				frigi.calculateOffset();				
			} else {	
				frigi.tapByXPath(MyXPath.signInOne);
			}
			System.out.println("FRIGI OFFSET: " + frigi.getOffset()); //change offset back to protected later
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fail() {
		screenshotCount++;
		System.out.println("Screenshot #" + screenshotCount + "taken.");
		takeScreenshot("screenshot" + screenshotCount);
		Assert.fail();
	}
	
	/**
	 * Take a screenshot and save it the project root directory
	 * @param name Filename of the picture. Should not include a file extension. 
	 */
	public void takeScreenshot(String name) {
		try {
			File file  = ((TakesScreenshot)this).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(name + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void takeScreenshot(String name, String path) {
		takeScreenshot(path+name);
	}
	
	@AfterClass
	public static void quit(){
		System.out.println("Shutting down driver...");
		frigi.quit();
		System.out.println("Driver shut down!");
	}
}
