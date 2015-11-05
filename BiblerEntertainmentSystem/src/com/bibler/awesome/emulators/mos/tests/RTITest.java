package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class RTITest extends TestCase {
	
	/*
	 * 	MODE           SYNTAX       HEX LEN TIM
		Implied       RTI           $40  1   6
	 */
	
	private CPU6502 cpu;
	
	public void testRTI() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x40);
		cpu.setStatusRegister(0x00);
		cpu.stackPush(0x44);
		cpu.stackPush(0x00);
		cpu.stackPush(0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getStatusRegister());
		assertEquals(0x4401, cpu.getPC());
	}

}
