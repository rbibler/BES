package com.bibler.awesome.emulators.mos.systems;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Controller implements KeyListener {
	
	private int buttonsByte;
	private int latch;
	private int output;
	
	public final static int UP = 0x01;
	public final static int DOWN = 0x02;
	public final static int LEFT = 0x03;
	public final static int RIGHT = 0x04;
	public final static int SELECT = 0x05;
	public final static int START = 0x06;
	public final static int A = 0x07;
	public final static int B = 0x08;
	
	private HashMap<Integer, Integer> keyMap = new HashMap<Integer, Integer>();
	
	public Controller() {
		fillKeyMap();
	}
	
	private void fillKeyMap() {
		keyMap.put(KeyEvent.VK_A, 1);
		keyMap.put(KeyEvent.VK_S, 0b10);
		keyMap.put(KeyEvent.VK_SPACE, 0b100);
		keyMap.put(KeyEvent.VK_ENTER, 0b1000);
		keyMap.put(KeyEvent.VK_UP, 0b10000);
		keyMap.put(KeyEvent.VK_DOWN, 0b100000);
		keyMap.put(KeyEvent.VK_LEFT, 0b1000000);
		keyMap.put(KeyEvent.VK_RIGHT,  0b10000000);
	}
	
	public synchronized int read() {
		return output;
	}
	
	public synchronized void write() {
		latch = buttonsByte;
	}
	
	public synchronized void strobe() {
		 output = latch & 1;
	     latch = ((latch >> 1) | 0x100);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyEvent = arg0.getKeyCode();
		if(!keyMap.containsKey(keyEvent)) {
			return;
		}
		buttonsByte |= keyMap.get(keyEvent);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyEvent = arg0.getKeyCode();
		if(!keyMap.containsKey(keyEvent)) {
			return;
		}
		buttonsByte &= ~keyMap.get(keyEvent);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
