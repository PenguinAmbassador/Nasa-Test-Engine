package test.java;

import main.java.nasaTestSuite.Appliance;
import main.java.nasaTestSuite.FrigiDriver;
import main.java.nasaTestSuite.MyXPath;
import main.java.nasaTestSuite.PhoneConfig;
import main.java.nasaTestSuite.Stromboli;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.TestFunctions;
import main.java.nasaTestSuite.TestServers;

public class Base 
{	
	static int oneMinute = 60;

	public static FrigiDriver frigi = null;
	public static Stromboli strombo = null;
	public static Appliance app = null;
	public static TestFunctions test = null;	
	boolean testing = false;
	
	//TODO overload and if a offset is not used and the device is new then calculate a new offset.
	/**
	 * Launch appium, Frigidaire app, and tap sign in. 
	 * @param offset
	 */
	protected static void setupApp(int offset) 
	{
		//Starting the app and pressing the first button
		try {	
			TestCapabilities capabilities = new TestCapabilities();
			//TODO offset param not currently used, but would be a valuable default offset
			PhoneConfig phone = new PhoneConfig(capabilities.GetDeviceName(), capabilities.GetPlatformVersion());
			System.out.println("Capabilities: " + capabilities);
			System.out.println("Phone: " + phone);
			System.out.println("Temporarily removed update");
			frigi = new FrigiDriver(TestServers.LocalServer(), capabilities.AssignAppiumCapabilities(), phone); //was from 540-890
			frigi.useWebContext(); //required for hybrid apps
			app = new Appliance(frigi);
			test = new TestFunctions(frigi, app);	
			if(phone.isNewDevice()) {
				//s7 offset is 90 accurate.
				//s8 offset is 150? inaccurate.
				//NEXUS6P is 165 inaccurate.
				frigi.tapByXPath(MyXPath.signInOne, frigi.BUTTON_WAIT);
				frigi.calculateOffset();				
			} else {	
				frigi.tapByXPath(MyXPath.signInOne);
			}
			System.out.println("FRIGI OFFSET: " + frigi.getOffset()); //change offset back to protected later
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
