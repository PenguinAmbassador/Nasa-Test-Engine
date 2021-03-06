package main.java.nasaTestSuite;

public class MyXPath {
    //screens
    public static String signInRegisterScreen = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView";
    public static String registerScreen = "	/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View/android.view.View[2]/android.widget.Button[2]"; //register button
    public static String verifyEmailScreen = ""; //leads back to sign in with back button
    public static String signInTypeScreen = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[5]/android.widget.Button"; //sign in button

    //updateScreen
    public static String updateButton = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[3]/android.widget.LinearLayout/android.widget.Button";
    //signInRegisterScreen
    public static String signInOne = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button[1]";
    //signInScreen
    public static String emailField = "//input[@id='email']";
    public static String passField = "//input[@id='password']";
    public static String signInTwo = "//button[@id='sign-in--button']";

    //firstApplianceScreen
    public static String addAppliance = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.Button";

    //applianceList
    public static String stromboListCard = "//h3[contains(text(),'Strombo') or contains(text(),'strombo')]";
    public static String racListCard = "//h3[contains(text(),'Rac') or contains(text(),'rac')]";
    public static String dehumListCard = "//h3[contains(text(),'Dehum') or contains(text(),'dehum')]";
    
    //applianceOneControlHUMID
    public static String plainPowerButton = "//a[contains(@class,'single--action-wrap power-button-off')]";
    public static String powerOnButton = "//a[contains(@class,'action--button icon-power-button-on on hide')]";//STATE OFF if this shows
    public static String powerOffButton = "//a[contains(@class,'power-button-off') and contains(@class,'hide')]";//STATE ON if this shows
    public static String powerButton = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[1]/android.view.View[1]";
    public static String targHumidity = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[1]/android.widget.ListView/android.view.View[12]/android.view.View[1]";
    public static String contHumidity = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[1]/android.widget.ListView/android.view.View[1]";
    public static String currentHumidityText = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[1]/android.view.View[1]";
    public static String humidPlusButton = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[1]/android.view.View[3]";
    public static String humidMinusButton = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[1]/android.view.View[2]";
    public static String dSpeedUp = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[2]/android.view.View[3]";
    public static String racSpeedDown = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[1]/android.view.View/android.view.View[2]/android.view.View[2]";
//    public static String thinking = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[1]/android.view.View[2]";//this should help prevent us from clicking while thinking
    public static String thinking = "//div[@class='loading--content']";//this should help prevent us from clicking while thinking

    public static String settingsButton = "//a[@class='icon icon-settings icon--align-right pull-right']";
    public static String applianceNameField = "//input[@id='applianceLabel']";
    public static String cleanAirToggle = "//div[@id='cleanair-toggle']"; //if OFF class="toggle" when OFF     if ON class="toggle active"
    public static String sleepModeToggle = "//div[@id='sleep-toggle']";//if OFF class="toggle" when OFF     if ON class="toggle active"
    public static String notificationToggle = "//div[@id='notification-toggle']";//if OFF class="toggle" when OFF     if ON class="toggle active"
    public static String timeZoneOutterButton = "//span[@id='timezone-label']";//CONTAINS EASTERN: "//span[@id='timezone-label' and contains(text(),'Eastern')]"
    public static String timeZoneInnerButton0 = "//label[@for='timezone-0']"; //timezone indices vary from 0 - 11
    public static String unitToggle = "//div[@id='temperature-toggle']"; //IF C class="toggle toggle-select" IF F class="toggle toggle-select active"
    public static String versionInfoButton = "//a[@href='#niurecord']";
    public static String removeApplianceButton = "//a[@href='#remove-appliance']";
    
//    public static String settingsButton = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]";
    public static String dehumNameText = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.ListView/android.view.View[2]/android.widget.EditText";
    public static String dehumNameValue = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[3]";
    public static String airFilterStatus = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.ListView/android.view.View[4]";
    public static String notificationToggleBrokenMaybe = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.ListView/android.view.View[6]/android.view.View[2]";
    public static String versionInfo = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.ListView/android.view.View[10]/android.view.View";
    public static String removeAppliance = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[4]/android.view.View[2]/android.widget.ListView/android.view.View[11]/android.view.View";
        
    //strombo
    public static String stromboCurrentFanSpeed = "//div[@class='settings--controls settings--fan rac--controls settings--always-show-icons']//li[contains(@class,'current')]";
    public static String stromboTargetTemp = "//div[@class='settings--controls settings--temp rac--controls degree--symbol settings--always-show-icons']//li[@class='settings--item settings--fade settings--current']";
    public static String stromboCurrentTemp = "//div[@class='settings--controls settings--temp rac--controls degree--symbol settings--always-show-icons']//p[@class='settings--description']";
    public static String stromboCurrentMode = "//div[@class='settings--controls settings--mode rac--controls settings--always-show-icons']//li[contains(@class,'current')]";
	public static String stromboModeUp = "//div[@class='settings--controls settings--mode rac--controls settings--always-show-icons']//a[@class='settings--toggle settings--toggle-next']";
	public static String stromboModeDown = "//div[@class='settings--controls settings--mode rac--controls settings--always-show-icons']//a[@class='settings--toggle settings--toggle-prev']";
    public static String stromboTempUp = "//div[@class='settings--controls settings--temp rac--controls degree--symbol settings--always-show-icons']//a[@class='settings--toggle settings--toggle-next']";
	public static String stromboTempDown = "//div[@class='settings--controls settings--temp rac--controls degree--symbol settings--always-show-icons']//a[@class='settings--toggle settings--toggle-prev']";
	public static String stromboSpeedup = "//div[@class='settings--controls settings--fan rac--controls settings--always-show-icons']//a[@class='settings--toggle settings--toggle-next']";
	public static String stromboSpeedDown = "//div[@class='settings--controls settings--fan rac--controls settings--always-show-icons']//a[@class='settings--toggle settings--toggle-prev']";
    
    //universal
	public static String timer = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[3]/android.view.View[1]";
    public static String backButton = "//a[contains(@class,'icon icon-arrow-header-prev pull-left')]";
    
	

//    public static String timeZone0 = "//label[@for='timezone-0']"; //timezone indices vary from 0 - 11
    /**
     * Testing Idea for getting timezone xpath
     * @param zoneIndex
     * @return
     */
    public static String getTimeZoneInnerButton(int zoneIndex) {
    	return "//label[@for='timezone-" + zoneIndex + "']";
    }
}
