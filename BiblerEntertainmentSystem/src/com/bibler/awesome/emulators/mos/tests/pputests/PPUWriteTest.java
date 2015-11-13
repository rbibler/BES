package com.bibler.awesome.emulators.mos.tests.pputests;

import com.bibler.awesome.emulators.mos.systems.PPU;

import junit.framework.TestCase;

public class PPUWriteTest extends TestCase {
	
	
	/*
	 * $2000 PPUCTRL	X
	 * $2001 PPUMAS		X
	 * $2003 OAMADDR	x
	 * $2004 OAMDATA	X
	 * $2005 PPUSCROLL	X
	 * $2006 PPUADDR	X
	 * $2007 PPUDATA	X
	 */
	
	private PPU ppu;
	
	public void testPPUWritePPUCTRL() {
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
	
	public void testPPUWritePPUMASK() {
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
	
	public void testPPUWriteOAMADDR() {
		ppu = new PPU();
		ppu.write(3, 0x44);
		assertEquals(0x44, ppu.getOAMAddr());
	}
	
	public void testPPUWriteOAMDATA() {
		ppu = new PPU();
		ppu.write(4, 0x44);
		assertEquals(0x44, ppu.getManager().read((ppu.getOAMAddr() - 1) + 0x4000));
		assertEquals(1, ppu.getOAMAddr());
	}
	
	public void testPPUWritePPUSRCOLL() {
		ppu = new PPU();
		ppu.write(5, 0b01111101);
		assertEquals(0b1111, ppu.getT());
		assertEquals(0b101, ppu.getX());
		ppu.write(5, 0b01011110);
		assertEquals(0b110000101101111, ppu.getT());
	}
	
	public void testPPUWritePPUADDR() {
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
	
	public void testPPUWritePPUDATA() {
		ppu = new PPU();
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
