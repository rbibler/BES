package com.bibler.awesome.emulators.mos.ui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.bibler.awesome.emulators.mos.systems.MemoryManager;
import com.bibler.awesome.emulators.mos.systems.PPUMemoryManager;
import com.bibler.awesome.ui.hextable.HexTablePanel;

public class MemoryFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -170570335517644935L;
	private HexTablePanel ppuMem;
	private HexTablePanel cpuMem;
	private SpringLayout layout;
	private JPanel mainPanel;
	private boolean running;
	private MemoryManager cpuManager;
	private PPUMemoryManager ppuManager;
	
	public MemoryFrame() {
		super();
		initializeFrame();
		initializeMainPanel();
		initializeMemPanels();
		arrange();
		add(mainPanel);
		pack();
	}
	
	public void setPPUManager(PPUMemoryManager ppuManager) {
		this.ppuManager = ppuManager;
		ppuMem.getTable().setData(ppuManager.consolidateMemory());
		ppuManager.frame = this;
	}
	
	public void setCPUManager(MemoryManager cpuManager) {
		this.cpuManager = cpuManager;
		cpuMem.getTable().setData(cpuManager.consolidateMemory());
		cpuManager.setMemoryFrame(this);
	}
	
	private void initializeFrame() {
		addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent arg0) {}

			@Override
			public void windowClosing(WindowEvent arg0) {
				running = false;
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {}

			@Override
			public void windowOpened(WindowEvent arg0) {}
			
		});
	}
	
	private void initializeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(450, 450));
		layout = new SpringLayout();
		mainPanel.setLayout(layout);
	}
	
	private void initializeMemPanels() {
		ppuMem = new HexTablePanel();
		cpuMem = new HexTablePanel();
		ppuMem.setVisible(false);
	}
	
	private void arrange() {
		layout.putConstraint(SpringLayout.WEST, ppuMem, 0, SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, ppuMem, 0, SpringLayout.NORTH, mainPanel);
		layout.putConstraint(SpringLayout.WEST, cpuMem, 0, SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, cpuMem, 0, SpringLayout.NORTH, mainPanel);
		mainPanel.add(cpuMem);
		mainPanel.add(ppuMem);
	}
	
	private void updatePPUMem() {
		//ppuMem.getTable().clearAll();
		ppuMem.updateData(ppuManager.consolidateMemory());
	}
	
	private void updateCPUMem() {
		//cpuMem.getTable().clearAll();
		cpuMem.updateData(cpuManager.consolidateMemory());
	}
	
	public void showTheFrame() {
		updatePPUMem();
		updateCPUMem();
		pack();
		setVisible(true);
	}
	
	public void updateTable(int address, int data, boolean ppu) {
		if(ppu) {
			ppuMem.changeByte(address, data);
		} else {
			cpuMem.changeByte(address, data);
		}
		repaint();
	}
	
	public void switchTables() {
		ppuMem.setVisible(!ppuMem.isVisible());
		cpuMem.setVisible(!cpuMem.isVisible());
		if(ppuMem.isVisible()) {
			setTitle("PPU");
		} else {
			setTitle("CPU");
		}
	}

}
