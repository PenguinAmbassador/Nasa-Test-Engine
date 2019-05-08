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
	public static boolean fakeNotify = false;

    @Parameters
    public static Collection<Object[]> data() throws InterruptedException {
        //default Parameters
    	Collection<Object[]> result = Arrays.asList(new Object[][] {{Appliance.Types.RAC, Appliance.Modes.ECON}});
    	
    	GUI gui = new GUI();
    	gui.setVisible(true);
    	System.out.println("running? : " + gui.isRunning());
    	while(gui.isRunning()) {
    		System.out.println("Running: " + gui.isRunning());
    		Thread.sleep(200);
    	}
    	fakeNotify = true;
    	
    	System.out.println("Running: " + gui.isRunning());
    	boolean[] config = readXML();

		System.out.println("Config loaded");
    	
    	boolean phoneConfig = config[0];
    	boolean androidConfig = config[1];
    	boolean racConfig = config[2];
    	boolean stromboConfig = config[3];
    	boolean dehumConfig = config[4];
    	boolean signInConfig = config[5];
    	
    	if(racConfig && stromboConfig){
    		System.out.println("rac and strombo params loaded");
    		result = Arrays.asList(new Object[][] {
    	        	//RAC
    	        	{Appliance.Types.RAC, Appliance.Modes.ECON}, {Appliance.Types.RAC, Appliance.Modes.COOL}, {Appliance.Types.RAC, Appliance.Modes.FAN}
    	        	,
    	        	//STROMBO
    	        	{Appliance.Types.STROMBO, Appliance.Modes.ECON}, {Appliance.Types.STROMBO, Appliance.Modes.DRY}, {Appliance.Types.STROMBO, Appliance.Modes.FAN}, {Appliance.Types.STROMBO, Appliance.Modes.COOL}
    	           });
    	}else if(racConfig){
    		System.out.println("rac params loaded");
       		result = Arrays.asList(new Object[][] {
       	        	//RAC
       	        	{Appliance.Types.RAC, Appliance.Modes.ECON}, {Appliance.Types.RAC, Appliance.Modes.COOL}, {Appliance.Types.RAC, Appliance.Modes.FAN}
       	           });
    	}else if(stromboConfig) {
    		System.out.println("strombo params loaded");
    		result = Arrays.asList(new Object[][] {
	        	//STROMBO
	        	{Appliance.Types.STROMBO, Appliance.Modes.ECON}, {Appliance.Types.STROMBO, Appliance.Modes.DRY}, {Appliance.Types.STROMBO, Appliance.Modes.FAN}, {Appliance.Types.STROMBO, Appliance.Modes.COOL}
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
    public /* NOT private */ Appliance.Types targetAppliance;

    @Parameter(1) //Second data value
    public /* NOT private */ Appliance.Modes targetMode;

	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
//		add later
		System.out.println("Launch Test");
	}
	
	@Before
	public void changeMode() {
		long startTime = System.currentTimeMillis();
		
//		strombo.openControls(targetAppliance);
//		strombo.modeTo(targetMode);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("Time Elapsed @Before: " + ((stopTime-startTime)/1000f));
	}
	
	
	@Test
	public void printParams() {
		System.out.println("Print Param 1: " + targetAppliance);
	}
	
}
