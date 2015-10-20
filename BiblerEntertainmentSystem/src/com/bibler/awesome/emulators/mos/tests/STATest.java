package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class STATest extends TestCase {
	
	/*
	 * 	MODE           SYNTAX       HEX LEN TIM
		Zero Page     STA $44       $85  2   3
		Zero Page,X   STA $44,X     $95  2   4
		Absolute      STA $4400     $8D  3   4
		Absolute,X    STA $4400,X   $9D  3   5
		Absolute,Y    STA $4400,Y   $99  3   5
		Indirect,X    STA ($44,X)   $81  2   6
		Indirect,Y    STA ($44),Y   $91  2   6    
	 */
	
	private CPU6502 cpu;
	
	public void testSTAZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x85);
		cpu.write(0x8001,  0x44);
		cpu.write(0x44, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x44));
		assertEquals(3, cycles);
	}
	
	public void testSTAZeroPageX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x95);
		cpu.write(0x8001,  0x44);
		cpu.write(0x47, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x47));
		assertEquals(4, cycles);
	}
	
	public void testSTAAbsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x8D);
		cpu.write(0x8001,  0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4400));
		assertEquals(4, cycles);
	}
	
	public void testSTAAbsoluteX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x9D);
		cpu.write(0x8001,  0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4403));
		assertEquals(5, cycles);
	}
	
	public void testSTAAbsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x99);
		cpu.write(0x8001,  0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4403));
		assertEquals(5, cycles);
	}
	
	public void testSTAIndirectX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(3);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x81);
		cpu.write(0x8001,  0x44);
		cpu.write(0x47, 0x00);
		cpu.write(0x48, 0x44);
		cpu.write(0x4400, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4400));
		assertEquals(6, cycles);
	}
	
	public void testSTAIndirectY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.setAccumulator(0x44);
		cpu.write(0x8000,  0x91);
		cpu.write(0x8001,  0x44);
		cpu.write(0x44, 0x00);
		cpu.write(0x45,  0x44);
		cpu.write(0x4403, 0xFF);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.read(0x4403));
		assertEquals(6, cycles);
	}
}
