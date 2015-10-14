package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class BITTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testBITZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x24);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);				// 0b01000100
		cpu.setAccumulator(0);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(1, cpu.getOverflow());
		assertEquals(0, cpu.getSign());
		assertEquals(3, cycles);
	}
	
	public void testBITAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x2C);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		cpu.setAccumulator(0);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(1, cpu.getOverflow());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}
	
	public void testBITZeroPageSignTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x24);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x88);				// 0b01000100
		cpu.setAccumulator(0);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getOverflow());
		assertEquals(1, cpu.getSign());
	}
	
	public void testBITZeroPageZeroFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x24);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x88);				// 0b01000100
		cpu.setAccumulator(0b1000);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getOverflow());
		assertEquals(1, cpu.getSign());
	}

}
