package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.MemoryManager;

import junit.framework.TestCase;

public class MemoryManagerTest extends TestCase {
	
	private MemoryManager memManager;
	
	public void setUp() {
		memManager = new MemoryManager();
	}
	
	public void testMemoryManagerPPUCtrl() {
		memManager.write(0x2009, 0x04);
		assertEquals(0x04, memManager.read(0x2009));
	}
	
	public void testMemoryManagerWorkingRam() {
		memManager.write(0x967, 0x04);
		assertEquals(0x04, memManager.read(0x967));
	}
	
	public void testApuRegisters() {
		memManager.write(0x4001, 0x04);
		assertEquals(0x04, memManager.read(0x4001));
	}
	
	public void testCartExpansionRom() {
		memManager.write(0x5000,  0x04);
		assertEquals(0x04, memManager.read(0x5000));
	}
	
	public void testSRAM() {
		memManager.write(0x6200,  0x04);
		assertEquals(0x04, memManager.read(0x6200));
	}
	
	public void testPRGRom() {
		memManager.write(0xCDEF, 0x04);
		assertEquals(0x04, memManager.read(0xCDEF));
	}
	
	public void testPPUCtrlToggle() {
		memManager.write(0x2005, 0x04);
		assertEquals(0x01, memManager.getToggle());
		memManager.read(0x2002);
		assertEquals(0, memManager.getToggle());
		memManager.write(0x2006,  0x04);
		assertEquals(0x01, memManager.getToggle());
	}

}
