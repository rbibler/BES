package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class RegisterInstructionsTest extends TestCase {
	
	/*
	 * Affect Flags: S Z

		These instructions are implied mode, have a length of one byte and require two machine cycles.

		MNEMONIC                 HEX
		TAX (Transfer A to X)    $AA
		TXA (Transfer X to A)    $8A
		DEX (DEcrement X)        $CA
		INX (INcrement X)        $E8
		TAY (Transfer A to Y)    $A8
		TYA (Transfer Y to A)    $98
		DEY (DEcrement Y)        $88
		INY (INcrement Y)        $C8
	 */
	
	private CPU6502 cpu;
	
	public void testTAX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setX(0xFE);
		cpu.write(0x8000, 0xAA);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testTXA() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0xFE);
		cpu.setX(0x44);
		cpu.write(0x8000, 0x8A);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testDEX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000, 0xCA);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x43, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testINX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x44);
		cpu.write(0x8000, 0xE8);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testDEXWrap() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0x00);
		cpu.write(0x8000, 0xCA);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0xFF, cpu.getX());
		assertEquals(0, cpu.getZero());
		assertEquals(1, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testINXWrap() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0xFF);
		cpu.write(0x8000, 0xE8);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getX());
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testTAY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setY(0xFE);
		cpu.write(0x8000, 0xA8);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getY());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testTYA() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0xFE);
		cpu.setY(0x44);
		cpu.write(0x8000, 0x98);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testDEY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000, 0x88);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x43, cpu.getY());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testINY() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x44);
		cpu.write(0x8000, 0xC8);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x45, cpu.getY());
		assertEquals(0, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testDEYWrap() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0x00);
		cpu.write(0x8000, 0x88);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0xFF, cpu.getY());
		assertEquals(0, cpu.getZero());
		assertEquals(1, cpu.getSign());
		assertEquals(2, cycles);
	}
	
	public void testINYWrap() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setY(0xFF);
		cpu.write(0x8000, 0xC8);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getY());
		assertEquals(1, cpu.getZero());
		assertEquals(0, cpu.getSign());
		assertEquals(2, cycles);
	}

}
