package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class NOPTest extends TestCase {
	/*
	 * 	MODE           SYNTAX       HEX LEN TIM
	 *	Implied        NOP          $EA  1   2
	 */
	
	private CPU6502 cpu;
	
	public void testNOP() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xEA);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(2, cycles);
		assertEquals(0x8001, cpu.getPC());
	}

}
