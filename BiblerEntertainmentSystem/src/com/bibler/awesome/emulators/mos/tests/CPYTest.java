package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class CPYTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testCPYImmediateEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000, 0xC0);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCPYImmediateGreaterThan() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000, 0xC0);
		cpu.write(0x8001, 0x43);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCPYImmediateLessThan() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x40);
		cpu.write(0x8000, 0xC0);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(1, cpu.getSign());
		assertEquals(0, cpu.getCarry());
	}
	
	public void testCPYZeroPageEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000, 0xC4);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(3, cycles);
	}
	
	public void testCPYAbsoluteEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000, 0xCC);
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
