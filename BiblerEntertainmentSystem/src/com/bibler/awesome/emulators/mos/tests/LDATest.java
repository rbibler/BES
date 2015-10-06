package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class LDATest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
	}
	
	public void testLoadA() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA9);
		mem.write(0x21, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadAWithNegative() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA9);
		mem.write(0x21, 0xFE);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0xFE, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);	
	}
	
	public void testLoadAWithZero() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA9);
		mem.write(0x21, 0x00);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x00, cpu.accumulator);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadAZP() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xA5);
		mem.write(0x21, 0x03);
		mem.write(0x03, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
		
	}
	
	public void testLoadAZPX() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xB5);
		mem.write(0x21, 0x03);
		mem.write(0x04, 0x03);
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadAAbsolute() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xAD);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4400, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
		
	}

	public void testLoadAAbsoluteX() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xBD);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4401, 0x03);
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadAAbsoluteY() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xB9);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4401, 0x03);
		cpu.Y = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadAIndirectX() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xA1);
		mem.write(0x20, 0x61);
		mem.write(0x21, 0x44);
		mem.write(0x45, 0x04);
		mem.write(0x04, 0x05);
		mem.write(0x05, 0xDE);
		mem.write(0xDE05, 0x03);
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLDAIndirectY() {
		setup();
		mem.write(0x20, 0xB1);
		mem.write(0x21, 0x04);
		mem.write(0x04, 0x04);
		mem.write(0x05, 0xDE);
		mem.write(0xDE05, 0x03);
		cpu.PC = 0x20;
		cpu.Y = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}


}
