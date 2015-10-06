package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class ASLTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
	}
	
	public void testASLZP() {
		setup();
		mem.write(0x20, 0x06);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x80);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x00));
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testASLA() {
		setup();
		cpu.accumulator = 0x01;
		mem.write(0x20, 0x0A);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}
	
	public void testORAAbs() {
		setup();
		mem.write(0x20, 0x0E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, mem.read(0x4000));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}
	
	public void testASLZPX() {
		setup();
		mem.write(0x20, 0x16);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x01);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, mem.read(0x01));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}
	
	public void testASLAbsX() {
		setup();
		mem.write(0x20, 0x1E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x01);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, mem.read(0x4001));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}

}
