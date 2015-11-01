package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class SBCTest extends TestCase {
	
	/*
	 * 	Affects Flags: S V Z C

		MODE           SYNTAX       HEX LEN TIM
		Immediate     SBC #$44      $E9  2   2
		Zero Page     SBC $44       $E5  2   3
		Zero Page,X   SBC $44,X     $F5  2   4
		Absolute      SBC $4400     $ED  3   4
		Absolute,X    SBC $4400,X   $FD  3   4+
		Absolute,Y    SBC $4400,Y   $F9  3   4+
		Indirect,X    SBC ($44,X)   $E1  2   6
		Indirect,Y    SBC ($44),Y   $F1  2   5+
	 */
	
	private CPU6502 cpu;
	
	public void testSBCImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x50);
		cpu.write(0x8000, 0xE9);
		cpu.write(0x8001, 0xF0);
		cpu.updateCarry(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getOverflow());
	}
	
	public void testSBCImmediateOverflow() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x50);
		cpu.write(0x8000, 0xE9);
		cpu.write(0x8001, 0xB0);
		cpu.updateCarry(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0xA0, cpu.getAccumulator());
		assertEquals(0, cpu.getCarry());
		assertEquals(1, cpu.getOverflow());
	}
	
	public void testSBCImmediate3() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x50);
		cpu.write(0x8000, 0xE9);
		cpu.write(0x8001, 0x70);
		cpu.updateCarry(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0xE0, cpu.getAccumulator());
		assertEquals(0, cpu.getCarry());
		assertEquals(0, cpu.getOverflow());
	}
	
	public void testSBCImmediateNoBorrowOverflow() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0xD0);
		cpu.write(0x8000, 0xE9);
		cpu.write(0x8001, 0x70);
		cpu.updateCarry(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(1, cpu.getCarry());
		assertEquals(1, cpu.getOverflow());
	}
	
	public void testSBCZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xE5);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(3, cycles);
	}
	
	public void testSBCZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xF5);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testSBCAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xED);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testSBCAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xFD);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testSBCAbsoluteXPageBoundary() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setX(7);
		cpu.write(0x8000, 0xFD);
		cpu.write(0x8001, 0xFE);
		cpu.write(0x8002, 0x00);
		cpu.write(0x0105, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(5, cycles);
	}
	
	public void testSBCAbsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.write(0x8000, 0xF9);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(4, cycles);
	}
	
	public void testSBCAbsoluteYPageBoundary() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.write(0x8000, 0xF9);
		cpu.write(0x8001, 0xFE);
		cpu.write(0x8002, 0x00);
		cpu.write(0x101, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(5, cycles);
	}
	
	public void testSBCIndirectX() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.write(0x8000, 0xE1);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x00);
		cpu.write(0x48, 0x44);
		cpu.write(0x4400, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(6, cycles);
	}
	
	public void testSBCIndirectY() {
		cpu = CPU6502.getInstance(null);
		cpu.updateCarry(1);
		cpu.setPC(0x8000);
		cpu.setY(7);
		cpu.write(0x8000, 0xF1);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x10);
		cpu.write(0x45, 0x00);
		cpu.write(0x17, 0xF0);
		cpu.setAccumulator(0x50);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x60, cpu.getAccumulator());
		assertEquals(5, cycles);
	}

}
