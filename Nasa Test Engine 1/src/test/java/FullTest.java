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
import java.util.Random;
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
import main.java.nasaTestSuite.GUI;
import main.java.nasaTestSuite.XPath;


@RunWith(Parameterized.class)
public class FullTest extends Base{

	
	private static boolean phoneConfig;
	private static boolean androidConfig;
	private static boolean racConfig;
	private static boolean stromboConfig;
	private static boolean dehumConfig;
	private static boolean signInConfig;

    @Parameters
    public static Collection<Object[]> data() throws InterruptedException {	
        //default Parameters
    	Collection<Object[]> result = Arrays.asList(new Object[][] {{Appliance.TestType.RAC, Appliance.Modes.ECON}});
    	
    	GUI gui = new GUI();
    	gui.setVisible(true);
    	while(gui.isRunning()) {
    		System.out.println("Waiting: " + gui.isRunning());
    		Thread.sleep(200);
    	}    	
    	
    	boolean[] config = readXML();
		System.out.println("Config loaded");
    	
    	phoneConfig = config[0];
    	androidConfig = config[1];
    	racConfig = config[2];
    	stromboConfig = config[3];
    	dehumConfig = config[4];
    	signInConfig = config[5];
    	
    	if(racConfig && stromboConfig){
    		System.out.println("rac and strombo params loaded");
    		result = Arrays.asList(new Object[][] {
				//SIGNIN
				{Appliance.TestType.SIGNIN, null},
				//DEHUM
				{Appliance.TestType.DEHUM, null},
	        	//RAC
	        	{Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}
	        	,
	        	//STROMBO
	        	{Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}
	           });
    	}else if(racConfig){
    		System.out.println("rac params loaded");
       		result = Arrays.asList(new Object[][] {
				//SIGNIN
				{Appliance.TestType.SIGNIN, null},
				//DEHUM
				{Appliance.TestType.DEHUM, null},
   	        	//RAC
   	        	{Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}
   	           });
    	}else if(stromboConfig) {
    		System.out.println("strombo params loaded");
    		result = Arrays.asList(new Object[][] {
				//SIGNIN
				{Appliance.TestType.SIGNIN, null},
				//DEHUM
				{Appliance.TestType.DEHUM, null},
	        	//STROMBO
	        	{Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}
	           });
    	}
    	
    	if(dehumConfig){
    		System.out.println("dehum params loaded");
    	}
    	if(signInConfig){
    		System.out.println("sign in params loaded");        		
    	}
    	return result;
    }


    @Parameter(0) // first data value 
    public /* NOT private */ Appliance.TestType targetAppliance;

    @Parameter(1) //Second data value
    public /* NOT private */ Appliance.Modes targetMode;

	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		System.out.println("Full Test");
    	
		setupApp("eluxtester1@gmail.com", "1234567");	
	}
	
	@Before
	public void changeMode() {
		long startTime = System.currentTimeMillis();
		
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("@Before");
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true) {
			//Tried to change to click backbutton, try again with more specific xpath. 
			System.out.println("SIGN IN: Resetting errors before each test");
			frigi.tapByXPath(XPath.backButton, frigi.BUTTON_WAIT);
			frigi.tapByXPath(XPath.signInOne, frigi.BUTTON_WAIT);		
		}else if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig == true) {
			System.out.println("Opening Dehum");
			strombo.openControls(targetAppliance);
			if(!strombo.isPowerOn(1)) {
				//if power is off, turn on
				frigi.tapByXPath(XPath.plainPowerButton); 
			}
		}else if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			System.out.println("Opening AC Controls");
			strombo.openControls(targetAppliance);
			if(!strombo.isPowerOn(1)) {
				//if power is off, turn on
				frigi.tapByXPath(XPath.plainPowerButton); 
			}
			strombo.modeTo(targetMode, 1);	
		}else {
			System.out.println("Problem?");
		}
				
		
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Time Elapsed @Before: " + ((stopTime-startTime)/1000f));
	}
	
	
	@Test
	public void printParams() {
		System.out.println("Print Param 1: " + targetAppliance);
	}	
	
	// *************************** //	
	// ********DEHUM TESTS******** //
	// *************************** //	
	
	//functional and passing
	@Test
	public void powerOnOff() 
	{
		if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig == true) {
			test.testPower();			
		}
	}
	
	//functional and passing
	@Test
	public void Humidity_Up() 
	{
		if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig == true) {
			test.printStartTest("Humidity Up");
			int expectedHumidity = -1;
			int currentHumidity = dehum.getTargHumidity();
			dehum.clickHumidPlus();
			if(currentHumidity == 85) {
				expectedHumidity = 30;
			}else {
				expectedHumidity = currentHumidity + 5;			
			}
			currentHumidity = dehum.getTargHumidity();
			System.out.println("Verify expectedTemp = " + expectedHumidity);
			System.out.println("Verify currentTemp = " + currentHumidity);
			if(expectedHumidity != currentHumidity) 
			{
				test.printEndTest("Humidity Up", "FAIL");
				fail();
			}
			else
			{
				test.printEndTest("Humidity Up", "PASS");
			}			
		}
	}
	
	//functional and passing
	@Test
	public void Humidity_Down() 
	{
		if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig == true) {
			test.printStartTest("Humidity Down - WARNING possible bugs");
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
	}
	
	//functional and passing
	@Test 
	public void speedUp() 
	{
		if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig == true) {
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
	}

	//functional and passing
	@Test 
	public void speedDown() 
	{
		if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig == true) {
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
	
	
	// ************************ //	
	// ********AC TESTS******** //
	// ************************ //	
	
	
	//functional and passing
	@Test
	public void AcPowerOnOff() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			test.testPower();			
		}
	}
	
	
	@Test
	public void AcTempUpByRandom() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode != Appliance.Modes.FAN && targetMode != Appliance.Modes.DRY) {
				test.printStartTest("Temp Up Random");
				System.out.println("Untested Temp up by Random");		
				test.tempUpBy(new Random().nextInt(10)+1);				
			}
		}
	}
	
	//verified
	@Test
	public void AcTempUpPastMax(){
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode != Appliance.Modes.FAN && targetMode != Appliance.Modes.DRY) {
				test.printStartTest("Temp up past MAX");
				test.tempUpPastMax();
				//TODO on rare occasion the app doesn't think after 3 seconds. Lag? Wifi? Causes Fail? Ask Developer			
			}			
		}
	}
	
	//Verified
	@Test
	public void AcTempDownPastMin(){
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode != Appliance.Modes.FAN && targetMode != Appliance.Modes.DRY) {
				test.printStartTest("Temp Down past MIN");
				test.tempDownPastMin();
			}			
		}
	}
	
	//functional and passing
	@Test
	public void AcModeUp() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
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
	}
	
	//verify functionality
	@Test
	public void AcModeUpDown() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode == Appliance.Modes.COOL) {
				test.printStartTest("Mode Up");
				test.modeUp();
				test.printStartTest("Mode Down");
				test.modeDown();
			}			
		}
	}

	//functional and passing
	@Test 
	public void AcSpeedUp() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode != Appliance.Modes.DRY) {
				test.printStartTest("Speed Up Four Times");
				for(int i = 0; i < 4; i++) {
					test.speedUp(targetAppliance, targetMode);
				}
			}			
		}
	}

	//verify functionality
	@Test 
	public void AcSpeedDown() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode != Appliance.Modes.DRY) {
				test.printStartTest("Speed Down");
				test.speedDown(targetAppliance, targetMode);
			}						
		}
	}
}