package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class CMPTest extends TestCase {

	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.accumulator = 3;
	}
	
	public void testCMPIndX() {
		setup();
		mem.write(0x20, 0xC1);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x04);
		mem.write(0x04, 0x00);
		mem.write(0x05, 0x40);
		mem.write(0x4000, 0x02);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPZP() {
		setup();
		mem.write(0x20, 0xC5);
		mem.write(0x21, 0x00);
		mem.write(0x00, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPIMM() {
		setup();
		mem.write(0x20, 0xC9);
		mem.write(0x21, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPAbs() {
		setup();
		mem.write(0x20, 0xCD);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPIndY() {
		setup();
		mem.write(0x20, 0xD1);
		mem.write(0x00, 0x00);
		mem.write(0x01, 0x40);
		mem.write(0x4001, 0x02);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPZPX() {
		setup();
		mem.write(0x20, 0xD5);
		mem.write(0x21, 0x00);
		mem.write(0x01, 0x02);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPAbsY() {
		setup();
		mem.write(0x20, 0xD9);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x02);
		cpu.Y = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
	
	public void testCMPAbsX() {
		setup();
		mem.write(0x20, 0xDD);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x02);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.carry);
		assertEquals(0, cpu.negative);
	}
}
