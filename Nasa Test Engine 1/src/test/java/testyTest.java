package test.java;

import org.junit.BeforeClass;
import org.junit.Ignore;

import main.java.nasaTestSuite.MyXPath;
import main.java.nasaTestSuite.Stromboli;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.apache.tools.ant.util.SymbolicLinkUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import main.java.nasaTestSuite.Appliance;
import main.java.nasaTestSuite.Dehum;
import main.java.nasaTestSuite.FrigiDriver;
import main.java.nasaTestSuite.MyXPath;
//@Ignore
public class testyTest extends Base
{
	@BeforeClass//("^This code opens the app$")
	public static void launchMyTest()
	{
		//Setup app
		System.out.println("Testy Test");
		setupApp();
		
		//Sign in
	    strombo.signIn("eluxtester1@gmail.com", "123456");
		System.out.println("PASS: Sign In");
	    System.out.println();
		strombo.openControls("Strombo");
		if(!strombo.isPowerOn()) {
			System.out.println("Turning on appliance");
			frigi.tapByXPath(MyXPath.plainPowerButton, frigi.BUTTON_WAIT);
		}
//		strombo.changeModeToCoolorEcon();
//		strombo.openSettings();
	}
	
	@Test
	public void testAssertFail() 
	{
		System.out.println("fail");
		try {
			fail();			
		} catch (Exception e) {
			System.out.println("fail caught");
		}
	}
	
	@Test
	public void testAssertPass1() 
	{
		System.out.println("pass");
	}
	
	
	@Test
	public void testAssertPass2() 
	{
		System.out.println("pass");
	}
	
	@Test
	public void testAssertPass3() 
	{
		System.out.println("fail");
		try {
			fail();			
		} catch (Exception e) {
			System.out.println("fail caught");
		}
	}
	
	@Test
	public void testAssertPass4() 
	{
		System.out.println("pass");
	}
	
	@Test
	public void testAssertPass5() 
	{
		System.out.println("pass");
	}
	
	@Test
	public void testAssertPass6() 
	{
		System.out.println("pass");
	}
}