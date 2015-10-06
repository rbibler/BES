package com.bibler.awesome.emulators.mos.tests;

import com.bibler.awesome.emulators.mos.systems.PPU;

import junit.framework.TestCase;

public class PPUWriteTest extends TestCase {
	
	public void testScroll() {
		PPU ppu = new PPU();
		int data = 0b11000111;
		ppu.write(5, data);				//HGFED = 11000
		assertEquals(0b111, ppu.getXScroll());
		assertEquals(0b11000, ppu.getTempVAddress());
		data = 0b00111000; 				// CBA = 000; HG = 00; FED = 111;
		ppu.write(5,  data);
		assertEquals(0b111, ppu.getXScroll());
		assertEquals(0b000000011111000, ppu.getTempVAddress()); //CBA..HGFEDHGFED
		data = 0x20;
		ppu.write(6, data);
		assertEquals(0x20 << 8, ppu.getTempVAddress());
		data = 0x03;
		ppu.write(6,  data);
		assertEquals(0x2003, ppu.getVAddress());
		
		int tva = 0b000010000011111;
		int va = 0xFFFF;
		va &= ~0x41f;
		va |= (tva & 0x41F);
		va &= ~0xFBE0;
		va |= (tva & 0x7BE0);
		assertEquals(tva, va);
	}

}
