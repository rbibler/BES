package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class LSRTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
	}
	
	public void testLSRZP() {
		setup();
		mem.write(0x20, 0x46);
		mem.write(0x21, 0x00);
		mem.write(0x00, 1);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, mem.read(0x00));
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.carry);
	}
	
	public void testLSRA() {
		setup();
		cpu.accumulator = 16;
		mem.write(0x20, 0x4A);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(8, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}
	
	public void testLSRAbs() {
		setup();
		mem.write(0x20, 0x4E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 16);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(8, mem.read(0x4000));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}
	
	public void testLSRZPX() {
		setup();
		mem.write(0x20, 0x56);
		mem.write(0x21, 0x00);
		mem.write(0x01, 16);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(8, mem.read(0x01));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}
	
	public void testLSRAbsX() {
		setup();
		mem.write(0x20, 0x5E);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 16);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(8, mem.read(0x4001));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(0, cpu.carry);
	}

}
