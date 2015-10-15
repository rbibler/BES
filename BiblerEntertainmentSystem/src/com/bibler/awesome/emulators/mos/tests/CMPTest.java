package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class CMPTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testCMPImmediateEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000, 0xC9);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCMPImmediateGreaterThan() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000, 0xC9);
		cpu.write(0x8001, 0x43);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCMPImmediateLessThan() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x40);
		cpu.write(0x8000, 0xC9);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getZero());
		assertEquals(1, cpu.getSign());
		assertEquals(0, cpu.getCarry());
	}
	
	public void testCMPZeroPageEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000, 0xC5);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(3, cycles);
	}
	
	public void testCMPZeroPageXEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setX(3);
		cpu.write(0x8000, 0xD5);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(4, cycles);
	}
	
	public void testCMPAbsoluteEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000, 0xCD);
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
	
	public void testCMPAbsoluteXEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setX(3);
		cpu.write(0x8000, 0xDD);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(4, cycles);
	}
	
	public void testCMPAbsoluteYEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setY(3);
		cpu.write(0x8000, 0xD9);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(4, cycles);
	}
	
	public void testCMPIndirectXEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setX(3);
		cpu.write(0x8000, 0xC1);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x00);
		cpu.write(0x48, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(6, cycles);
	}
	
	public void testCMPIndirectYEqual() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setY(3);
		cpu.write(0x8000, 0xD1);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x00);
		cpu.write(0x45, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(1, cpu.getCarry());
		assertEquals(5, cycles);
	}

}
