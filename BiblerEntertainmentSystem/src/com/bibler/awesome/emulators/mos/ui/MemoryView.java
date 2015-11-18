package com.bibler.awesome.emulators.mos.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.bibler.awesome.emulators.mos.controllers.HexTableController;
import com.bibler.awesome.emulators.mos.systems.CPU6502;
import com.bibler.awesome.emulators.mos.systems.MemoryManager;
import com.bibler.awesome.emulators.mos.systems.PPUMemoryManager;
import com.bibler.awesome.ui.hextable.HexTable;

public class MemoryView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7941655020467385772L;
	HexTableController cpuController;
	HexTableController ppuController;
	CPU6502 cpu;
	JTabbedPane pane;
	
	public MemoryView(int size) {
		super();
		initializeView();
		initializeTables(size);
		add(pane);
		pack();
		setVisible(true);
	}
	
	private void initializeView() {
		pane = new JTabbedPane();
	}
	
	public void setCPU(CPU6502 cpu) {
		this.cpu = cpu;
		if(cpuController != null) {
			cpuController.registerObserver(cpu.getMem());
		}
		if(ppuController != null) {
			ppuController.registerObserver(cpu.getPPU().getManager());
		}
	}
	
	private void initializeTables(int size) {
		cpuController = new HexTableController();
		pane.add("CPU Memory", cpuController.setTable(new HexTable(size)));
		ppuController = new HexTableController();
		pane.add("PPU Memory", ppuController.setTable(new HexTable(size)));
	}
	
	public void update(CPU6502 cpu) {
		final MemoryManager mem = cpu.getMem();
		final PPUMemoryManager ppuMem = cpu.getPPU().getManager();
		cpuController.updateCell(mem.getLastChanged());
		ppuController.updateCell(ppuMem.getLastChanged());
	}

}
