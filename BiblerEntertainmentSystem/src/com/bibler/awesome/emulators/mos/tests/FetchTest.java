package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.CPU;
import com.bibler.awesome.emulators.mos.systems.Memory;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class FetchTest extends TestCase {
	
	public void testFecth() {
		CPU cpu = new CPU(null);
		MemoryManager mem = cpu.mem;
		mem.write(0x07, 0x65);
		cpu.PC = 0x07;
		byte fetchand = (byte) cpu.fetch();
		assertEquals(0x65, fetchand);
		assertEquals(0x08, cpu.PC);
	}
	
}
