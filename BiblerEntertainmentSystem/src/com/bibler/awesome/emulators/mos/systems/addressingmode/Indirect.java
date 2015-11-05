package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Indirect implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu, boolean read) {
		final int PC = cpu.getPC();
		cpu.incrementPC();
		final int PCPlusOne = cpu.getPC();
		cpu.incrementPC();
		final int address = cpu.read(PC)  | cpu.read(PCPlusOne) << 8;
		final int addLow = cpu.read(address);
		int addHigh = ((address + 1) & 0xFF) + (address & 0xFF00);
		addHigh = cpu.read(addHigh);
		cpu.setAddress(addLow | (addHigh << 8));
		if(read) {
			return cpu.read(address);
		} else
			return 0;
	}

}
