package com.bibler.awesome.emulators.mos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.bibler.awesome.emulators.mos.ui.MainFrame;

public class MenuListener implements ActionListener {
	
	private MainFrame frame;
	
	public MenuListener(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		switch(command) {
		case "OPEN":
			frame.open();
			break;
		case "DEBUG":
			frame.debug();
			break;
		case "RUN":
			frame.run();
			break;
		case "STEP_INTO":
			frame.stepInto();
			break;
		case "STEP_OUT":
			frame.stepOut();
			break;
		case "RESET":
			frame.reset();
			break;
		case "SHOW_MEM":
			frame.showMemFrame();
			break;
		case "SHOW_PT":
			frame.showPatternTableFrame();
			break;
		case "SHOW_NT":
			frame.showNameTableFrame();
			break;
		case "NEXT_FRAME":
			frame.nextFrame();
			break;
		case "SHOW_GRID":
			frame.showGrid();
			break;
		case "SHOW_ATTR_GRID":
			frame.showAttr();
			break;
		case "PAUSE":
			frame.pause();
			break;
		}
		
		
	}

}
