package com.bibler.awesome.emulators.mos.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.bibler.awesome.emulators.mos.systems.PPU;
import com.bibler.awesome.emulators.mos.systems.PPUMemoryManager;
import com.bibler.awesome.emulators.mos.utils.StringUtils;

public class PalettePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2102176297703121132L;
	
	private int[] palette = new int[32];
	private PPUMemoryManager manager;
	private int currentPaletteIndex;

	public PalettePanel() {
		super();
		setPreferredSize(new Dimension(256, 64));
		setupMouseTouch();
	}
	
	public int[] getCurrentPalette() {
		int[] ret = new int[4];
		for(int i = 0; i < 4; i++) {
			ret[i] = palette[i + currentPaletteIndex];
		}
		return ret;
	}
	
	private void setupMouseTouch() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int x = arg0.getX();
				int y = arg0.getY();
				int size = getWidth() / 16;
				x /= (size * 4);
				y /= size;
				int offset = (y * 16) + (x * 4);
				currentPaletteIndex = offset;
				System.out.println("Offset: " + offset);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		}); 
	}
	
	public void setPPU(PPU ppu) {
		manager = ppu.manager;
	}
	
	private void updatePalette(PPUMemoryManager manager) {
		if(manager == null)
			return;
		for(int i = 0; i < palette.length; i++) {
			palette[i] = manager.read(0x3f00 + i);
		}
	}
	
	public void update() {
		updatePalette(manager);
		repaint();
	}
	
	private Color getColor(int index) {
		int color = NESPalette.grabValue(index);
		return new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = getWidth() / 16;
		Color c;
		for(int i = 0 ; i < palette.length; i++) {
			c = getColor(palette[i]);
			g.setColor(c);
			g.fillRect((i % 16) * size, (i / 16) * size, size, size);
			g.setColor(Color.WHITE);
			g.drawRect((i % 16) * size, (i / 16) * size, size, size);
			g.setColor(new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()));
			String num = StringUtils.formatNumber(palette[i], 2);
			g.drawString(num, ((i % 16) * size) + 2, (int) (((i / 16) * size) + (size)));
		}	
		g.setColor(Color.RED);
		g.drawRect((currentPaletteIndex % 16) * size, (currentPaletteIndex / 16) * size, size * 4, size);
	}
}
