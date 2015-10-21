package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class STYTest extends TestCase {
	
	/*
	 * Affects Flags: none

		MODE           SYNTAX       HEX LEN TIM
		Zero Page     STY $44       $84  2   3
		Zero Page,X   STY $44,X     $94  2   4
		Absolute      STY $4400     $8C  3   4   
	 */
	
	private CPU6502 cpu;
	
	public void testSTYZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000,  0x84);
		cpu.write(0x8001,  0x44);
		cpu.write(0x44, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x44));
		assertEquals(3, cycles);
	}
	
	public void testSTYZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.setY(0x44);
		cpu.write(0x8000,  0x94);
		cpu.write(0x8001,  0x44);
		cpu.write(0x47, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x47));
		assertEquals(4, cycles);
	}
	
	public void testSTYAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000,  0x8C);
		cpu.write(0x8001,  0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4400));
		assertEquals(4, cycles);
	}
	
}
