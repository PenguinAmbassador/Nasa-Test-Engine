package main.java.nasaTestSuite;

import org.openqa.selenium.Capabilities;

import io.appium.java_client.android.AndroidDriver;

public class AppiumExtension extends AndroidDriver {

	public AppiumExtension(Capabilities desiredCapabilities) {
		super(desiredCapabilities);
	}

}
