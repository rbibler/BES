package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class LDYTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
	}
	
	public void testLoadYImmediate() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA0);
		mem.write(0x21, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadYWithNegative() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA0);
		mem.write(0x21, 0xFE);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0xFE & 0xFF), cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);	
	}
	
	public void testLoadYWithZero() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20, 0xA0);
		mem.write(0x21, 0x00);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x00, cpu.Y);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadYZP() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xA4);
		mem.write(0x21, 0x03);
		mem.write(0x03, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
		
	}
	
	public void testLoadYZPX() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xB4);
		mem.write(0x21, 0x03);
		mem.write(0x04, 0x03);
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testLoadYAbsolute() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xAC);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4400, 0x03);
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
		
	}
	
	public void testLoadXAbsoluteX() {
		setup();
		cpu.PC = 0x20;
		mem.write(0x20,  0xBC);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x44);
		mem.write(0x4401, 0x03);
		cpu.X = 1;
		byte opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x03, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}

}
