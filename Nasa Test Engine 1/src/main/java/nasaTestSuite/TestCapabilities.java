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
	
	public DevicePlatform GetPlatform(){return platform; }
	
	public String GetPlatformVersion() 
	{
		try 
		{
			return LaunchCMD("adb shell getprop ro.build.version.release");
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	
	public String GetDeviceName() 
	{ 
		try 
		{
			return LaunchCMD("adb shell getprop ro.product.model");
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			return e.getMessage();
		}
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
	
	public TestCapabilities()
	{
		this.platform = DevicePlatform.Android;
		this.platformVersion = "";
		this.deviceName = "";
		this.avd = "";
		this.app = "";
		this.automationName = "";
	}
	
	public DesiredCapabilities AssignAppiumCapabilities()
	{
		String appPath = new File("").getAbsolutePath();
		if(appPath.contains("Users"))
		{
			appPath = appPath.concat("\\src\\main\\resources\\Android_v4.0.3_Test.apk");
			System.out.println(appPath);
			DesiredCapabilities appiumSettings = new DesiredCapabilities();
			appiumSettings.setCapability("platform", this.GetPlatform().toString());
			appiumSettings.setCapability("platformVersion", this.GetPlatformVersion());
			appiumSettings.setCapability("deviceName", this.GetDeviceName());
			//appiumSettings.setCapability("avd", /*this.GetAVD()*/ "Nexus6P"); //removed to avoid emulation
			appiumSettings.setCapability("app", appPath);
			appiumSettings.setCapability("automationName", "UiAutomator2");
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
	
	
	
}
