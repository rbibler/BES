package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class BITTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 0;
	}
	
	public void testBITZP() {
		setup();
		mem.write(0x20, 0x24);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0xC1);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.accumulator);
		assertEquals(1, cpu.zero);
		assertEquals(1, cpu.negative);
		assertEquals(1, cpu.overflow);
	}
	
	public void testBITAbs() {
		setup();
		mem.write(0x20, 0x2C);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.accumulator);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.overflow);
	}
}
