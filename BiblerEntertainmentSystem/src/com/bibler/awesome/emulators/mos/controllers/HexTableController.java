package com.bibler.awesome.emulators.mos.controllers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.bibler.awesome.ui.hextable.HexTable;
import com.bibler.awesome.ui.hextable.HexTablePanel;

public class HexTableController extends Observable {
	
	private HexTable table;
	private HexTablePanel panel;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public HexTableController() {
		
	}
	
	public HexTablePanel setTable(HexTable table) {
		this.table = table;
		table.setController(this);
		panel = new HexTablePanel(table);
		return panel;
	}
	
	public void updateCell(Point cellToChange) {
		table.updateCell(cellToChange.x, cellToChange.y);
	}
	
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}
	
	public void notifyObserversOnCellDataChange(Point dataChangeReport) {
		for(Observer observer : observers) {
			observer.update(this, dataChangeReport);
		}
	}

}
