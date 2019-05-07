package test.java;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyRunnerTest {
	public static void main(String[] args) {
//		try {
//			System.out.println("Waiting 30 seconds");
//			Thread.sleep(30000);
//		}catch(Exception e) {
//			
//		}
//		Result result = JUnitCore.runClasses(MyTest.class);
//		for(Failure failure : result.getFailures()) {
//			System.out.println(failure.toString());
//		}
		
		
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		junit.run(MyTest.class);
	}
}
