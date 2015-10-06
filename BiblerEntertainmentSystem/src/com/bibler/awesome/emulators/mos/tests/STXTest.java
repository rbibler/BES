package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class STXTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
	}
	
	public void testSTXZP() {
		setup();
		cpu.PC = 0x20;
		cpu.X = 0x10;
		mem.write(0x20, 0x86);
		mem.write(0x21, 0x40);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x40));
		
	}
	
	public void testSTXZPY() {
		setup();
		cpu.PC = 0x20;
		cpu.X = 0x10;
		cpu.Y = 1;
		mem.write(0x20, 0x96);
		mem.write(0x21, 0x40);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x41));
	}
	
	public void testSTXAbsolue() {
		setup();
		cpu.PC = 0x20;
		cpu.X = 0x10;
		mem.write(0x20, 0x8E);
		mem.write(0x21, 0x4040);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x4040));
	}

}
