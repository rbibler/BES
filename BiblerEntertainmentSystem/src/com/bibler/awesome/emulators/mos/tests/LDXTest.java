package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class LDXTest extends TestCase {
	
	/*
	MODE           SYNTAX       HEX LEN TIM
	Immediate     LDX #$44      $A2  2   2
	Zero Page     LDX $44       $A6  2   3
	Zero Page,Y   LDX $44,Y     $B6  2   4
	Absolute      LDX $4400     $AE  3   4
	Absolute,Y    LDX $4400,Y   $BE  3   4+
	*/
	
	private CPU6502 cpu;
	
	public void testLDXImmediate() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xA2);
		cpu.write(0x8001, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testLDXZeroPage() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xA6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x44, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(3, cycles);
	}
	
	public void testLDXZeroPageY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.setX(3);
		cpu.write(0x8000, 0xB6);
		cpu.write(0x8001, 0x44);
		cpu.write(0x47, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}
	
	public void testLDXABsolute() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xAE);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4400, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}
	
	public void testLDXABsoluteY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(3);
		cpu.setAccumulator(0);
		cpu.write(0x8000, 0xBE);
		cpu.write(0x8001, 0x00);
		cpu.write(0x8002, 0x44);
		cpu.write(0x4403, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(4, cycles);
	}

}
