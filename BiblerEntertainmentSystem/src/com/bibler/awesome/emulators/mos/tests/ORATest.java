package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class ORATest extends TestCase {
	
	/*
	 * Affects Flags: S Z

		MODE           SYNTAX       HEX LEN TIM
		Immediate     ORA #$44      $09  2   2
		Zero Page     ORA $44       $05  2   3
		Zero Page,X   ORA $44,X     $15  2   4
		Absolute      ORA $4400     $0D  3   4
		Absolute,X    ORA $4400,X   $1D  3   4+
		Absolute,Y    ORA $4400,Y   $19  3   4+
		Indirect,X    ORA ($44,X)   $01  2   6
		Indirect,Y    ORA ($44),Y   $11  2   5+

	 */
	
	private CPU6502 cpu;
	
	public void testORAImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x04);
		cpu.write(0x8000, 0x09);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
		
	}
	
	public void testORAImmediateZeroTrue() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x00);
		cpu.write(0x8000, 0x09);
		cpu.write(0x8001, 0);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getAccumulator());
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		
	}
	
	public void testORAZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.write(0x8000, 0x05);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44,  0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(3, cycles);
		
	}
	
	public void testORAZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.setX(3);
		cpu.write(0x8000, 0x15);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47,  0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
		
	}
	
	public void testORAAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.write(0x8000, 0x0D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
		
	}
	
	public void testORAAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.setX(3);
		cpu.write(0x8000, 0x1D);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
		
	}
	
	public void testORAAbsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.setY(3);
		cpu.write(0x8000, 0x19);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002,  0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
		
	}

	public void testORAIndirectX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.setX(3);
		cpu.write(0x8000, 0x01);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47,  0x00);
		cpu.write(0x48, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(6, cycles);	
	}
	
	public void testORAIndirectY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(4);
		cpu.setY(3);
		cpu.write(0x8000, 0x11);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44,  0x00);
		cpu.write(0x45, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(5, cycles);
		
	}
}
