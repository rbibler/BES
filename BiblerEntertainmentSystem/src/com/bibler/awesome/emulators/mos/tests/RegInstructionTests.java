package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class RegInstructionTests extends TestCase {
	
	MemoryManager mem;
	CPU cpu;
	byte opCode;
	
	public void setupCPU() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
		cpu.X = 1;
		cpu.Y = 1;
		cpu.accumulator = 5;
	}
	
	public void testINX() {
		setupCPU();
		mem.write(0x20, 0xE8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testINY() {
		setupCPU();
		mem.write(0x20, 0xC8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(2, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testDEX() {
		setupCPU();
		mem.write(0x20, 0xCA);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.X);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		cpu.execute(opCode);
		assertEquals(0xFF, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);
	}
	
	public void testDEY() {
		setupCPU();
		mem.write(0x20, 0x88);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(0, cpu.Y);
		assertEquals(1, cpu.zero);
		assertEquals(0, cpu.negative);
		cpu.execute(opCode);
		assertEquals(0xFF, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(1, cpu.negative);
	}
	
	public void testTAX() {
		setupCPU();
		mem.write(0x20,  0xAA);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(5, cpu.X);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testTXA() {
		setupCPU();
		mem.write(0x20,  0x8A);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testTAY() {
		setupCPU();
		mem.write(0x20,  0xA8);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(5, cpu.Y);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testTYA() {
		setupCPU();
		mem.write(0x20,  0x98);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, cpu.accumulator);
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	

	
}
