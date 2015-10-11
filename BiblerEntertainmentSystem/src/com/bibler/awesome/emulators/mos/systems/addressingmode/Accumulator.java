package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Accumulator implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		return cpu.getAccumulator();
	}

}