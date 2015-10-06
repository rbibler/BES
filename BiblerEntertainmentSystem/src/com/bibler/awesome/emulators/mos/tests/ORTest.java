package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class ORTest extends TestCase {
	
	MemoryManager mem;
	CPU cpu;
	byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 0;
	}
	
	public void testORAIndX() {
		setup();
		mem.write(0x20, 0x01);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x04);
		mem.write(0x04, 0x00);
		mem.write(0x05, 0x40);
		mem.write(0x4000, 0x01);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAZP() {
		setup();
		mem.write(0x20, 0x05);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAIMM() {
		setup();
		mem.write(0x20, 0x09);
		mem.write(0x21, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAAbs() {
		setup();
		mem.write(0x20, 0x0D);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAIndY() {
		setup();
		mem.write(0x20, 0x11);
		mem.write(0x00, 0x00);
		mem.write(0x01, 0x40);
		mem.write(0x4001, 0x01);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAZPX() {
		setup();
		mem.write(0x20, 0x15);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x01);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAAbsY() {
		setup();
		mem.write(0x20, 0x19);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x01);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testORAAbsX() {
		setup();
		mem.write(0x20, 0x1D);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x01);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
}
