package com.bibler.awesome.emulators.mos.systems;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.bibler.awesome.emulators.mos.interfaces.Notifiable;

public class Emulator extends Observable implements Runnable {
	
	private CPU6502 cpu;
	private PPU ppu;
	private byte currentOpcode;
	
	private Object pauseLock = new Object();
	private boolean pause;
	private boolean running;
	private boolean NMINext;
	private boolean canNMI;
	private boolean frameByFrame;
	private int state;
	private int cycles;
	
	public final static int DEBUG = 0x01;
	public final static int RUN = 0x02;
	
	private ArrayList<Integer> breakPoints = new ArrayList<Integer>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	
	public Emulator() {
	}
	
	public void setCPU(CPU6502 cpu) {
		this.cpu = cpu;
		ppu = cpu.getPPU();
		ppu.setEmulator(this);
	}
	
	public void setPPU(PPU ppu) {
		this.ppu = ppu;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	
	public CPU6502 getCPU() {
		return cpu;
	}
	
	public void pauseEmulation() {
		pause();
		notifyObservers();
	}
	
	public void pause() {
		synchronized(pauseLock) {
			pause = true;
		}
	}
	
	public void resume() {
		synchronized(pauseLock) {
			pause = false;
			pauseLock.notifyAll();
		}
	}
	
	public void nextCycle() {
		currentOpcode = (byte) cpu.fetch();
		cycles = cpu.execute(currentOpcode);
		int ppuCycles = cycles * 3;
		for(int i = 0; i < ppuCycles; i++) {
			ppu.step();
		}
		if(NMINext) {
			canNMI = false;
			cpu.NMI();
			cycles = 7;
			NMINext = false;
			System.out.println("NMI");
		}
		notifyObservers("MEM");
	}
	
	public void startEmulation() {
		Thread t = new Thread(this);
		running = true;
		cpu.setState(1);
		state = RUN;
		//ppu.setStartTime();
		pause();
		t.start();
		resume();
	}
	
	public void nextFrame() {
		if(state != RUN) {
			startEmulation();
		} else {
			if(pause) {
				resume();
			}
		}
		frameByFrame = true;
	}
	
	public void debug() {
		state = DEBUG;
		cpu.setState(1);
	}
	
	public void reset() {
		cpu.reset();
	}
	
	public void stepInto() {
		if(!running) {
			startEmulation();
			pause();
		}
		nextCycle();
		notifyObservers("STATUS");
	}
	
	public void stepOutOf() {
		resume();
	}
	
	public int getCycles() {
		return cycles;
	}
	
	public void setNMI(boolean NMINext) {
		if(canNMI)
			this.NMINext = NMINext;
	}
	
	public void resetNMI() {
		canNMI = true;
	}
	
	public void addBreakPoint(int pc) {
		if(breakPoints.contains(pc)) {
			return;
		}
		breakPoints.add(pc);
	}
	
	public void frameAlert(long time) {
		if(frameByFrame) {
			notifyObservers("STATUS");
			pause();
		}
	}
	
	public void notifyObservers(String message) {
		for(Observer observer : observers) {
			observer.update(this, message);
		}
	}
	
	public void notifyScanline(int scanline) {
		
	}

	@Override
	public void run() {
		while(running) {
			if(pause) {
				synchronized(pauseLock) {
					try {
						pauseLock.wait();
					} catch(InterruptedException e) {}
				}
			}
			for(Integer bp : breakPoints) {
				if(bp == cpu.getPC()) {
					pause();
					continue;
				}
			}
			nextCycle();
		}
	}

	public PPU getPPU() {
		return ppu;
	}
}
