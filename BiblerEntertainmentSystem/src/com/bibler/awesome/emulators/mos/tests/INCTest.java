package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class INCTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testINCZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xE6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.read(0x44));
		assertEquals(5, cycles);
	}
	
	public void testINCZeroPageWrap() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xE6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.read(0x44));
		assertEquals(5, cycles);
	}
	
	public void testINCZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xF6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.read(0x47));
		assertEquals(6, cycles);
	}
	
	public void testINCAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xEE);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.read(0x4400));
		assertEquals(6, cycles);
	}
	
	public void testINCAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xFE);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.read(0x4403));
		assertEquals(7, cycles);
	}

}
