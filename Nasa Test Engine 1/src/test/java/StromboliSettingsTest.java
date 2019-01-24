package test.java;

import org.junit.BeforeClass;
import org.junit.Ignore;

import main.java.nasaTestSuite.XPath;
import main.java.nasaTestSuite.Stromboli;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.tools.ant.util.SymbolicLinkUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import main.java.nasaTestSuite.Appliance;
import main.java.nasaTestSuite.Dehum;
import main.java.nasaTestSuite.FrigiDriver;
import main.java.nasaTestSuite.XPath;

//@Ignore
public class StromboliSettingsTest extends Base
{

	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		//Setup app
		System.out.println("StromboliSettingsTest");//delete later
		setupApp("eluxtester1@gmail.com", "123456");
		
		//Sign in
		System.out.println("PASS: Sign In");
	    System.out.println("App Launched");
	    System.out.println();
		strombo.openControls("Strombo");
		System.out.println("Goal: POWERED ON WITH ECON/COOL SETTINGS MODE");
		if(!strombo.isPowerOn()) {
			System.out.println("Turning on appliance");
			frigi.tapByXPath(XPath.plainPowerButton, frigi.BUTTON_WAIT);
		}
		strombo.changeModeToCoolorEcon();
		strombo.openSettings();
	}
	
//	//functional and passing
//	@Test
//	public void changeName() 
//	{
//		test.changeName();
//	}
	
	//functional and passing
	@Test
	public void cleanAir() 
	{
		test.cleanAir();
	}
	
	//functional and passing
	@Test
	public void sleepMode() 
	{
		test.sleepMode();
	}
	
	//functional and passing
//	@Test
//	public void timeZone() 
//	{
//		test.timeZone();
//	}

	@Test
	public void notification() 
	{
		test.notificationTest();
	}
}