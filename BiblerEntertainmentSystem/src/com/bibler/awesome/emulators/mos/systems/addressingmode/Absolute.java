package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Absolute implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		final int PC = cpu.getPC();
		cpu.incrementPC();
		final int PCPlusOne = cpu.getPC();
		cpu.incrementPC();
		final int address = cpu.read(PC)  | cpu.read(PCPlusOne) << 8;
		cpu.setAddress(address);
		return cpu.read(address);
	}

}
