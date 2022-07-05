package testSquidDevs;

import testSquidDevBoundary.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestDrivenDevelopment {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestLoginUi.class);
		System.out.println("Test successful? " + result.wasSuccessful());

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		
		}

	}

}
