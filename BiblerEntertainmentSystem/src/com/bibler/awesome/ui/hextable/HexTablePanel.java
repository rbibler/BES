package com.bibler.awesome.ui.hextable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HexTablePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4113892017356412562L;
	private HexTable table;
	private InfoPanel infoPanel;
	private JScrollPane pane;
	
	public HexTablePanel(HexTable table) {
		super();
		this.table = table;
		initialize();
	}
	
	private void initialize() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		RowTable row = new RowTable(table);
		pane = new JScrollPane(table);
		pane.setRowHeaderView(row);
		add(pane);
		infoPanel = new InfoPanel();
		add(infoPanel);
		table.setInfoPanel(infoPanel);
	}
	
	public HexTable getTable() {
		return table;
	}
	
	public void updateData(int[] data) {
		Rectangle visible = table.getVisibleRect();
		table.updateData(data);
		pane.scrollRectToVisible(visible);
	}
	
	public void changeByte(int address, int data) {
		Rectangle visible = table.getVisibleRect();
		table.changeByte(address, data);
		pane.scrollRectToVisible(visible);
	}

}
