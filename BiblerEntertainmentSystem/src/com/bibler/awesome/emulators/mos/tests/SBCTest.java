package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class SBCTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.carry = 1;
		cpu.accumulator = 4;
	}
	
	public void testSBCZP() {
		setup();
		mem.write(0x02,  0x01);
		mem.write(0x20, 0xE5);
		mem.write(0x21, 0x02);
		cpu.accumulator = 0x80;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals((byte) (0x7F), cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.overflow);
	}
	
	public void testSBCZPX() {
		setup();
		mem.write(0x03,  0x03);
		mem.write(0x20, 0xF5);
		mem.write(0x21, 0x02);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testSBCImmediate() {
		setup();
		mem.write(0x20, 0xE9);
		mem.write(0x21, 0x03);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testSBCAbsolute() {
		setup();
		mem.write(0x20, 0xED);
		mem.write(0x21, 0x02);
		mem.write(0x22, 0xDE);
		mem.write(0x02, 0x04);
		mem.write(0xDE02, 0x03);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x01, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x23, cpu.PC);
	}
	
	public void testSBCAbsoluteX() {
		setup();
		mem.write(0x20, 0xFD);
		mem.write(0x21, 0x02);
		mem.write(0x22, 0xDE);
		mem.write(0x02, 0x04);
		mem.write(0xDE03, 0x03);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x01, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x23, cpu.PC);
	}
	
	public void testSBCAbsoluteY() {
		setup();
		mem.write(0x20, 0xF9);
		mem.write(0x21, 0x02);
		mem.write(0x22, 0xDE);
		mem.write(0x02, 0x04);
		mem.write(0xDE03, 0x03);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x01, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x23, cpu.PC);
	}
	
	public void testSBCIndirectX() {
		setup();
		mem.write(0x20, 0xE1);
		mem.write(0x21, 0x44);
		mem.write(0x45, 0x04);
		mem.write(0x04, 0x05);
		mem.write(0x05, 0xDE);
		mem.write(0xDE05, 0x03);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x01, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x22, cpu.PC);
	}
	
	public void testSBCIndirectY() {
		setup();
		mem.write(0x20, 0xF1);
		mem.write(0x21, 0x04);
		mem.write(0x04, 0x04);
		mem.write(0x05, 0xDE);
		mem.write(0xDE05, 0x03);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x01, cpu.accumulator);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0x22, cpu.PC);
	}

}
