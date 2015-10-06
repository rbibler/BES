package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class JMPTest extends TestCase {
	
	private CPU cpu;
	private MemoryManager mem;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
	}
	
	public void testJMPAbs() {
		setup();
		mem.write(0x20, 0x4C);
		mem.write(0x21, 0xDE);
		mem.write(0x22, 0x00);
		mem.write(0xDE, 0xE8);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x00DE, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.X);
		assertEquals(0x00DF, cpu.PC);
	}

	public void testJMPIndirect() {
		setup();
		mem.write(0x20, 0x6C);
		mem.write(0x21, 0xDE);
		mem.write(0x22, 0x00);
		mem.write(0x00DE, 0x40);
		mem.write(0x00DF, 0x40);
		mem.write(0x4040, 0xE8);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x4040, cpu.PC);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.X);
		assertEquals(0x4041, cpu.PC);
	}

}
