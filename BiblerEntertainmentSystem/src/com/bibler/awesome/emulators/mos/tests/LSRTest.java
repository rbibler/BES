package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class LSRTest extends TestCase {
	
	/*
	 * 		MODE           SYNTAX       HEX LEN TIM
	 *  	Accumulator   LSR A         $4A  1   2
	 *		Zero Page     LSR $44       $46  2   5
	 *		Zero Page,X   LSR $44,X     $56  2   6
	 * 		Absolute      LSR $4400     $4E  3   6
	 *		Absolute,X    LSR $4400,X   $5E  3   7
	 */
	
	private CPU6502 cpu;
	
	public void testLSRAccumulator() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000, 0x4A);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x22, cpu.getAccumulator());
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testLSRAccumulatorWithCarry() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(1);
		cpu.write(0x8000, 0x4A);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getAccumulator());
		assertEquals(1, cpu.getCarry());
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
	}
	
	public void testLSRZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x46);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44,  0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x22, cpu.read(0x44));
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(5, cycles);
	}
	
	public void testLSRZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0x56);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47,  0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x22, cpu.read(0x47));
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(6, cycles);
	}
	
	public void testLSRAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x4E);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x22, cpu.read(0x4400));
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(6, cycles);
	}
	
	public void testLSRAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0x5E);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x22, cpu.read(0x4403));
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(7, cycles);
	}

}
