package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class ROLTest extends TestCase {
	
	/*
	 * Affects Flags: S Z C

		MODE           SYNTAX       HEX LEN TIM
		Accumulator   ROL A         $2A  1   2
		Zero Page     ROL $44       $26  2   5
		Zero Page,X   ROL $44,X     $36  2   6
		Absolute      ROL $4400     $2E  3   6
		Absolute,X    ROL $4400,X   $3E  3   7

	 */
	
	private CPU6502 cpu;
	
	public void testROLAccumulator() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0b10000001);
		cpu.write(0x8000, 0x2A);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.getAccumulator());
		assertEquals(1, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testROLAccumulatorWithCarry() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0b10000001);
		cpu.updateCarry(1);
		cpu.write(0x8000, 0x2A);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0b00000011, cpu.getAccumulator());
		assertEquals(1, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
	}
	
	public void testROLZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x26);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44,  0b10000001);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.read(0x44));
		assertEquals(1, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(5, cycles);
	}
	
	public void testROLZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0x36);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47,  0b10000001);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.read(0x47));
		assertEquals(1, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(6, cycles);
	}
	
	public void testROLAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x2E);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400,  0b10000001);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.read(0x4400));
		assertEquals(1, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(6, cycles);
	}
	
	public void testROLAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(0);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0x3E);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403,  0b10000001);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0b00000010, cpu.read(0x4403));
		assertEquals(1, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(7, cycles);
	}

}
