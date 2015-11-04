package com.bibler.awesome.emulators.mos.controllers;

import java.awt.Point;

import com.bibler.awesome.ui.hextable.HexTable;
import com.bibler.awesome.ui.hextable.HexTablePanel;

public class HexTableController {
	
	private HexTable table;
	private HexTablePanel panel;
	
	public HexTableController() {
		
	}
	
	public HexTablePanel setTable(HexTable table) {
		this.table = table;
		panel = new HexTablePanel(table);
		return panel;
	}
	
	public void updateCell(Point cellToChange) {
		table.updateCell(cellToChange.x, cellToChange.y);
	}

}
