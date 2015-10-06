package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class STYTest extends TestCase {
	
	MemoryManager mem;
	CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
	}
	
	public void testSTYZP() {
		setup();
		cpu.PC = 0x20;
		cpu.Y = 0x10;
		mem.write(0x20, 0x84);
		mem.write(0x21, 0x40);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x40));
		
	}
	
	public void testSTYZPX() {
		setup();
		cpu.PC = 0x20;
		cpu.Y = 0x10;
		cpu.X = 1;
		mem.write(0x20, 0x94);
		mem.write(0x21, 0x40);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x41));
	}
	
	public void testSTYAbsolue() {
		setup();
		cpu.PC = 0x20;
		cpu.Y = 0x10;
		mem.write(0x20, 0x8C);
		mem.write(0x21, 0x4040);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x4040));
	}

}
