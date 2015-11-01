package com.bibler.awesome.emulators.mos.ui;

import javax.swing.JFrame;

import com.bibler.awesome.ui.hextable.HexTable;
import com.bibler.awesome.ui.hextable.HexTablePanel;

public class MemoryView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7941655020467385772L;
	HexTable table;
	HexTablePanel tablePanel;
	
	public MemoryView(int size) {
		super();
		initializeView();
		initializeTable(size);
	}
	
	private void initializeView() {
		
	}
	
	private void initializeTable(int size) {
		table = new HexTable(size);
		tablePanel = new HexTablePanel(table);
		add(tablePanel);
		pack();
		setVisible(true);
	}
	
	public void update(int address, int data) {
		table.updateCell(address, data);
	}

}
