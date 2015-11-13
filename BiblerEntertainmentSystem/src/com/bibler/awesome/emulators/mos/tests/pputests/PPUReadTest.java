package com.bibler.awesome.emulators.mos.tests.pputests;

import com.bibler.awesome.emulators.mos.systems.PPU;

import junit.framework.TestCase;

public class PPUReadTest extends TestCase {
	
	
	/*
	 * $2002 PPUSTATUS	X
	 * $2004 OAMDATA	X
	 * $2007 PPUDATA	X
	 */
	
	public void testPPUReadPPUSTATUS() {
		PPU ppu = new PPU();
		ppu.write(1, 0xFE);
		ppu.updatePPUStatus(true,  PPU.SPRITE_0_HIT);
		ppu.updatePPUStatus(true,  PPU.SPRITE_OVERFLOW);
		ppu.updatePPUStatus(false, PPU.V_BLANK);
		final int status = ppu.read(2);
		assertEquals(0, status >> PPU.V_BLANK & 1);
		assertEquals(1, status >> PPU.SPRITE_0_HIT & 1);
		assertEquals(1, status >> PPU.SPRITE_OVERFLOW & 1);
		assertEquals(0xFE & 0b11111, status & 0b11111);
	}
	
	public void testPPUReadOAMData() {
		PPU ppu = new PPU();
		ppu.write(3, 0x44);
		ppu.write(4, 0x44);
		final int OAM = ppu.read(3);
		//assertEquals(0x44, OAM);
	}
	
	public void testPPUReadPPUDATA() {
		PPU ppu = new PPU();
		ppu.read(2);
		ppu.write(6, 0x20);
		ppu.write(6, 0x00);
		ppu.write(7, 0x44);
		ppu.read(7);
		ppu.read(2);
		ppu.write(6, 0x20);
		ppu.write(6, 0x00);
		assertEquals(0x44, ppu.read(7));
	}

}
