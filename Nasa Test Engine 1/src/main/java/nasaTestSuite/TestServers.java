package main.java.nasaTestSuite;

import java.net.MalformedURLException;
import java.net.URL;

public class TestServers 
{
	 public static URL LocalServer () throws MalformedURLException
	 {
		 return new URL("http://localhost:4723/wd/hub");	 
	 }
}