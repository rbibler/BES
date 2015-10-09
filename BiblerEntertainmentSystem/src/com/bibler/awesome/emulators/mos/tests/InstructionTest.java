package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.Instruction;

import junit.framework.TestCase;

public class InstructionTest extends TestCase {
	
	private Instruction inst;
	private int opCode;
	private int cycles;
	private int length;
	private String mnemonic;
	private int memoryType;
	
	public void testInstruction() {
		inst = new Instruction();
		opCode = 1;
		cycles = 1;
		length = 1;
		mnemonic = "A9";
		memoryType = 1;
		inst.setOpCode(opCode);
		assertEquals(opCode, inst.getOpCode());
		inst.setCycles(cycles);
		assertEquals(cycles, inst.getCycles());
		inst.setLength(length);
		assertEquals(length, inst.getLength());
		inst.setMnemonic(mnemonic);
		assertEquals(mnemonic, inst.getMnemonic());
		inst.setMemoryType(memoryType);
		assertEquals(memoryType, inst.getMemoryType());
	}
	
	public void testInstructionConstructor() {
		opCode = 1;
		cycles = 1;
		length = 1;
		mnemonic = "A9";
		memoryType = 1;
		inst = new Instruction(opCode, mnemonic, length, cycles, memoryType);
		assertEquals(opCode, inst.getOpCode());
		assertEquals(mnemonic, inst.getMnemonic());
		assertEquals(length, inst.getLength());
		assertEquals(cycles, inst.getCycles());
		assertEquals(memoryType, inst.getMemoryType());
	}

}
