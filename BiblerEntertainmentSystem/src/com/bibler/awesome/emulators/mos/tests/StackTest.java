package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class StackTest extends TestCase {
	
	private MemoryManager mem;
	private CPU cpu;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.SP = 0xFF;
	}
	
	public void testPHA() {
		setup();
		mem.write(0x20, 0x48);
		opCode = (byte) cpu.fetch();
		cpu.accumulator = 0x04;
		cpu.execute(opCode);
		assertEquals(0x04, mem.read(0x01FF));
		assertEquals(0xFE, cpu.SP);
	}
	
	public void testPLA() {
		setup();
		mem.write(0x20, 0x48);
		mem.write(0x21, 0x68);
		cpu.accumulator = 0x04;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		cpu.accumulator = 0x07;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x04, cpu.accumulator);
		assertEquals(0xFF, cpu.SP);
	}
	
	public void testPHP() {
		setup();
		mem.write(0x20, 0x08);
		cpu.zero = 1;
		cpu.negative = 0;
		cpu.overflow = 1;
		cpu.decimal = 0;
		cpu.carry = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		byte p = (byte) mem.read(0x01FF);
		assertEquals(0, (p >> 7) & 0x01);
		assertEquals(1, (p >> 6) & 0x01);
		assertEquals(1, (p >> 1) & 0x01);
		assertEquals(1, (p & 0x01));
	}
	
	public void testPLP() {
		setup();
		mem.write(0x20, 0x08);
		mem.write(0x21,  0x28);
		cpu.zero = 1;
		cpu.negative = 0;
		cpu.overflow = 1;
		cpu.decimal = 0;
		cpu.carry = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		cpu.negative = 1;
		cpu.overflow = 0;
		cpu.carry = 0;
		cpu.zero = 0;
	    opCode = (byte) cpu.fetch();
	    cpu.execute(opCode);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		assertEquals(1, cpu.overflow);
		assertEquals(1, cpu.carry);
	}
	
	public void testTSX() {
		setup();
		mem.write(0x20, 0xBA);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0xFF, cpu.X);
	}
	
	public void testTXS() {
		setup();
		mem.write(0x20, 0x9A);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0x01, cpu.SP);
	}
}
