package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class CPXTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testCPXImmediateEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000, 0xE0);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCPXImmediateGreaterThan() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000, 0xE0);
		cpu.write(0x8001, 0x43);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCPXImmediateLessThan() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x40);
		cpu.write(0x8000, 0xE0);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(1, cpu.getSign());
		assertEquals(0, cpu.getCarry());
	}
	
	public void testCPXZeroPageEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000, 0xE4);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(3, cycles);
	}
	
	public void testCPXAbsoluteEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000, 0xEC);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(4, cycles);
	}

}
