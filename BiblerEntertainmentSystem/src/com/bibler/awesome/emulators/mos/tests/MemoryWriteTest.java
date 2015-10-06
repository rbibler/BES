package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.Memory;

import junit.framework.TestCase;

public class MemoryWriteTest extends TestCase {
	
	public void testMemoryWrite() {
		Memory mem = new Memory();
		int address = 0x4500;
		mem.write(address,  0x03);
		assertEquals(0x03, mem.memory[address]);
	}

}
