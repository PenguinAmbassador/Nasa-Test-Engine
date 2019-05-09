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

@Ignore
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
		ac.openControls("Strombo");
		System.out.println("Goal: POWERED ON WITH ECON/COOL SETTINGS MODE");
		if(!ac.isPowerOn()) {
			System.out.println("Turning on appliance");
			frigi.tapByXPath(XPath.plainPowerButton, frigi.BUTTON_WAIT);
		}
		ac.changeModeToCoolorEcon();
		ac.openSettings();
	}
	
	//functional and passing
	@Test
	public void changeName() 
	{
		test.changeName();
	}
	
	//functional and passing
	@Ignore("Disabled clean tests until 2019 unit is available.")
	@Test
	public void cleanAir() 
	{
		//TODO don't know how to tell whether it is a 2019 model yet
		System.out.println("Disabled clean tests until 2019 unit is available.");
		//test.cleanAir();
	}
	
	//functional and passing
	@Test
	public void sleepMode() 
	{
		test.sleepMode();
	}
	
	//functional and passing
	@Test
	public void timeZone() 
	{
		test.timeZone();
	}

	//functional and passing
	@Test
	public void notification() 
	{
		//TODO move to global SettingsTest
		//test.notificationTest();
	}
	
	//functional and passing
	@Test
	public void Temperature_Unit_Test() {
		test.toggleTempTest();
	}
	
	//TODO Version_Information - Discuss if this falls under UI or functionality testing
	@Test
	public void Version_Information() {
		//Discuss if this falls under UI or functionality testing
	}
	
	//TODO Remove_Appliance - Discuss if this falls under UI or functionality testing
	@Test 
	public void Remove_Appliance() {
		//Discuss if this falls under UI or functionality testing
	}
}