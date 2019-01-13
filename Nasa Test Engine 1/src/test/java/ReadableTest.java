package test.java;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.apache.commons.lang3.StringUtils;

/**
 * Allow Junit tests to be separated automatically by spaces using camel case. 
 * @author WoodmDav
 *
 */
public class ReadableTest extends BlockJUnit4ClassRunner {
	@Override
	 protected String testName(FrameworkMethod method) {
		//This code does not compile for unknown reasons. Probably depreciated code. 
//		return StringUtils.humanize(method.getMethod().getName());
		return "dumb broken thing";
	 }
	 
	public ReadableTest(Class<?> klass) throws InitializationError {
		super(klass);
	 }
	 
}
