package test.java;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import main.java.nasaTestSuite.Dehum;
import main.java.nasaTestSuite.FrigiDriver;
import main.java.nasaTestSuite.XPath;

public class MyTest {

	

		
//		public static FrigiDriver frigi = new FrigiDriver();
//		public static Dehum dehum = new Dehum(); 
		static int oneMinute = 60;
		
//		@Test//("^Appium server is running$")
//		public void appium_server_is_running()  {
//		    // Write code here that turns the phrase above into concrete actions
//		}
//
//		@Test//("^Phone or emulator are ready$")
//		public void phone_or_emulator_are_ready()  {
//		    // Write code here that turns the phrase above into concrete actions
//		}

//		@BeforeClass//("^This code opens the app$")
//		public static void launchMyTest(){
//			System.out.println("Launching App2");//delete later
//			//this.frigi = new FrigiDriver(20); //David: param is implicit time THIS BROKE SO HARD  NULLPOINTER LATER ON AT SIGN IN CAUSE UNKNOWN. Found out it was being reset between scenarios.
//			//frigi.startApp(20); original working code
//			frigi.startApp(1000000);//huge debug wait
//			
//			dehum.setDriver(frigi.getDriver());
//			//frigi.UpdateApp();
//			frigi.clickByXpath(XPath.updateButton, oneMinute*3);
//			System.out.println("PASS: Start and update the app");
////			frigi.clickSignIn1();
//			
//			
//			frigi.clickByXpath(XPath.signInOne, 240);
//		    frigi.clickByXpath(XPath.emailField, oneMinute);
//		    frigi.typeEmail();
//		    frigi.clickByXpath(XPath.passField, oneMinute);
//		    frigi.typePassword();
//		    frigi.clickByXpath(XPath.signInTwo, oneMinute);
//			System.out.println("PASS: Sign In");
//		    frigi.thinkWait();
//		    System.out.println("App Launched");
//		}
//		
//		@Test
//		public void dehumPowerOn() {
////			dehum.openControls();
////			System.out.println("Testing: dehum power on");
////			if(dehum.isPowerOn()) {//if power is on turn it off so we can test power on function
////				dehum.powerButton();
////			}
////			dehum.powerButton();
////			//TODO navigate to dehum
////			System.out.println("Completed: dehum power on");
//			
//			
//			frigi.testPowerOn(dehum);
//		}
//		
//		//Humidity will change to 'CONT' if 85, 35 if 'CONT', or it will just increase by 5
//		@Test
//		public void humidityUp(){
//			//setup
//			System.out.println("Testing: dehum humidity up");
//			dehum.openControls(); //press back button then choose dehum appliance
//			
//			System.out.println("HumidPRE1: " + dehum.isPowerOn());
//			if(!dehum.isPowerOn()) {//if power is not on, turn it on
//				dehum.powerButton();
//			}
//			System.out.println("HumidPRE2: " + dehum.isPowerOn());
//			
//			//test
//			try {
//				dehum.refreshHumidity();//refresh dehum.humidity
//				int expectedHumidity = dehum.getHumidity() + 5;
//				dehum.clickHumidPlus();
//				frigi.thinkWait();
//				
//				
//				dehum.refreshHumidity();
//				System.out.println("DEBUG1");
//				int actualHumidity = dehum.getHumidity();
//				if(expectedHumidity == 90) {
//					System.out.println("WIP: Handle CONT logic StepDef");
//					
//				}else if(expectedHumidity == 5){//means humidity went from 0 to 5, so actualHumidity should be 35
//					System.out.println("WIP: Handle CONT logic StepDef");
//				}else if(40 < expectedHumidity && expectedHumidity < 85) {
//					System.out.println("DEHUM PASS");
//					System.out.println("Expected Humidity: " + expectedHumidity);
//					System.out.println("Actual Humidity: " + expectedHumidity);
//					if(actualHumidity != expectedHumidity) {
//						System.out.println("Expected Humidity: " + expectedHumidity);
//						System.out.println("Actual Humidity: " + expectedHumidity);
//						fail();
//					}
//				}else {
//					System.out.println("ERROR: Plus Button Pressed - Unexpected humidity");
//				}
//				System.out.println("PASS: Scenario: feature Dehumidifier Humidity Control - function UP");
//				System.out.println("DEBUG2");
//			}catch(Exception e) {
//				e.printStackTrace();
//				System.out.println("delete catch after debugging");
//			}
//			frigi.thinkWait();
//			System.out.println("Completed: dehum humidity up");
//		}
//		
//		@Test
//		public void powerOff() {
//			System.out.println("Testing power off");
//			dehum.openControls(); //press back button then choose dehum appliance
//			if(!dehum.isPowerOn()) {
//				dehum.powerButton();
//			}
//			
//			dehum.powerButton();
//			System.out.println("Device shoud be off");
//		}

	

}
