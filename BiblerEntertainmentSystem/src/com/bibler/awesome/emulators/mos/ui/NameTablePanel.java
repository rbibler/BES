package com.bibler.awesome.emulators.mos.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.bibler.awesome.emulators.mos.systems.PPU;
import com.bibler.awesome.emulators.mos.systems.PPUMemoryManager;

public class NameTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2221336356206890787L;
	BufferedImage image;
	PPUMemoryManager manager;
	PPU ppu;
	private int ntStart;
	
	public NameTablePanel(int ntStart) {
		super();
		this.ntStart = ntStart;
		setPreferredSize(new Dimension(256, 240));
		image = new BufferedImage(256, 240, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	public void setPPU(PPU ppu) {
		this.ppu = ppu;
		this.manager = ppu.manager;
	}
	
	public void update() {
		renderFrame();
		repaint();
	}
	
	private void renderFrame() {
		int attrX;
		int attrY;
		int x;
		int y;
		int tLB;
		int curNT;
		int curAttr;
		int length = 256 * 240;
		int bgPT = ppu.getBGPt();
		for(int i = 0; i < length; i++) {
			x = i % 256;
			y = (i / 256);
			curNT = manager.read(ntStart + (((y / 8) * 32) + (x / 8))) & 0xFF;
			curAttr = manager.read(ntStart + 0x3C0 + (((y / 32) * 8) + (x / 32))) & 0xFF;
			tLB = (curNT * 0x10) + (y % 8);
			int tileLowByte = manager.read(bgPT + tLB);
			int tileHighByte = manager.read(bgPT + tLB + 8);
			int pixel = grabBit(tileHighByte, (7 - (x % 8))) << 1 | grabBit(tileLowByte, 7 - (x % 8));
			int attrStart = (((y / 32) * 32) * 256) + (((x / 32) * 32));
			attrX = (x / 32) * 4;
			attrY = (y / 32) * 4;
			int ntX = x / 8;
			int ntY = y / 8;
			attrStart = i - attrStart;
			int attrBitShift = (((ntX - attrX) / 2) * 2) + (((ntY - attrY) / 2) * 4);
			int palVal = ((curAttr >> attrBitShift) & 3) << 2;
			image.setRGB(x, y, NESPalette.grabValue(manager.read(0x3F00 +(palVal + pixel))));
		}
	}
	
	private int grabBit(int byteFromWhichToGrab, int bitToGrab) {
		return (byteFromWhichToGrab >> bitToGrab) & 1;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}
