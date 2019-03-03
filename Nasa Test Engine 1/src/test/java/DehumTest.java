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
	
//	//functional and passing
//	@Test
//	public void powerOnOff() 
//	{
//		test.testPower();
//	}
//	
//	//functional and passing
//	@Test
//	public void Humidity_Up() 
//	{
//		test.printStartTest("Humidity Up");
//		int expectedHumidity = -1;
//		int currentHumidity = dehum.getTargHumidity();
//		dehum.clickHumidPlus();
//		if(currentHumidity == 85) {
//			expectedHumidity = 30;
//		}else {
//			expectedHumidity = currentHumidity + 5;			
//		}
//		currentHumidity = dehum.getTargHumidity();
//		System.out.println("Verify expectedTemp = " + expectedHumidity);
//		System.out.println("Verify currentTemp = " + currentHumidity);
//		if(expectedHumidity != currentHumidity) 
//		{
//			test.printEndTest("Humidity Up", "FAIL");
//			fail();
//		}
//		else
//		{
//			test.printEndTest("Humidity Up", "PASS");
//		}
//	}
	
	//functional and passing
	@Test
	public void Humidity_Down() 
	{
		test.printStartTest("Humidity Down");
		int expectedHumidity = -1;
		int currentHumidity = dehum.getTargHumidity();
		dehum.clickHumidMinus();
		if(currentHumidity == 30) {
			expectedHumidity = 85;
		}else {
			expectedHumidity = currentHumidity - 5;			
		}
		currentHumidity = dehum.getTargHumidity();
		System.out.println("Verify expectedTemp = " + expectedHumidity);
		System.out.println("Verify currentTemp = " + currentHumidity);
		if(expectedHumidity != currentHumidity){
			test.printEndTest("Humidity Down", "FAIL");
			fail();
		}
		else
		{
			test.printEndTest("Humidity Down", "PASS");
		}
	}
	
//	//functional and passing
//	@Test 
//	public void speedUp() 
//	{
//		test.printStartTest("Speed Up");
//		int expectedSpeed = dehum.getNextExpectedSpeed();
//		dehum.clickSpeedUp();
//		System.out.println("Speed: " + dehum.getSpeed());
//		System.out.println("Expected: " + expectedSpeed);
//		if(expectedSpeed == dehum.getSpeed()) 
//		{
//			test.printEndTest("Speed Up", "PASS");
//		}else 
//		{
//			test.printEndTest("Speed Up", "FAIL");
//			fail();
//		}
//	}
//
//	//functional and passing
//	@Test 
//	public void speedDown() 
//	{
//		test.printStartTest("Speed Down");
//		int expectedSpeed = dehum.getPrevExpectedSpeed();
//		dehum.clickSpeedDown();
//		int currentSpeed = dehum.getSpeed();
//		System.out.println("Speed: " + currentSpeed);
//		System.out.println("Expected: " + expectedSpeed);
//		if(expectedSpeed == currentSpeed) 
//		{
//			test.printEndTest("Speed Down", "PASS");
//		}else 
//		{
//			test.printEndTest("Speed Down", "FAIL");
//			fail();
//		}
//	}
}
