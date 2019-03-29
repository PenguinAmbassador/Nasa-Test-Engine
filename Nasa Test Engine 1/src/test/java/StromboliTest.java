package test.java;

import org.junit.BeforeClass;
import org.junit.Ignore;

import main.java.nasaTestSuite.XPath;
import main.java.nasaTestSuite.Stromboli;
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
public class StromboliTest extends Base
{	
	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		System.out.println("StromboliTest");//delete later
		setupApp("eluxtester1@gmail.com", "123456");	

//		System.out.println("Assume power is on");
//	    strombo.signIn("eluxtester1@gmail.com", "123456");
		strombo.openControls("Strombo");
		if(!strombo.isPowerOn()) {
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
//	
//	@Test
//	public void tempUpByRandom() 
//	{
//		//TODO Increase by random ammount
//		//test.tempUpBy(random);
//	}
	
	@Test
	public void tempUpPastMax(){
		if(strombo.getTargTemp() > 32) {
			System.out.println("Temp up past Max fahrenheit");
			test.tempUpTo(90);
			test.tempUpBy(1);
			strombo.openSettings();
			frigi.scrollDown(-100);
			frigi.tapByXPath(XPath.unitToggle);
			frigi.tapByXPath(XPath.backButton);
			System.out.println("Temp up past Max celcius");
			test.tempUpTo(32);
			test.tempUpBy(1);
		} else {
			System.out.println("Temp up past Max celcius");
			test.tempUpTo(32);
			test.tempUpBy(1);
			strombo.openSettings();
			frigi.scrollDown(-100);
			frigi.tapByXPath(XPath.unitToggle);
			frigi.tapByXPath(XPath.backButton);
			System.out.println("Temp up past Max fahrenheit");
			test.tempUpTo(90);
			test.tempUpBy(1);			
		}
	}
	
	@Test
	public void tempDownPastMin(){
		if(strombo.getTargTemp() > 32) {
			System.out.println("Temp down past min fahrenheit");
			test.tempDownTo(60);
			test.tempDownBy(1);
			strombo.openSettings();
			frigi.scrollDown(-100);
			frigi.tapByXPath(XPath.unitToggle);
			frigi.tapByXPath(XPath.backButton);
			System.out.println("Temp down past min celsius");
			test.tempDownTo(16);
			test.tempDownBy(1);
		} else {
			System.out.println("Temp down past min celsius");
			test.tempDownTo(16);
			test.tempDownBy(1);
			strombo.openSettings();
			frigi.scrollDown(-100);
			frigi.tapByXPath(XPath.unitToggle);
			frigi.tapByXPath(XPath.backButton);
			System.out.println("Temp down past min fahrenheit");
			test.tempDownTo(32);
			test.tempDownBy(1);			
		}
	}
	
//	//verify functionality
//	@Test
//	public void tempDown() 
//	{
//		test.printStartTest("Temp Down");
////		if(!strombo.isPowerOn()) 
////		{
////			strombo.tapByXPath(MyXPath.powerOnButton, 10);
////		}
//		strombo.changeModeToCoolorEcon();
//		int expectedTemp = strombo.getTargTemp();
//		strombo.clickTempMinus();
//		expectedTemp--;
//		int currentTemp = strombo.getTargTemp();
//		System.out.println("Verify expectedTemp = " + expectedTemp);
//		System.out.println("Verify currentTemp = " + currentTemp);
//		System.out.println("removed -1 in conditional: verify.");
//		if(expectedTemp != currentTemp){
//			test.printEndTest("Temp Down", "FAIL");
//			fail();
//		}else {
//			test.printEndTest("Temp Down", "PASS");
//		}
//	}
//	
//	//functional and passing
//	@Test
//	public void modeUp() 
//	{
//		test.printStartTest("Mode Up");
//		
////		if(!strombo.isPowerOn()) 
////		{
////			strombo.tapByXPath(MyXPath.powerOnButton, 10);
////		}
//		int expectedMode = strombo.getNextExpectedMode();
//		strombo.clickModeUp();
//		System.out.println("Mode: " + strombo.getMode());
//		System.out.println("Expected: " + expectedMode);
//		if(expectedMode == strombo.getMode()) 
//		{
//			test.printEndTest("Mode Up", "PASS");
//		}else 
//		{
//			test.printEndTest("Mode Up", "FAIL");
//			fail();
//		}
//	}
//	
//	//verify functionality
//	@Test
//	public void modeDown() 
//	{
//		test.printStartTest("Mode Down");
//		
////		if(!strombo.isPowerOn()) 
////		{
////			strombo.tapByXPath(MyXPath.powerOnButton, 10);
////		}
//		int expectedMode = strombo.getPrevExpectedMode();
//		strombo.clickModeDown();
//		int currentMode = strombo.getMode();
//		System.out.println("Mode: " + currentMode);
//		System.out.println("Expected: " + expectedMode);
//		if(expectedMode == currentMode) 
//		{
//			test.printEndTest("Mode Down", "PASS");
//		}else 
//		{
//			test.printEndTest("Mode Down", "FAIL");
//			fail();
//		}
//	}
//
//	//functional and passing
//	@Test 
//	public void speedUp() 
//	{
//		test.printStartTest("Speed Up");
//
////		if(!strombo.isPowerOn()) 
////		{
////			strombo.tapByXPath(MyXPath.powerOnButton, 10);
////		}
//		//Avoid dry mode
//		if(strombo.getMode()==5) {
//			strombo.clickModeUp();
//		}
//		int expectedSpeed = strombo.getNextExpectedSpeed();
//		strombo.clickSpeedUp();
//		System.out.println("Speed: " + strombo.getSpeed());
//		System.out.println("Expected: " + expectedSpeed);
//		if(expectedSpeed == strombo.getSpeed()) 
//		{
//			test.printEndTest("Speed Up", "PASS");
//		}else 
//		{
//			test.printEndTest("Speed Up", "FAIL");
//			fail();
//		}
//	}
//
//	//verify functionality
//	@Test 
//	public void speedDown() 
//	{
//		test.printStartTest("Speed Down");
//
////		if(!strombo.isPowerOn()) 
////		{
////			strombo.tapByXPath(MyXPath.powerOnButton, 10);
////		}
//		//Avoid dry mode
//		if(strombo.getMode()==5) 
//		{
//			strombo.clickModeUp();
//		}
//		int expectedSpeed = strombo.getPrevExpectedSpeed();
//		strombo.clickSpeedDown();
//		int currentSpeed = strombo.getSpeed();
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