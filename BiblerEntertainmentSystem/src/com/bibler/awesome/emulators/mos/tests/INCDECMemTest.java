package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class INCDECMemTest extends TestCase {
	
	MemoryManager mem;
	CPU cpu;
	byte opCode;
	
	public void setupCPU() {
		cpu = new CPU(null);
		mem = cpu.mem;
		cpu.PC = 0x20;
	}
	
	public void testDECZP() {
		setupCPU();
		mem.write(0x20, 0xC6);
		mem.write(0x21, 0x40);
		mem.write(0x40, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, mem.read(0x40));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testDECZPX() {
		setupCPU();
		cpu.X = 1;
		mem.write(0x20, 0xD6);
		mem.write(0x21, 0x40);
		mem.write(0x41, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, mem.read(0x41));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testDECAbsolute() {
		setupCPU();
		mem.write(0x20, 0xCE);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, mem.read(0x4000));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		
	}
	
	public void testDECAbsoluteX() {
		setupCPU();
		mem.write(0x20, 0xDE);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x02);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(1, mem.read(0x4001));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}
	
	public void testINCZP() {
		setupCPU();
		mem.write(0x20, 0xE6);
		mem.write(0x21, 0x40);
		mem.write(0x40, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(3, mem.read(0x40));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testINCZPX() {
		setupCPU();
		cpu.X = 1;
		mem.write(0x20, 0xF6);
		mem.write(0x21, 0x40);
		mem.write(0x41, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(3, mem.read(0x41));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
	}
	
	public void testINCAbsolute() {
		setupCPU();
		mem.write(0x20, 0xEE);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4000, 0x02);
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(3, mem.read(0x4000));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);
		
	}
	
	public void testINCAbsoluteX() {
		setupCPU();
		mem.write(0x20, 0xFE);
		mem.write(0x21, 0x00);
		mem.write(0x22, 0x40);
		mem.write(0x4001, 0x02);
		cpu.X = 1;
		opCode = (byte) cpu.fetch();
		cpu.execute(opCode);
		assertEquals(3, mem.read(0x4001));
		assertEquals(0, cpu.zero);
		assertEquals(0, cpu.negative);	
	}

}
