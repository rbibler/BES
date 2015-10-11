package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class ZeroPageX implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		final int zpAdd = cpu.read(cpu.getPC());
		final int X = cpu.getX();
		final int address = (zpAdd + X) % 256;
		cpu.incrementPC();
		cpu.setAddress(address);
		return cpu.read(address);
	}

}
