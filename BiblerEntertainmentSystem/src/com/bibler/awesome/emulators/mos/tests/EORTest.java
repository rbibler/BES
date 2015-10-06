package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class EORTest extends TestCase {

	
	MemoryManager mem;
	CPU cpu;
	byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 0;
	}
	
	public void testEORIndX() {
		setup();
		mem.write(0x20, 0x41);
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
	
	public void testEORZP() {
		setup();
		mem.write(0x20, 0x45);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testEORIMM() {
		setup();
		mem.write(0x20, 0x49);
		mem.write(0x21, 0xFF);
		cpu.accumulator = 0xF0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0x0F), cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testEORAbs() {
		setup();
		mem.write(0x20, 0x4D);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testEORIndY() {
		setup();
		mem.write(0x20, 0x51);
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
	
	public void testEORZPX() {
		setup();
		mem.write(0x20, 0x55);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x01);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testEORAbsY() {
		setup();
		mem.write(0x20, 0x59);
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
	
	public void testEORAbsX() {
		setup();
		mem.write(0x20, 0x5D);
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
