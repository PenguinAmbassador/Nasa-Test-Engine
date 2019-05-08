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

@Ignore
@RunWith(Parameterized.class)
public class StromboliTest extends Base
{	

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
        	//RAC
        	{Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}
        	,
        	//STROMBO
        	{Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}
        	
//        	//test 
//        	{Appliance.Types.RAC, Appliance.Modes.ECON}, {Appliance.Types.RAC, Appliance.Modes.COOL}, {Appliance.Types.RAC, Appliance.Modes.FAN}, {Appliance.Types.STROMBO, Appliance.Modes.DRY}       	
        	
        	
        	//example
            //, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 }  
           });
    }

    @Parameter(0) // first data value 
    public /* NOT private */ Appliance.TestType targetAppliance;

    @Parameter(1) //Second data value
    public /* NOT private */ Appliance.Modes targetMode;
    
	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		System.out.println("StromboliTest");//delete later
		setupApp("eluxtester1@gmail.com", "1234567");	
		if(!strombo.isPowerOn()) {
			//if power is off, turn on
			frigi.tapByXPath(XPath.plainPowerButton); 
		}
	}
	
	@Before
	public void changeMode() {
		long startTime = System.currentTimeMillis();
		
		
		strombo.openControls(targetAppliance);
		strombo.modeTo(targetMode);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Time Elapsed @Before: " + ((stopTime-startTime)/1000f));
	}
	
	
	@Test
	public void printParams() {
		System.out.println("Print Param 1: " + targetAppliance);
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
		if(targetMode != Appliance.Modes.FAN && targetMode != Appliance.Modes.DRY) {
			test.printStartTest("Temp up past MAX");
			test.tempUpPastMax();
			//TODO on rare occasion the app doesn't think after 3 seconds. Lag? Wifi? Causes Fail? Ask Developer			
		}
	}
	
	//Verified
	@Test
	public void tempDownPastMin(){
		if(targetMode != Appliance.Modes.FAN && targetMode != Appliance.Modes.DRY) {
			test.printStartTest("Temp Down past MIN");
			test.tempDownPastMin();
		}
	}
	
//	//DEPRECIATED
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
		if(targetMode == Appliance.Modes.COOL) {
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
		if(targetMode == Appliance.Modes.COOL) {
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
		if(targetMode != Appliance.Modes.DRY) {
			test.printStartTest("Speed Up Four Times");
			for(int i = 0; i < 4; i++) {
				test.speedUp(targetAppliance, targetMode);
			}
		}
	}

	//verify functionality
	@Test 
	public void speedDown() 
	{
		if(targetMode != Appliance.Modes.DRY) {
			test.printStartTest("Speed Down");
			test.speedDown(targetAppliance, targetMode);
		}
	}
	
}