package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class ClearAndSetTests extends TestCase {
	
	private CPU cpu;
	private MemoryManager mem;
	private byte opCode;
	
	private void setup() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
	}
	
	public void testCLC() {
		setup();
		cpu.carry = 1;
		mem.write(0x20, 0x18);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.carry);
	}
	
	public void testSEC() {
		setup();
		cpu.carry = 0;
		mem.write(0x20,  0x38);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.carry);
	}
	
	public void testCLI() {
		setup();
		cpu.interrupt = 1;
		mem.write(0x20,  0x58);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.interrupt);
	}
	
	public void testSEI() {
		setup();
		cpu.interrupt = 0;
		mem.write(0x20,  0x78);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.interrupt);
	}
	
	public void testCLV() {
		setup();
		cpu.overflow = 1;
		mem.write(0x20,  0xB8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.overflow);
	}
	
	public void testCLD() {
		setup();
		cpu.decimal = 1;
		mem.write(0x20,  0xD8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.decimal);
	}
	
	public void testSED() {
		setup();
		cpu.decimal = 0;
		mem.write(0x20,  0xF8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.decimal);
	}
	
	

}
