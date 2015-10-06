package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class CPXTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.X = 3;
	}
	
	public void testCPXImm() {
		setup();
		mem.write(0x20, 0xE0);
		mem.write(0x21, 0x04);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);
	}
	
	public void testCPXZP() {
		setup();
		mem.write(0x20, 0xE4);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testCPXAbs() {
		setup();
		mem.write(0x20, 0xEC);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}


}
