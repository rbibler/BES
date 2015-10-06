package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class RORTest extends TestCase {
	
	MemoryManager mem;
	CPU cpu;
	byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 0;
	}
	
	public void testRORZP() {
		setup();
		mem.write(0x20, 0x66);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x01);
		opCode = (byte) cpu.fetch();
		cpu.carry = 1;
		cpu.execute(opCode);
		assertEquals((byte) (0x80 & 0xFF), mem.read(0x00));
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testRORAbs() {
		setup();
		mem.write(0x20, 0x6E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x01);
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x4000));
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testRORZPX() {
		setup();
		mem.write(0x20, 0x76);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x01);
		cpu.X = 1;
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x01));
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testRORAbsX() {
		setup();
		mem.write(0x20, 0x7E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x01);
		cpu.X = 1;
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x4001));
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testRORA() {
		setup();
		mem.write(0x20, 0x6A);
		cpu.accumulator = 0x01;
		cpu.carry = 0;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.accumulator);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}

}
