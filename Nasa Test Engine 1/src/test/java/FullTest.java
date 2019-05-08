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
    	System.out.println("running? : " + gui.isRunning());
    	while(gui.isRunning()) {
    		System.out.println("Running: " + gui.isRunning());
    		Thread.sleep(200);
    	}
    	
    	System.out.println("Running: " + gui.isRunning());
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
		if(!strombo.isPowerOn()) {
			//if power is off, turn on
			frigi.tapByXPath(XPath.plainPowerButton); 
		}
	}
	
	@Before
	public void changeMode() {
		long startTime = System.currentTimeMillis();
		
		if(targetAppliance == Appliance.TestType.SIGNIN) {
			//Tried to change to click backbutton, try again with more specific xpath. 
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("Resetting errors before each test");
			frigi.tapByXPath(XPath.backButton, frigi.BUTTON_WAIT);
			frigi.tapByXPath(XPath.signInOne, frigi.BUTTON_WAIT);		
		}else if(targetAppliance == Appliance.TestType.DEHUM && dehumConfig) {
			strombo.openControls(targetAppliance);
		}else if((targetAppliance == Appliance.TestType.RAC && racConfig == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			strombo.openControls(targetAppliance);
			strombo.modeTo(targetMode);	
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
}