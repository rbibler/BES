package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Immediate implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu, boolean read) {
		final int add = cpu.getPC();
		cpu.incrementPC();
		cpu.setAddress(add);
		if(read) {
			return cpu.read(add);
		} else
			return 0;
	}

}
