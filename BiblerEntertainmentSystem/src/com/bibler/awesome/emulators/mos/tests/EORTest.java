package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class EORTest extends TestCase {

	private CPU6502 cpu;
	
	public void testEORImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.write(0x8000, 0x49);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(2, cycles);
	}
	
	public void testEORZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.write(0x8000, 0x45);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44,  0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(3, cycles);
	}
	
	public void testEORZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.setX(3);
		cpu.write(0x8000, 0x55);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47,  0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testEORAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.write(0x8000, 0x4D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testEORAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.setX(3);
		cpu.write(0x8000, 0x5D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testEORIndirectX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.setX(3);
		cpu.write(0x8000, 0x41);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47,  0x00);
		cpu.write(0x48, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(6, cycles);
	}
	
	public void testEORIndirectY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.setY(3);
		cpu.write(0x8000, 0x51);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44,  0x00);
		cpu.write(0x45, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getAccumulator());
		assertEquals(5, cycles);
	}
}
