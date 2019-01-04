package test.java;

import org.junit.BeforeClass;
import org.junit.Ignore;

import main.java.nasaTestSuite.MyXPath;
import main.java.nasaTestSuite.PhoneConfig;
import main.java.nasaTestSuite.Stromboli;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;
import main.java.nasaTestSuite.TestServers;

import static org.junit.Assert.fail;

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

@Ignore
public class SignInTest extends Base
{	
	@BeforeClass
	public static void beforeClass()
	{
		setupApp();
		System.out.println("SignIn Functionality Testing Start.");	
	}

	/**
	 * Run before every Sign In test to ensure that each test is not affected by an error that occurs in another test. 
	 */
	@Before
	public void resetErrors() {
		//Tried to change to click backbutton, try again with more specific xpath. 
		frigi.tapByXPath(MyXPath.backButton, frigi.BUTTON_WAIT);
		frigi.tapByXPath(MyXPath.signInOne, frigi.BUTTON_WAIT);
	}
	
	@Test
	public void Sign_In_Sign_Out() 
	{
		test.signInSignOutValidation();
	}
	
	@Test
	public void Empty_Email_Validation()
	{
		test.emptyEmailValidation();
	}
	
	@Test
	public void Empty_Password_Validation()
	{
		test.emptyPasswordValidation();
	}
	
	@Test
	public void Invalid_Email_Validation() 
	{
		test.printStartTest("Invalid Email Validation");
		test.invalidEmailValidation("eluxtester1@gmail.");
		resetErrors();
		test.invalidEmailValidation("eluxtester1@.com");
		resetErrors();
		test.invalidEmailValidation("eluxtester1@gmail.c om");
	}
	
	@Test
	public void Short_Password_Validation()
	{
		test.shortPasswordValidation();
	}
	
	@Test
	public void Credential_Validation()
	{
		test.printStartTest("Credential Validation");
		test.credentialValidation("eluxtester1@gmail.com", "12345");
		resetErrors();
		test.credentialValidation("wrongemail@gmail.com", "123456");
	}
	
	@Test
	public void forgotPassword() 
	{
		test.forgotPass("eluxtester1@gmail.com");
	}
	
	@Test
	public void showPassword() 
	{
		test.showPass();
	}
}