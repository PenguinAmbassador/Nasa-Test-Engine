package main.java.nasaTestSuite;

public class PhoneConfig 
{
	private int offset;
	private boolean accurateOffset = false;
	private boolean newDevice = false;
	private String deviceName = null;
	private String OS = null;
	
	/**
	 * Store information like tap offset based on parameters.
	 * @param deviceName Passed in from TestCapabilities (example: Nexus 6P)
	 * @param OS Passed in from TestCapabilities (example: 7.1.1)
	 */
	public PhoneConfig(int defaultOffset, String deviceName, String OS)
	{
		this.deviceName = deviceName;
		this.OS = OS;
		switch (deviceName)
		{
			case "SAMSUNG-SM-G930A":
				deviceName = "S6 - SAMSUNG-SM-G930A";
				offset = 115;
				accurateOffset = true;
				newDevice = false;
				break;				
			case "Nexus 6P":
				offset = 165;
				accurateOffset = false;
				newDevice = false;
				break;
			default:
				System.out.println("NEW DEVICE");
				newDevice = true;
				offset = defaultOffset;
				accurateOffset = false;
				break;		
		}
	}
	
	public String toString() {
		String result = "DeviceName: " + deviceName + "          OS: " + OS + "          Offset: " + offset + "          Accurate Offset: " + accurateOffset + "          NewDevice: " + newDevice;
		return result;
	}

	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public boolean isAccurateOffset() {
		return accurateOffset;
	}


	public void setAccurateOffset(boolean accurateOffset) {
		this.accurateOffset = accurateOffset;
	}


	public boolean isNewDevice() {
		return newDevice;
	}


	public void setNewDevice(boolean newDevice) {
		this.newDevice = newDevice;
	}


	public String getDeviceName() {
		return deviceName;
	}


	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}


	public String getOS() {
		return OS;
	}


	public void setOS(String oS) {
		OS = oS;
	}	
}
