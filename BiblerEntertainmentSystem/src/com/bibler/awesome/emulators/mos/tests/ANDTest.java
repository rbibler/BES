package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class ANDTest extends TestCase {

	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 0;
	}
	
	public void testANDIndX() {
		setup();
		mem.write(0x20, 0x21);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x04);
		mem.write(0x04, 0x00);
		mem.write(0x05, 0x40);
		mem.write(0x4000, 0x02);
		cpu.X = 1;
		cpu.accumulator = 2;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testANDZP() {
		setup();
		mem.write(0x20, 0x25);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x01);
		cpu.accumulator = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAIMM() {
		setup();
		mem.write(0x20, 0x29);
		mem.write(0x21, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.accumulator = 1;
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAAbs() {
		setup();
		mem.write(0x20, 0x2D);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x01);
		cpu.accumulator = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testANDIndY() {
		setup();
		mem.write(0x20, 0x31);
		mem.write(0x00, 0x00);
		mem.write(0x01, 0x40);
		mem.write(0x4001, 0x01);
		cpu.Y = 1;
		cpu.accumulator = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAZPX() {
		setup();
		mem.write(0x20, 0x35);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x01);
		cpu.X = 1;
		cpu.accumulator = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAAbsY() {
		setup();
		mem.write(0x20, 0x39);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x01);
		cpu.accumulator = 1;
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAAbsX() {
		setup();
		mem.write(0x20, 0x3D);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x01);
		cpu.X = 1;
		cpu.accumulator = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
}
