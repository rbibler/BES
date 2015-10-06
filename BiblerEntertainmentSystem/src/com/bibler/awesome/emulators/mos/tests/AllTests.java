package com.bibler.awesome.emulators.mos.tests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(ANDTest.class);
		suite.addTestSuite(ASLTest.class);
		suite.addTestSuite(BITTest.class);
		suite.addTestSuite(BranchTest.class);
		suite.addTestSuite(ClearAndSetTests.class);
		suite.addTestSuite(CMPTest.class);
		suite.addTestSuite(CPXTest.class);
		suite.addTestSuite(CPYTest.class);
		suite.addTestSuite(EORTest.class);
		suite.addTestSuite(FetchTest.class);
		suite.addTestSuite(INCDECMemTest.class);
		suite.addTestSuite(JMPTest.class);
		suite.addTestSuite(JSRTest.class);
		suite.addTestSuite(LDATest.class);
		suite.addTestSuite(LDXTest.class);
		suite.addTestSuite(LDYTest.class);
		suite.addTestSuite(LSRTest.class);
		suite.addTestSuite(MemoryReadTest.class);
		suite.addTestSuite(MemoryWriteTest.class);
		suite.addTestSuite(ORTest.class);
		suite.addTestSuite(RegInstructionTests.class);
		suite.addTestSuite(ROLTest.class);
		suite.addTestSuite(RORTest.class);
		suite.addTestSuite(SBCTest.class);
		suite.addTestSuite(StackTest.class);
		suite.addTestSuite(STATest.class);
		suite.addTestSuite(STXTest.class);
		suite.addTestSuite(STYTest.class);
		suite.addTestSuite(TestAdd.class);
		
		
		//$JUnit-END$
		return suite;
	}

}
