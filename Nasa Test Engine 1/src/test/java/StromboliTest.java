package test.java;

import org.junit.BeforeClass;
import org.junit.Ignore;

import main.java.nasaTestSuite.XPath;
import main.java.nasaTestSuite.Stromboli;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.apache.tools.ant.util.SymbolicLinkUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import main.java.nasaTestSuite.Appliance;
import main.java.nasaTestSuite.Dehum;
import main.java.nasaTestSuite.FrigiDriver;
import main.java.nasaTestSuite.XPath;

//@Ignore
@RunWith(Parameterized.class)
public class StromboliTest extends Base
{	

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
        	{ Appliance.Modes.COOL}, { Appliance.Modes.FAN}, { Appliance.Modes.ECON}, { Appliance.Modes.DRY}
                 //, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }  
           });
    }

    @Parameter // first data value (0) is default
    public /* NOT private */ Appliance.Modes firstParam;

//    @Parameter(1)
//    public /* NOT private */ int secondParam;
    
	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		System.out.println("StromboliTest");//delete later
		System.out.println("WARN: RAC will need to adjust mode num and speed num tests");
		setupApp("eluxtester1@gmail.com", "123456");	
		strombo.openControls("Strombo");
		if(!strombo.isPowerOn()) {
			//if power is off, turn on
			frigi.tapByXPath(XPath.plainPowerButton); //todo remove power assumption
		}
	}
	
	@Before
	public void changeMode() {
		strombo.modeTo(firstParam);
	}
	
	
	@Test
	public void printParams() {
		System.out.println("Print Param 1: " + firstParam);
	}
	
	//functional and passing
	@Test
	public void powerOnOff() 
	{
		test.testPower();
	}
	
	
//	@Test
//	public void tempUpByRandom() 
//	{
//		//TODO Increase by random ammount
//		//test.tempUpBy(random);
//	}
	
	//verified
	@Test
	public void tempUpPastMax(){
		if(firstParam != Appliance.Modes.FAN && firstParam != Appliance.Modes.DRY) {
			test.printStartTest("Temp up past MAX");
			test.tempUpPastMax();
			//TODO on rare occasion the app doesn't think after 3 seconds. Lag? Wifi? Causes Fail? Ask Developer			
		}
	}
	
	//Verified
	@Test
	public void tempDownPastMin(){
		if(firstParam != Appliance.Modes.FAN && firstParam != Appliance.Modes.DRY) {
			test.printStartTest("Temp Down past MIN");
			test.tempDownPastMin();
		}
	}
	
//	//verify functionality
//	@Test
//	public void tempDown() 
//	{
//		test.printStartTest("Temp Down");
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
	
	//functional and passing
	@Test
	public void modeUp() 
	{
		//TODO add test case that verifies that each mode was found
		//Cycles through all modes
		//Only need to run this test once
		if(firstParam == Appliance.Modes.COOL) {
			test.printStartTest("Mode Up Four Times");
			for(int i = 0; i < 4; i++) {
				test.modeUp();
			}
		}
	}
	
	//verify functionality
	@Test
	public void modeUpDown() 
	{
		if(firstParam == Appliance.Modes.COOL) {
			test.printStartTest("Mode Up");
			test.modeUp();
			test.printStartTest("Mode Down");
			test.modeDown();
		}
	}

	//functional and passing
	@Test 
	public void speedUp() 
	{
		if(firstParam != Appliance.Modes.DRY) {
			test.printStartTest("Speed Up Four Times");
			for(int i = 0; i < 4; i++) {
				test.speedUp();
			}
		}
	}

	//verify functionality
	@Test 
	public void speedDown() 
	{
		if(firstParam != Appliance.Modes.DRY) {
			test.printStartTest("Speed Down");
			test.speedDown();
		}
	}
	
}