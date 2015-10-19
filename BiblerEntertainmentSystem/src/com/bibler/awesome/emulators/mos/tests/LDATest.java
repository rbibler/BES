package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class LDATest extends TestCase {
	
	/*
	MODE           SYNTAX       HEX LEN TIM
	Immediate     LDA #$44      $A9  2   2
	Zero Page     LDA $44       $A5  2   3
	Zero Page,X   LDA $44,X     $B5  2   4
	Absolute      LDA $4400     $AD  3   4
	Absolute,X    LDA $4400,X   $BD  3   4+
	Absolute,Y    LDA $4400,Y   $B9  3   4+
	Indirect,X    LDA ($44,X)   $A1  2   6
	Indirect,Y    LDA ($44),Y   $B1  2   5+
	*/
	
	private CPU6502 cpu;
	
	public void testLDAImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xA9);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testLDAZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xA5);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(3, cycles);
	}
	
	public void testLDAZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.setX(3);
		cpu.write(0x8000, 0xB5);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}
	
	public void testLDAABsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xAD);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}
	
	public void testLDAABsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xBD);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}
	
	public void testLDAABsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xB9);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}


}
