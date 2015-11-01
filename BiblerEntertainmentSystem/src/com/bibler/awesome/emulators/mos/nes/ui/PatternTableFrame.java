package com.bibler.awesome.emulators.mos.nes.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bibler.awesome.emulators.mos.systems.PPU;

public class PatternTableFrame extends JFrame implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1376132564238561076L;
	private PatternTablePanel leftPanel;
	private PatternTablePanel rightPanel;
	private PalettePanel palettePanel;
	private JPanel mainPanel;
	private boolean running;
	private Object pauseLock = new Object();
	private boolean pause;
	
	public PatternTableFrame() {
		super();
		initializePanels();
		arrange();
		startThread();
	}
	
	private void initializePanels() {
		leftPanel = new PatternTablePanel(0);
		rightPanel = new PatternTablePanel(0x1000);
		palettePanel = new PalettePanel();
	}
	
	private void startThread() {
		Thread thread = new Thread(this);
		running = true;
		pause();
		thread.start();
	}
	
	public void setPPU(PPU ppu) {
		leftPanel.setPPU(ppu);
		rightPanel.setPPU(ppu);
		palettePanel.setPPU(ppu);
	}
	
	private void arrange() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.weighty = 0.75;
		mainPanel.add(leftPanel, gbc);
		gbc.gridx = 1;
		mainPanel.add(rightPanel, gbc);
		add(mainPanel);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0.25;
		mainPanel.add(palettePanel, gbc);
		pack();
	}
	
	private void pause() {
		pause = true;
	}
	
	private void resume() {
		synchronized(pauseLock) {
			pauseLock.notifyAll();
		}
		pause = false;
	}
	
	private void update() {
		palettePanel.update();
		int[] palette = palettePanel.getCurrentPalette();
		leftPanel.update(palette);
		rightPanel.update(palette);
		
	}
	
	public void showThisFrame() {
		setVisible(true);
		resume();
	}
	
	@Override
	public void run() {
		while(running) {
			if(pause) {
				synchronized(pauseLock) {
					try {
						pauseLock.wait();
					} catch(InterruptedException e) {}
				}
			}
			update();
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {}
		}
	}
}
