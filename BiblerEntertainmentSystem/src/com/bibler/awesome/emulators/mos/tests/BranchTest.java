package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class BranchTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
	}
	
	public void testBPL() {
		setup();
		cpu.negative = 0;
		cpu.X = 0;
		mem.write(0x20, 0x10);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBPLbackwards() {
		setup();
		cpu.negative = 0;
		cpu.X = 0;
		mem.write(0x20, 0x10);
		mem.write(0x21, 0xFB);
		mem.write(0x1D, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x1D, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBMI() {
		setup();
		cpu.negative = 1;
		cpu.X = 0;
		mem.write(0x20, 0x30);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBVC() {
		setup();
		cpu.overflow = 0;
		cpu.X = 0;
		mem.write(0x20, 0x50);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBVS() {
		setup();
		cpu.overflow = 1;
		cpu.X = 0;
		mem.write(0x20, 0x70);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBCC() {
		setup();
		cpu.carry = 0;
		cpu.X = 0;
		mem.write(0x20, 0x90);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBCS() {
		setup();
		cpu.carry = 1;
		cpu.X = 0;
		mem.write(0x20, 0xB0);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBNE() {
		setup();
		cpu.zero = 0;
		cpu.X = 0;
		mem.write(0x20, 0xD0);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	public void testBEQ() {
		setup();
		cpu.zero = 1;
		cpu.X = 0;
		mem.write(0x20, 0xF0);
		mem.write(0x21, 0x40);
		mem.write(0x62, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x62, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.X);
	}
	
	

}
