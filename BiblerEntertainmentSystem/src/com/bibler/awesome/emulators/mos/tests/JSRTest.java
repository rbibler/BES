package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class JSRTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.SP = 0xFF;
	}
	
	public void testJSR() {
		setup();
		mem.write(0x20, 0x20);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x23, 0xC8);
		mem.write(0x4000, 0xC8);
		mem.write(0x4001, 0x60);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x4000, cpu.PC);
		assertEquals(0x22, mem.read(0x01FE));
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.Y);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(3, cpu.Y);
	}
	
	

}
