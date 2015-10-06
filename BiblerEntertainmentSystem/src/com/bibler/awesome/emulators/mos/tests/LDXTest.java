package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class LDXTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
	}
	
	public void testLoadXImmediate() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA2);
		mem.write(0x21, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadXWithNegative() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA2);
		mem.write(0x21, 0xFE);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0xFE & 0xFF), cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);	
	}
	
	public void testLoadXWithZero() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA2);
		mem.write(0x21, 0x00);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x00, cpu.X);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadXZP() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xA6);
		mem.write(0x21, 0x03);
		mem.write(0x03, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
		
	}
	
	public void testLoadXZPY() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xB6);
		mem.write(0x21, 0x03);
		mem.write(0x04, 0x03);
		cpu.Y = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadXAbsolute() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xAE);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4400, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
		
	}
	
	public void testLoadXAbsoluteY() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xBE);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4401, 0x03);
		cpu.Y = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
}
