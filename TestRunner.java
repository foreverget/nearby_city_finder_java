//importing libraries
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//test runner class
public class TestRunner {
	
	//main methd	
	public static void main(String[] args) {

		//making object of Result class	
		Result resultA = JUnitCore.runClasses(DBTest.class);

		//if fails
		for (Failure failureA : resultA.getFailures()) {
			System.out.println(failureA.toString());
		}

		//if passes
		System.out.println("DB Connection Test: "+resultA.wasSuccessful());
	}
}  	