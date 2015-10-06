package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class TestAdd extends TestCase {
	
	MemoryManager mem;
	CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.accumulator = 0x04;
	}
	
	public void testAddZP() {
		setup();
		mem.write(0x02,  0x04);
		mem.write(0x20, 0x65);
		mem.write(0x21, 0x02);
		cpu.PC = 0x20;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0x08 & 0xFF), cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testAddZPX() {
		setup();
		mem.write(0x03,  0x04);
		mem.write(0x20, 0x75);
		mem.write(0x21, 0x02);
		cpu.X = 1;
		cpu.PC = 0x20;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0x08 & 0xFF), cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testAddImmediate() {
		setup();
		mem.write(0x20, 0x69);
		mem.write(0x21, 0x44);
		cpu.PC = 0x20;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0x48 & 0xFF), cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testAddAbsolute() {
		setup();
		mem.write(0x20, 0x6D);
		mem.write(0x21, 0x02);
		mem.write(0x22, 0xDE);
		mem.write(0x02, 0x04);
		mem.write(0xDE02, 0x04);
		cpu.PC = 0x20;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x08, cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x23, cpu.PC);
	}
	
	public void testAddAbsoluteX() {
		setup();
		mem.write(0x20, 0x7D);
		mem.write(0x21, 0x02);
		mem.write(0x22, 0xDE);
		mem.write(0x02, 0x04);
		mem.write(0xDE03, 0x04);
		cpu.PC = 0x20;
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x08, cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x23, cpu.PC);
	}
	
	public void testAddAbsoluteY() {
		setup();
		mem.write(0x20, 0x79);
		mem.write(0x21, 0x02);
		mem.write(0x22, 0xDE);
		mem.write(0x02, 0x04);
		mem.write(0xDE03, 0x04);
		cpu.PC = 0x20;
		cpu.Y = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x08, cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x23, cpu.PC);
	}
	
	public void testAddIndirectX() {
		setup();
		mem.write(0x20, 0x61);
		mem.write(0x21, 0x44);
		mem.write(0x45, 0x04);
		mem.write(0x04, 0x05);
		mem.write(0x05, 0xDE);
		mem.write(0xDE05, 0x04);
		cpu.PC = 0x20;
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x08, cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x22, cpu.PC);
	}
	
	public void testAddIndirectY() {
		setup();
		mem.write(0x20, 0x71);
		mem.write(0x21, 0x04);
		mem.write(0x04, 0x04);
		mem.write(0x05, 0xDE);
		mem.write(0xDE05, 0x04);
		cpu.PC = 0x20;
		cpu.Y = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x08, cpu.accumulator);
		assertEquals(0, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x22, cpu.PC);
	}

}
