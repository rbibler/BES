package com.bibler.awesome.emulators.mos.main;

import com.bibler.awesome.emulators.mos.ui.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		setupShutdown(frame);
	}
	
	
	
	private static void setupShutdown(MainFrame frame) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				frame.shutdown();
			}
		}));
		
	}

}
