package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Immediate implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		final int add = cpu.getPC();
		cpu.incrementPC();
		cpu.setAddress(add);
		return cpu.read(add);
	}

}
