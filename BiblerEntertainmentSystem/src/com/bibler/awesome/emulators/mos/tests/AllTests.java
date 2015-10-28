package com.bibler.awesome.emulators.mos.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	
	/*
	 * ADC		X
	 * AND		X
	 * ASL		X
	 * BCC		X	
	 * BCS		X
	 * BEQ		X
	 * BIT		X
	 * BMI		X
	 * BNE		X
	 * BPL		X
	 * BRK		-
	 * BVC		X
	 * BVS		X
	 * CLC		X
	 * CLD		X
	 * CLI		X
	 * CLV		X
	 * CMP		X
	 * CPX		X
	 * CPY		X
	 * DEC		X
	 * DEX		X
	 * DEY		X
	 * EOR		X
	 * INC		X
	 * INX		X
	 * INY		X
	 * JMP		X
	 * JSR		X
	 * LDA		X
	 * LDX		X
	 * LDY		X
	 * LSR		X
	 * NOP		X
	 * ORA		X
	 * PHA		X
	 * PHP		X
	 * PLA		X
	 * PLP		X
	 * ROL		X
	 * ROR		X			
	 * RTI		X
	 * RTS		X
	 * SBC		X
	 * SED		X
	 * SEI		X
	 * STA		X
	 * STX		X
	 * STY		X
	 * TAX		X
	 * TAY		X
	 * TSX		X
	 * TXA		X
	 * TXS		X
	 * TYA		X
	 */	

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
		suite.addTestSuite(JMPTest.class);
		suite.addTestSuite(JSRTest.class);
		suite.addTestSuite(LDATest.class);
		suite.addTestSuite(LDXTest.class);
		suite.addTestSuite(LDYTest.class);
		suite.addTestSuite(LSRTest.class);
		suite.addTestSuite(NOPTest.class);
		suite.addTestSuite(ORATest.class);
		suite.addTestSuite(RegisterStatusTests.class);
		suite.addTestSuite(RegisterInstructionsTest.class);
		suite.addTestSuite(ROLTest.class);
		suite.addTestSuite(RORTest.class);
		suite.addTestSuite(RTITest.class);
		suite.addTestSuite(RTSTest.class);
		suite.addTestSuite(SBCTest.class);
		suite.addTestSuite(StackInstructionsTest.class);
		suite.addTestSuite(STATest.class);
		suite.addTestSuite(STXTest.class);
		suite.addTestSuite(STYTest.class);
		//$JUnit-END$
		return suite;
	}

}
