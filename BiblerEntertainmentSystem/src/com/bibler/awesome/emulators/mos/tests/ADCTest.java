package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class ADCTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testADCImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x69);
		cpu.write(0x8001, 0x44);
		cpu.setAccumulator(0x04);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x48, cpu.getAccumulator());
		assertEquals(2, cycles);
	}
	
	public void testADCZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x65);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		cpu.setAccumulator(0x04);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x48, cpu.getAccumulator());
		assertEquals(3, cycles);
	}
	
	public void testADCZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0x75);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		cpu.setAccumulator(0x04);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x48, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x6D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		cpu.setAccumulator(0x04);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x48, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0x7D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		cpu.setAccumulator(0x04);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x48, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testAbsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.write(0x8000, 0x79);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		cpu.setAccumulator(0x04);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x48, cpu.getAccumulator());
		assertEquals(4, cycles);
	}

}
