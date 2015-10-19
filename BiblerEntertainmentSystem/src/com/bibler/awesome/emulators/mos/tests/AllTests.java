package com.bibler.awesome.emulators.mos.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(ADCTest.class);
		suite.addTestSuite(ANDTest.class);
		suite.addTestSuite(ASLTest.class);
		suite.addTestSuite(BITTest.class);
		suite.addTestSuite(BranchTests.class);
		suite.addTestSuite(CMPTest.class);
		suite.addTestSuite(CPXTest.class);
		suite.addTestSuite(CPYTest.class);
		suite.addTestSuite(DECTest.class);
		suite.addTestSuite(EORTest.class);
		suite.addTestSuite(INCTest.class);
		suite.addTestSuite(LDATest.class);
		suite.addTestSuite(LDXTest.class);
		suite.addTestSuite(LDYTest.class);
		suite.addTestSuite(LSRTest.class);
		suite.addTestSuite(NOPTest.class);
		suite.addTestSuite(RegisterTests.class);
		//$JUnit-END$
		return suite;
	}

}
