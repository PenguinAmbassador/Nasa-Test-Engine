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

	
	private static boolean iphoneConfig;
	private static boolean androidConfig;
	private static boolean racConfig;
	private static boolean stromboConfig;
	private static boolean dehumConfig;
	private static boolean signInConfig;
	private static boolean racStressConfig;
	private static boolean stromboStressConfig;
	private static boolean dehumStressConfig;

	private static boolean racQueued;
	private static boolean stromboQueued;
	private static boolean dehumQueued;
	private static boolean signInQueued;

    @Parameters
    public static Collection<Object[]> data() throws InterruptedException {	
        //default Parameters
    	Collection<Object[]> result = Arrays.asList(new Object[][] {{Appliance.TestType.SIGNIN, null}, {Appliance.TestType.DEHUM, null}});
    	
    	//Set up configs based on user input via GUI
    	GUI gui = new GUI();
    	gui.setVisible(true);
    	while(gui.isRunning()) {
    		System.out.println("Waiting: " + gui.isRunning());
    		Thread.sleep(200);
    	}    	
    	
    	//Read user input via XML
    	boolean[] config = readXML();
		System.out.println("Config loaded");
    	
		//Control which tests are run
    	iphoneConfig = config[0];
    	androidConfig = config[1];
    	racConfig = config[2];
    	stromboConfig = config[3];
    	dehumConfig = config[4];
    	signInConfig = config[5];
    	racStressConfig = config[6];
    	stromboStressConfig = config[7];
    	dehumStressConfig = config[8];
    	
    	//Used to limit openControls() checks and shorten runtime
    	racQueued = racConfig;
    	stromboQueued = stromboConfig;
    	dehumQueued = dehumConfig;
    	signInQueued= signInConfig;
    	
    	//Parameterized test configurations. The entire test class will re-run for each parameter set, however depending on the parameters certain tests get skipped until they are relevant to the parameter set. 
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
    	
    	//STRESS CONFIG
    	if(racStressConfig) {
    		System.out.println("Rac stress override");
       		result = Arrays.asList(new Object[][] {
				//SIGNIN
				{Appliance.TestType.SIGNIN, null},
				//DEHUM
				{Appliance.TestType.DEHUM, null},
	        	//RAC 10x
       			{Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}, {Appliance.TestType.RAC, Appliance.Modes.ECON}, {Appliance.TestType.RAC, Appliance.Modes.COOL}, {Appliance.TestType.RAC, Appliance.Modes.FAN}
   	           });
    	}else if(stromboStressConfig) {
    		System.out.println("Strombo stress override");
       		result = Arrays.asList(new Object[][] {
				//SIGNIN
				{Appliance.TestType.SIGNIN, null},
				//DEHUM
				{Appliance.TestType.DEHUM, null},
	        	//Strombo 10x
				{Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}, {Appliance.TestType.STROMBO, Appliance.Modes.ECON}, {Appliance.TestType.STROMBO, Appliance.Modes.DRY}, {Appliance.TestType.STROMBO, Appliance.Modes.FAN}, {Appliance.TestType.STROMBO, Appliance.Modes.COOL}
   	           });    		
    	}else if(dehumStressConfig) {
    		System.out.println("Dehum stress override");
       		result = Arrays.asList(new Object[][] {
				//SIGNIN
				{Appliance.TestType.SIGNIN, null},
				//DEHUM
				{Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}, {Appliance.TestType.DEHUM, null}
   	           });
    	}
    	return result;
    }


    @Parameter(0) // first data value 
    public /* NOT private */ Appliance.TestType targetAppliance;

    @Parameter(1) //Second data value
    public /* NOT private */ Appliance.Modes targetMode;

	@BeforeClass//("^This code opens the app$")
	public static void BeforeClass()
	{
		System.out.println("Full Test");
    	if(signInConfig) {
    		setupApp();
    		if(app.isSignedIn()) {
    			app.signOut();
    		}
    		System.out.println("SignIn Test - START");    		
    	}else {
    		setupApp("eluxtester1@gmail.com", "123456");	
    	}
	}
	
	@Before
	public void BeforeTest() {
		long startTime = System.currentTimeMillis();
		
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("@Before");
		System.out.println("TestType: " + targetAppliance);
		System.out.println("TargetMode: " + targetMode);
				
		if(targetAppliance != Appliance.TestType.SIGNIN && signInQueued== true){
			System.out.println("Signing in after \"SIGN IN TESTS\"");
			app.signIn("eluxtester1@gmail.com", "123456", true);
			signInQueued = false;			
		}
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true) {
			//nothing
		}else if(targetAppliance == Appliance.TestType.DEHUM && dehumQueued == true) {		
			System.out.println("Opening Dehum");
			ac.openControls(targetAppliance);
			if(!ac.isPowerOn(SHORT_WAIT)) {
				//if power is off, turn on
				frigi.tapByXPath(XPath.plainPowerButton); 
			}
			//Only need to switch to dehum once
			dehumQueued = false;
		}else if(targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true){
			if(stromboQueued) {
				System.out.println("Opening AC Controls");
				ac.openControls(targetAppliance);
				if(!ac.isPowerOn(SHORT_WAIT)) {
					//if power is off, turn on
					frigi.tapByXPath(XPath.plainPowerButton); 
				}
				//Only need to switch to AC once 
				stromboQueued = false;
			}else {
				ac.modeTo(targetMode, SHORT_WAIT);				
			}	
		}else if(targetAppliance == Appliance.TestType.RAC && racConfig == true){
			if(racQueued) {
				System.out.println("Opening AC Controls");
				ac.openControls(targetAppliance);
				if(!ac.isPowerOn(SHORT_WAIT)) {
					//if power is off, turn on
					frigi.tapByXPath(XPath.plainPowerButton); 
				}
				//Only need to switch to AC once 
				racQueued = false;
			}else {
				ac.modeTo(targetMode, SHORT_WAIT);				
			}	
		}else {
			if(signInConfig || dehumConfig) {
				System.out.println("Problem?");
			}else {
				System.out.println("Skipping AC Tests");				
			}
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			test.testPower();			
		}
	}
	
	
	@Test
	public void AcTempUpByRandom() 
	{
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig== true)){
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
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
		if((targetAppliance == Appliance.TestType.RAC && racQueued == true) || (targetAppliance == Appliance.TestType.STROMBO && stromboConfig == true)){
			if(targetMode != Appliance.Modes.DRY) {
				test.printStartTest("Speed Down");
				test.speedDown(targetAppliance, targetMode);
			}						
		}
	}


