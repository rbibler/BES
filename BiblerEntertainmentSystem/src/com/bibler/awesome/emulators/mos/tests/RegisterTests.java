package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

import junit.framework.TestCase;

public class RegisterTests extends TestCase {
	
	private CPU6502 cpu;
	
	public void testCLC() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x18);
		cpu.updateCarry(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getCarry());
	}
	
	public void testSEC() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x38);
		cpu.updateCarry(0);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getCarry());
	}
	
	public void testCLI() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x58);
		cpu.updateInterrupt(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getInterrupt());
	}
	
	public void testSEI() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0x78);
		cpu.updateInterrupt(0);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getInterrupt());
	}
	
	public void testCLV() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xB8);
		cpu.updateOverflow(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getOverflow());
	}
	
	public void testCLD() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xD8);
		cpu.updateDecimal(1);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(0, cpu.getDecimal());
	}
	
	public void testSED() {
		cpu = CPU6502.getInstance(null);
		cpu.setPC(0x8000);
		cpu.write(0x8000, 0xF8);
		cpu.updateDecimal(0);
		final int opCode = cpu.fetch();
		final int cycles = cpu.execute(opCode);
		assertEquals(1, cpu.getDecimal());
	}

}
