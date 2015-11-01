package com.bibler.awesome.emulators.mos.nes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.bibler.awesome.emulators.mos.systems.PPU;
import com.bibler.awesome.emulators.mos.systems.PPUMemoryManager;

public class PatternTablePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7179428603709489712L;
	private BufferedImage image;
	private PPU ppu;
	private PPUMemoryManager manager;
	private int ptAddress;
	private int[] palette;
	private Color borderColor;
	
	public PatternTablePanel(int ptAddress) {
		super();
		this.ptAddress = ptAddress;
		image = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
		setPreferredSize(new Dimension(256, 256));
	}
	
	public void setPPU(PPU ppu) {
		this.ppu = ppu;
		manager = ppu.manager;
	}
	
	protected void update(int[] palette) {
		this.palette = palette;
		borderColor = NESPalette.getColor(palette[2]);
		if(ppu == null)
			return;
		updatePatternTable();
		repaint();
	}
	
	 private void updatePatternTable() {
		int patternX;
		int patternY;
		int patternLow;
		int patternHigh;
		int pixel;
		int startOffset;
		for(int i = 0; i < 0xFF; i++) {
			patternX = i % 16;
			patternY = i / 16;
			for(int y = 0; y < 8; y++) {
				startOffset = i * 16;
				patternLow = manager.read(ptAddress + startOffset + y);
				patternHigh = manager.read(ptAddress + (startOffset + y) + 8);
				for(int x = 0; x < 8; x++) {
					pixel = ((patternHigh >> (7 - x) & 1) << 1) | (patternLow >> (7 - x) & 1);
					image.setRGB(patternX * 8 + x, patternY * 8 + y, NESPalette.grabValue(palette[pixel]));
				}
			}
		}
	}
	 
	 @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		 g.setColor(borderColor);
		 g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	 }

}
