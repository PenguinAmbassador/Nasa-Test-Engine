package test.java;

import org.junit.BeforeClass;
import org.junit.Ignore;

import main.java.nasaTestSuite.XPath;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;

import static org.junit.Assert.fail;

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
public class DehumTest extends Base{
	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		System.out.println("DehumTest");//delete later
		setupApp("eluxtester1@gmail.com", "123456");	

//	    dehum.signIn("eluxtester1@gmail.com", "123456");
	    dehum.openControls("Dehum");
		if(!dehum.isPowerOn()) {
			//if power is off, turn on
			frigi.tapByXPath(XPath.plainPowerButton);
		}
	}
	
	//functional and passing
	@Test
	public void powerOnOff() 
	{
		test.testPower();
	}
	
	//untested
	@Test
	public void tempUp() 
	{
		test.printStartTest("Humidity up");
		int expectedTemp = dehum.getTargHumidity();
		dehum.clickHumidPlus();
		expectedTemp +=5;
		int currentTemp = dehum.getTargHumidity();
		System.out.println("Verify expectedTemp = " + expectedTemp);
		System.out.println("Verify currentTemp = " + currentTemp);
		if(expectedTemp != currentTemp) 
		{
			test.printEndTest("Temp Up", "FAIL");
			fail();
		}
		else
		{
			test.printEndTest("Temp Up", "PASS");
		}
	}
	
	//verify functionality
	@Test
	public void tempDown() 
	{
		test.printStartTest("Humidity up");
		int expectedTemp = dehum.getTargHumidity();
		dehum.clickHumidMinus();
		expectedTemp -=5;
		int currentTemp = dehum.getTargHumidity();
		System.out.println("Verify expectedTemp = " + expectedTemp);
		System.out.println("Verify currentTemp = " + currentTemp);
		if(expectedTemp != currentTemp) 
		{
			test.printEndTest("Temp Up", "FAIL");
			fail();
		}
		else
		{
			test.printEndTest("Temp Up", "PASS");
		}
	}
	
	//functional and passing
	@Test 
	public void speedUp() 
	{
		test.printStartTest("Speed Up");
		int expectedSpeed = dehum.getNextExpectedSpeed();
		dehum.clickSpeedUp();
		System.out.println("Speed: " + dehum.getSpeed());
		System.out.println("Expected: " + expectedSpeed);
		if(expectedSpeed == dehum.getSpeed()) 
		{
			test.printEndTest("Speed Up", "PASS");
		}else 
		{
			test.printEndTest("Speed Up", "FAIL");
			fail();
		}
	}

	//verify functionality
	@Test 
	public void speedDown() 
	{
		test.printStartTest("Speed Down");
		int expectedSpeed = dehum.getPrevExpectedSpeed();
		dehum.clickSpeedDown();
		int currentSpeed = dehum.getSpeed();
		System.out.println("Speed: " + currentSpeed);
		System.out.println("Expected: " + expectedSpeed);
		if(expectedSpeed == currentSpeed) 
		{
			test.printEndTest("Speed Down", "PASS");
		}else 
		{
			test.printEndTest("Speed Down", "FAIL");
			fail();
		}
	}
}
