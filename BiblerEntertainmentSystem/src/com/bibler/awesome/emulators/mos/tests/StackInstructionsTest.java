package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class StackInstructionsTest extends TestCase {
	
	/*
	 * 	MNEMONIC                        HEX TIM
		TXS (Transfer X to Stack ptr)   $9A  2
		TSX (Transfer Stack ptr to X)   $BA  2
		PHA (PusH Accumulator)          $48  3
		PLA (PuLl Accumulator)          $68  4
		PHP (PusH Processor status)     $08  3
		PLP (PuLl Processor status)     $28  4
	 */
	
	private CPU6502 cpu;
	
	public void testTXS() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0xFE);
		cpu.write(0x8000, 0x9A);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0xFE, cpu.getSP());
		assertEquals(2, cycles);
	}
	
	public void testTSX() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setX(0xFE);
		cpu.setSP(0x44);
		cpu.write(0x8000, 0xBA);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getX());
		assertEquals(2, cycles);
	}
	
	public void testPHA() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setSP(0x44);
		cpu.write(0x8000, 0x48);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.stackPeek());
		assertEquals(0x43, cpu.getSP());
		assertEquals(3, cycles);
	}
	
	public void testPHAOverFlow() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setSP(0x00);
		cpu.write(0x8000, 0x48);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.stackPeek());
		assertEquals(0xFF, cpu.getSP());
		assertEquals(3, cycles);
	}
	
	public void testPLA() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0xFF);
		cpu.setSP(0xFE);
		cpu.write(0x8000, 0x68);
		cpu.write(0x1FF, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0xFF, cpu.getSP());
		assertEquals(4, cycles);
	}
	
	public void testPLAUnderflow() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0xFF);
		cpu.setSP(0xFF);
		cpu.write(0x8000, 0x68);
		cpu.write(0x100, 0x44);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0x00, cpu.getSP());
		assertEquals(4, cycles);
	}
	
	public void testPushAndPop() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setAccumulator(0x44);
		cpu.setSP(0xFF);
		cpu.write(0x8000, 0x48);
		cpu.write(0x8001, 0x68);
		int opCode = cpu.fetch();
		int cycles = cpu.execute(opCode);
		assertEquals(0x44, cpu.stackPeek());
		assertEquals(0xFE, cpu.getSP());
		cpu.setAccumulator(0xFE);
		cpu.execute(cpu.fetch());
		assertEquals(0x44, cpu.getAccumulator());
		assertEquals(0xFF, cpu.getSP());
	}
	
	public void testPHP() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setStatusRegister(0);
		cpu.updateCarry(1);
		cpu.updateZero(1);
		cpu.updateSign(1);						// Status Register is 0b10000011 or 0x83
		cpu.setSP(0xFF);
		cpu.write(0x8000, 0x08);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x83, cpu.stackPeek());
		assertEquals(0xFE, cpu.getSP());
		assertEquals(3, cycles);
	}
	
	public void testPLP() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.setSP(0xFE);
		cpu.write(0x8000, 0x28);
		cpu.write(0x1FF, 0x83);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0x83, cpu.getStatusRegister());
		assertEquals(0xFF, cpu.getSP());
		assertEquals(4, cycles);
	}

}
