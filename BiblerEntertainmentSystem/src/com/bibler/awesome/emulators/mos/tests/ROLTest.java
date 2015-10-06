package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class ROLTest extends TestCase {

	MemoryManager mem;
	CPU cpu;
	byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 0;
	}
	
	public void testROLZP() {
		setup();
		mem.write(0x20, 0x26);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x80);
		opCode = (byte) cpu.fetch();
		cpu.carry = 0;
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x00));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testROLAbs() {
		setup();
		mem.write(0x20, 0x2E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x80);
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x4000));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testROLZPX() {
		setup();
		mem.write(0x20, 0x36);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x80);
		cpu.X = 1;
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x01));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testROLAbsX() {
		setup();
		mem.write(0x20, 0x3E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x80);
		cpu.X = 1;
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x4001));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testROLA() {
		setup();
		mem.write(0x20, 0x2A);
		cpu.accumulator = 0x80;
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
}
