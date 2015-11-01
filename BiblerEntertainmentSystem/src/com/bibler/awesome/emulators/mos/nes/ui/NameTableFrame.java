package com.bibler.awesome.emulators.mos.nes.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bibler.awesome.emulators.mos.systems.PPU;

public class NameTableFrame extends JFrame implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024058156677216747L;
	private NameTablePanel nt0;
	private NameTablePanel nt1;
	private NameTablePanel nt2;
	private NameTablePanel nt3;
	private JPanel mainPanel;
	private boolean running;
	private boolean paused;
	private Object pauseLock = new Object();
	
	public NameTableFrame() {
		super();
		initializePanels();
		arrange();
		setupWindowListener();
		setupThread();
	}
	
	private void initializePanels() {
		nt0 = new NameTablePanel(0x2000);
		nt1 = new NameTablePanel(0x2400);
		nt2 = new NameTablePanel(0x2800);
		nt3 = new NameTablePanel(0x2C00);
	}
	
	private void arrange() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = .25;
		gbc.weighty = .25;
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(nt0, gbc);
		gbc.gridx = 1;
		mainPanel.add(nt1, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(nt2, gbc);
		gbc.gridx = 1;
		mainPanel.add(nt3, gbc);
		add(mainPanel);
		pack();
	}
	
	private void setupWindowListener() {
		this.addWindowListener( new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				pause();
			}

			@Override
			public void windowClosing(WindowEvent arg0) {}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				resume();
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				pause();
			}

			@Override
			public void windowOpened(WindowEvent arg0) {}
			
		});
	}
	
	private void setupThread() {
		Thread thread = new Thread(this);
		running = true;
		pause();
		thread.start();
	}
	
	public void setPPU(PPU ppu) {
		nt0.setPPU(ppu);
		nt1.setPPU(ppu);
		nt2.setPPU(ppu);
		nt3.setPPU(ppu);
	}
	
	public void showThisFrame() {
		setVisible(true);
		resume();
	}
	
	private void pause() {
		paused = true;
	}
	
	private void resume() {
		synchronized(pauseLock) {
			pauseLock.notifyAll();
		}
		paused = false;
	}
	
	private void update() {
		nt0.update();
		nt1.update();
		nt2.update();
		nt3.update();
	}
	
	@Override
	public void run() {
		while(running) {
			if(paused) {
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
