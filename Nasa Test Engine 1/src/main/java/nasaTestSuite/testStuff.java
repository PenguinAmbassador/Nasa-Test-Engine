package main.java.nasaTestSuite;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;



public class testStuff {
	public static void main(String[] args) {
		
		System.out.println("test3");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		FrigiDriver frigi = new FrigiDriver();
//		frigi.startApp(20);
//		frigi.UpdateApp();
//		frigi.clickSignIn1();
	}
	
	private static void divideByZero() {
		try {
			int i = 1/0;
		}catch(Exception e) {
			System.out.println("2");
		}
	}
	private static void test1() {
		URL testServerAddress=null; 
		AndroidDriver driver= null; 
		boolean boolAppStart = false;
		boolean boolAppUpdated = false;
		TestCapabilities testCap = new TestCapabilities();
		testCap.AssignAppiumCapabilities();
		try 
		{
			testServerAddress = new URL("http://localhost:4723/wd/hub");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("StepDef StartApp() CAUGHT: Failed to load URL");
		}
		
		//DesiredCapabilities appiumSettings = testCap.AssignAppiumCapabilities();//LOCALIZED CAPABILITIES
		DesiredCapabilities appiumSettings = new DesiredCapabilities();
		appiumSettings.setCapability("platform", /*this.GetPlatform().toString()*/"Android");
		appiumSettings.setCapability("platformVersion", /*this.GetPlatformVersion()*/"7.1.2");
		appiumSettings.setCapability("deviceName", /*this.GetDeviceName()*/"Galaxy s7");
		//appiumSettings.setCapability("avd", /*this.GetAVD()*/ "Nexus6P");
		appiumSettings.setCapability("app", /*this.GetApp()*/"C:\\Users\\WoodmDav\\Documents\\Android_AppUITesting.apk");
		appiumSettings.setCapability("automationName", "UiAutomator2");
		
		//David: run directly from local app, might run faster?
		//PACKAGE: com.ELXSmart
		//ACTIVITY: com.ELXSmart.ELXSmart
//		appiumSettings.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.chromecast.app");
//		appiumSettings.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.chromecast.app.core.MainActivity");
		
		try 
		{
			driver = new AndroidDriver<MobileElement>(testServerAddress, appiumSettings);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("CAUGHT: Failed to initialize AndroidDriver driver");
		}
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		boolAppStart = true;
		
		boolean looping = true;
		while(looping) {
			try {
				System.out.println("done waiting");
				WebElement updateButton = driver.findElementByAccessibilityId("MANAGE NEWS SOURCES");
				updateButton.click();
				looping = false;
			}catch(Exception e){
				System.out.println("Looking for news button");
			}
		}
	}
}
