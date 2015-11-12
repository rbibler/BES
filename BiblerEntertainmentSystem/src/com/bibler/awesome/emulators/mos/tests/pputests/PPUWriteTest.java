package com.bibler.awesome.emulators.mos.tests.pputests;

import com.bibler.awesome.emulators.mos.systems.PPU;

import junit.framework.TestCase;

public class PPUWriteTest extends TestCase {
	
	private PPU ppu;
	
	public void testPPUWrite2000() {
		ppu = new PPU();
		ppu.write(0, 0b11010000);
		assertEquals(0x00, (ppu.getT() & 0xFFFF));
		assertEquals(true, ppu.getNMI());
		assertEquals(1, ppu.getPPUMasterSlave());
		assertEquals(8, ppu.getSpriteSize());
		assertEquals(0x1000, ppu.getBGPatternTable());
		assertEquals(0x0000, ppu.getSpritePatternTable());
		assertEquals(1, ppu.getVramInc());
	}
	
	public void testPPUWrite2001() {
		ppu = new PPU();
		ppu.write(1,  0b00011110);
		assertEquals(false, ppu.emphasizeBlue());
		assertEquals(false, ppu.emphasizeGreen());
		assertEquals(false, ppu.emphasizeRed());
		assertEquals(true, ppu.showSprites());
		assertEquals(true, ppu.showBG());
		assertEquals(false, ppu.spriteCrop());
		assertEquals(false, ppu.bgCrop());
		assertEquals(false, ppu.grayscale());
	}
	
	public void testPPUWrite2005() {
		ppu = new PPU();
		ppu.write(5, 0b01111101);
		assertEquals(0b1111, ppu.getT());
		assertEquals(0b101, ppu.getX());
		ppu.write(5, 0b01011110);
		//System.out.println(Integer.toBinaryString(ppu.getT()));
		assertEquals(0b110000101101111, ppu.getT());
	}
	
	public void testPPUWrite2006() {
		ppu = new PPU();
		ppu.write(5, 0b01111101);
		assertEquals(0b1111, ppu.getT());
		ppu.write(5, 0b01011110);
		assertEquals(0b110000101101111, ppu.getT());
		ppu.write(6, 0b0111101);
		assertEquals(0b011110101101111, ppu.getT());
		ppu.write(6,  0b11110000);
		System.out.println("11110111110000");
		System.out.println(Integer.toBinaryString(ppu.getT()));
		assertEquals(0b011110111110000, ppu.getT());
		assertEquals(0b011110111110000, ppu.getV());
	}
	

}
