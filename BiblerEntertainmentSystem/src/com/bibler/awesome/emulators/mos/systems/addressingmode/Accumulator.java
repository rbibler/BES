package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Accumulator implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu, boolean read) {
		cpu.setAddress(CPU6502.ACCUMULATOR);
		return cpu.getAccumulator();
	}

}
