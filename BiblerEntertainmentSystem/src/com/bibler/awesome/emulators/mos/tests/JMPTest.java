package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class JMPTest extends TestCase {
	
	/*
	 * 	MODE           SYNTAX       HEX LEN TIM
		Absolute      JMP $5597     $4C  3   3
		Indirect      JMP ($5597)   $6C  3   5
	 */
	
	private CPU6502 cpu;
	
	public void testJMPAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x4C);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x4400, cpu.getPC());
		assertEquals(3, cycles);
	}
	
	public void testJMPIndirect() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x6C);
		cpu.write(0x8001, 0xFF);
		cpu.write(0x8002, 0x40);
		cpu.write(0x40FF, 0x00);
		cpu.write(0x4000, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x4400, cpu.getPC());
		assertEquals(5, cycles);
	}

}
