package com.bibler.awesome.emulators.mos.systems;

import java.util.Arrays;

import com.bibler.awesome.emulators.mos.ui.MemoryFrame;

public class MemoryManager {

	Memory cpuRam;
	public Memory ppuCtrlRegisters;
	Memory apuRegisters;
	Memory cartExpansionRom;
	Memory sRam;
	Memory prgRom;
	Controller controller;
	MemoryFrame frame;
	
	PPU ppu;
	
	byte ppuCtrlToggle;
	int[] ppuAdd = new int[2];
	
	public MemoryManager() {
		initializeMemoryBanks();
	}
	
	public void setMemoryFrame(MemoryFrame frame) {
		this.frame = frame;
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
			cpuRam.write(address % 0x800, data);
		} else if(address < 0x4000) {
			ppu.write(address % 8, data);
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
		if(frame != null && address > 0) {
			frame.updateTable(address, data, false);
		}
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
		int[] ret = new int[0xFFFF];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = this.read(i);
		}
		return ret;
	}
}
