package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.Memory;

import junit.framework.TestCase;

public class MemoryReadTest extends TestCase {
	
	public void testMemoryRead() {
		Memory mem = new Memory();
		int address = 0x7654;
		byte data = 0x3C;
		mem.memory[address] = data;
		assertEquals(data, mem.read(address));
	}
	
	

}
