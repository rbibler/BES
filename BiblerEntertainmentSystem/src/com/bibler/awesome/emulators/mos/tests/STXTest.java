package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class STXTest extends TestCase {
	
	/*
	 * Affects Flags: none

		MODE           SYNTAX       HEX LEN TIM
		Zero Page     STX $44       $86  2   3
		Zero Page,Y   STX $44,Y     $96  2   4
		Absolute      STX $4400     $8E  3   4   
	 */
	
	private CPU6502 cpu;
	
	public void testSTXZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000,  0x86);
		cpu.write(0x8001,  0x44);
		cpu.write(0x44, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x44));
		assertEquals(3, cycles);
	}
	
	public void testSTXZeroPageY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.setX(0x44);
		cpu.write(0x8000,  0x96);
		cpu.write(0x8001,  0x44);
		cpu.write(0x47, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x47));
		assertEquals(4, cycles);
	}
	
	public void testSTXAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000,  0x8E);
		cpu.write(0x8001,  0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4400));
		assertEquals(4, cycles);
	}
	
}
