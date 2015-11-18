package com.bibler.awesome.emulators.mos.systems;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import com.bibler.awesome.emulators.mos.controllers.HexTableController;

public class MemoryManager implements Observer {

	Memory cpuRam;
	public Memory ppuCtrlRegisters;
	Memory apuRegisters;
	Memory cartExpansionRom;
	Memory sRam;
	Memory prgRom;
	Controller controller;
	Point lastChanged = new Point(0,0);
	
	PPU ppu;
	
	byte ppuCtrlToggle;
	int[] ppuAdd = new int[2];
	
	public MemoryManager() {
		initializeMemoryBanks();
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	private void initializeMemoryBanks() {
		cpuRam = new Memory(0x800);
		ppuCtrlRegisters = new Memory(0x08);
		apuRegisters = new Memory(0x20);
		cartExpansionRom = new Memory(0x1FE0);
		sRam = new Memory(0x2000);
		prgRom = new Memory(0x8000);
	}
	
	public void copyPRG() {
		for(int i = 0; i < 0x4000; i++) {
			prgRom.write(0x4000 + i, prgRom.read(i));
		}
	}
	
	public void write(int address, int data) {
		if(address < 0x2000) {
			address %= 0x800;
			cpuRam.write(address, data);
		} else if(address < 0x4000) {
			address %= 8;
			ppu.write(address, data);
		} else if(address < 0x4020) {
			if(address == 0x4014) {
				executeDAM(data);
			}
			if(address == 0x4016) {
				controller.write();
			}
			apuRegisters.write(address - 0x4000, data);
		} else if(address < 0x6000) {
			cartExpansionRom.write(address - 0x4020, data);
		} else if(address < 0x8000) {
			sRam.write(address - 0x6000, data);
		} else if(address < 0x10000) {
			prgRom.write(address - 0x8000, data);
		}
		updateLastChanged(address, data);
	}
	
	private void updateLastChanged(int address, int data) {
		lastChanged.x = address;
		lastChanged.y = data;
	}
	
	public void executeDAM(int address) {
		address *= 0x100;
		for(int i = 0; i < 0x100; i++) {
			ppu.write(4, cpuRam.read(address + i));
		}
	}
	
	public int read(int address) {
		if(address < 0x2000) {
			return cpuRam.read(address % 0x800);
		} else if(address < 0x4000) {
			address = address % 0x08;
			return ppu.read(address);
		} else if(address < 0x4020) {
			if(address == 0x4016 && controller != null) {
				controller.strobe();
				return controller.read();
			}
			return apuRegisters.read(address - 0x4000);
		} else if(address < 0x6000) {
			try {
			return cartExpansionRom.read(address - 0x4020);
			} catch(ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} else if(address < 0x8000) {
			return sRam.read(address - 0x6000);
		} else if(address < 0x10000) {
			return prgRom.read(address - 0x8000);
		}
		return -1;
	}

	public int getToggle() {
		return ppuCtrlToggle;
	}
	
	public void resetAll() {
		cpuRam.reset();
	}
	
	public int[] consolidateMemory() {
		int[] ret = new int[0x8000];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = this.read(0x8000 + i);
		}
		return ret;
	}

	public Point getLastChanged() {
		return lastChanged;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof HexTableController) {
			final Point p = (Point) arg1;
			write(p.x, p.y);
		}
		
	}
}
