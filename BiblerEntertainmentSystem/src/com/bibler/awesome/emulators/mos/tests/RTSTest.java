package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class RTSTest extends TestCase {
	
	/*
	 * 	MODE           SYNTAX       HEX LEN TIM
		Implied       RTS           $60  1   6
	 */
	
	private CPU6502 cpu;
	
	public void testRTS() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.stackPush(0x44);
		cpu.stackPush(0x00);
		cpu.write(0x8000,  0x60);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x4401, cpu.getPC());
	}

}
