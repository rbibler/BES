package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class STATest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
	}
	
	public void testSTAZP() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		mem.write(0x20, 0x85);
		mem.write(0x21, 0x40);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x40));
	}
	
	public void testSTAZPX() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		cpu.X = 1;
		mem.write(0x20, 0x95);
		mem.write(0x21, 0x40);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x41));
	}
	
	public void testSTAAbsolute() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		mem.write(0x20, 0x8D);
		mem.write(0x21, 0x4040);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x4040));
	}
	
	public void testSTAAbsoluteX() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		cpu.X = 1;
		mem.write(0x20, 0x9D);
		mem.write(0x21, 0x4040);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x4041));
	}
	
	public void testSTAAbsoluteY() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		cpu.Y = 1;
		mem.write(0x20, 0x99);
		mem.write(0x21, 0x4040);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0x4041));
	}
	
	public void testSTAIndirectX() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		cpu.X = 1;
		mem.write(0x20, 0x81);
		mem.write(0x21, 0x40);
		mem.write(0x41, 0x00);
		mem.write(0x42, 0xDE);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0xDE00));
	}
	
	public void testSTAIndirectY() {
		setup();
		cpu.PC = 0x20;
		cpu.accumulator = 0x10;
		cpu.Y = 1;
		mem.write(0x20, 0x91);
		mem.write(0x21, 0x40);
		mem.write(0x40, 0x00);
		mem.write(0x41, 0xDE);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x10, mem.read(0xDE01));
	}


}
