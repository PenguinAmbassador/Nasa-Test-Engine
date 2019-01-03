package main.java.nasaTestSuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.AndroidMobileCapabilityType;

public class TestCapabilities 
{	
	public enum DevicePlatform
	{
		Undefined,
		iOS,
		Android
	}
	
	private DevicePlatform platform;
	private String platformVersion;
	private String deviceName;
	private String avd;
	private String app;
	private String automationName;
	
	public TestCapabilities()
	{
		try 
		{
			this.platform = DevicePlatform.Android;
			this.platformVersion = LaunchCMD("adb shell getprop ro.build.version.release");
			this.deviceName = LaunchCMD("adb shell getprop ro.product.model");
			this.avd = "";
			this.app = "";
			this.automationName = "UiAutomator2";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public DesiredCapabilities AssignAppiumCapabilities()
	{
		String appPath = new File("").getAbsolutePath();
		if(appPath.contains("Users"))
		{
			appPath = appPath.concat("\\src\\main\\resources\\Android_AppUITesting.apk");
			System.out.println(appPath);
			DesiredCapabilities appiumSettings = new DesiredCapabilities();
			appiumSettings.setCapability("platform", platform.toString());
			appiumSettings.setCapability("platformVersion", platformVersion);
			appiumSettings.setCapability("deviceName", deviceName);
			//appiumSettings.setCapability("avd", /*this.GetAVD()*/ "Nexus6P"); //removed to avoid emulation
			appiumSettings.setCapability("app", appPath);
			appiumSettings.setCapability("automationName", automationName);
			return appiumSettings;
		}
		else
		{
			return new DesiredCapabilities();
		}
		
	}
	
	private String LaunchCMD(String cmd) throws Exception 
	{
		String results = null;
		Process p;
		try 
		{
			p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			
			while ((line = in.readLine()) != null) 
			{
			    System.out.println(line);
			    if(!line.isEmpty())
			    {
			    	results = line;
			    }
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(results == null)
		{
			throw new Exception("Connect an Android phone and ensure USB Debugging is enabled");
		}
		return results;   
	}	

	public String toString() 
	{
		String result = "Platform: " + platform.toString() + "          Platform Version: " + platformVersion + "          DeviceName: " + deviceName + "          AVD: " + avd + "          APP: " + app + "          AutomationName: " + automationName;
		return result;
	}
	
	public DevicePlatform GetPlatform(){return platform; }
	
	public String GetPlatformVersion() 
	{		
		return platformVersion;
	}
	
	public String GetDeviceName() 
	{ 
		return deviceName;
	}
	
	public String GetAVD() { return avd; }
	public String GetApp() { return app; }
	public String GetAutomationName() { return automationName; }	
	public void SetPlatform(String plat){platform = DevicePlatform.valueOf(plat);}
	public void SetPlatformVersion (String s){platformVersion = s;}
	public void SetDeviceName (String s){deviceName = s;}
	public void SetAVD (String s){avd = s;}
	public void SetApp (String s){app = s;}
	public void SetAutomationName (String s){automationName = s;}
}
