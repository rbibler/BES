package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class ZeroPageY implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		final int zpAdd = cpu.read(cpu.getPC());
		final int Y = cpu.getY();
		final int address = (zpAdd + Y) % 256;
		cpu.incrementPC();
		cpu.setAddress(address);
		return cpu.read(address);
	}

}