// ***************************** //	
// ********SIGN-IN TESTS******** //
// ***************************** //	

	@Test
	public void Sign_In_Sign_Out() 
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.signInSignOutValidation();
		}
	}
	
	@Test
	public void Empty_Email_Validation()
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.emptyEmailValidation();	
			resetErrors();
		}
	}
	
	@Test
	public void Empty_Password_Validation()
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.emptyPasswordValidation();
			resetErrors();
		}
	}
	
	@Test
	public void Invalid_Email_Validation() 
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.printStartTest("Invalid Email Validation");
			test.invalidEmailValidation("eluxtester1@gmail.");
			resetErrors();
			test.invalidEmailValidation("eluxtester1@.com");
			resetErrors();
			test.invalidEmailValidation("eluxtester1@gmail.c om");
			test.printEndTest("Invalid Email Validation", "PASS");
			
			resetErrors();
		}
	}
	
	@Test
	public void Short_Password_Validation()
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.shortPasswordValidation();
			resetErrors();
		}
	}
	
	@Test
	public void Credential_Validation()
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.printStartTest("Credential Validation");
			test.credentialValidation("eluxtester1@gmail.com", "12345");
			resetErrors();
			test.credentialValidation("wrongemail@gmail.com", "123456");
			test.printEndTest("Invalid Email Validation", "PASS");

			resetErrors();
		}
	}
	
	@Test
	public void Forgot_Password() 
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.forgotPass("eluxtester1@gmail.com");
		}
	}
	
	@Test
	public void Show_Password() 
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.showPass();
			resetErrors();
		}
	}
	
	@Test
	public void Stay_Signed_In()
	{
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.staySignedIn();
		}
	}
	//TODO forgot pass Back button
	
	//untested
	@Test
	public void Forgot_Pass_Back_Button() {
		if(targetAppliance == Appliance.TestType.SIGNIN && signInConfig == true){
			test.forgotPassBackButton();
		}
	}
}