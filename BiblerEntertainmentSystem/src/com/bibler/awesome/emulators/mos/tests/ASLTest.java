package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class ASLTest extends TestCase {
	
	private CPU6502 cpu;
	
	public void testASLAccumulator() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10, cpu.getAccumulator());
		assertEquals(cycles, 2);
	}
	
	public void testASLAccumulatorCarryFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10, cpu.getAccumulator());
		assertEquals(cycles, 2);
		assertEquals(0, cpu.getCarry());
	}
	
	public void testASLAccumulatorCarryTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b10000001);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.getAccumulator());
		assertEquals(cycles, 2);
		assertEquals(1, cpu.getCarry());
	}
	
	public void testASLAccumulatorSignFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b10000001);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.getAccumulator());
		assertEquals(cycles, 2);
		assertEquals(0, cpu.getSign());
	}
	
	public void testASLAccumulatorSignTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b01000001);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10000010, cpu.getAccumulator());
		assertEquals(cycles, 2);
		assertEquals(1, cpu.getSign());
	}
	
	public void testASLAccumulatorZeroFalse() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b01000001);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10000010, cpu.getAccumulator());
		assertEquals(cycles, 2);
		assertEquals(0, cpu.getZero());
	}
	
	public void testASLAccumulatorZeroTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0A);
		cpu.setAccumulator(0b10000000);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getAccumulator());
		assertEquals(cycles, 2);
		assertEquals(1, cpu.getZero());
	}
	
	public void testASLZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x06);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0b01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10, cpu.read(0x44));
		assertEquals(cycles, 5);
	}
	
	public void testASLZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setX(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x016);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0b01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10, cpu.read(0x47));
		assertEquals(cycles, 6);
	}
	
	public void testASLAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x0E);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0b01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10, cpu.read(0x4400));
		assertEquals(cycles, 6);
	}
	
	public void testASLAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setX(3);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x1E);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0b01);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0b10, cpu.read(0x4403));
		assertEquals(cycles, 7);
	}

}
