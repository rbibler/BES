package com.bibler.awesome.emulators.mos.controllers;

import java.util.Observable;
import java.util.Observer;

import com.bibler.awesome.emulators.mos.interfaces.CPU;
import com.bibler.awesome.emulators.mos.ui.MemoryView;

public class MemoryViewController implements Observer {
	
	private MemoryView view;
	
	public MemoryViewController(MemoryView view) {
		this.view = view;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		int[] values = (int[]) arg1;
		view.update(values[0], values[1]);
	}

}
