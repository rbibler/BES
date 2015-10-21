package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class DECTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testDECZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xC6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x43, cpu.read(0x44));
		assertEquals(5, cycles);
	}
	
	public void testDECZeroPageWrap() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xC6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x00);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0xFF, cpu.read(0x44));
		assertEquals(5, cycles);
	}
	
	public void testDECZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xD6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x43, cpu.read(0x47));
		assertEquals(6, cycles);
	}
	
	public void testDECAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xCE);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x43, cpu.read(0x4400));
		assertEquals(6, cycles);
	}
	
	public void testDECAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xDE);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x43, cpu.read(0x4403));
		assertEquals(7, cycles);
	}

}
