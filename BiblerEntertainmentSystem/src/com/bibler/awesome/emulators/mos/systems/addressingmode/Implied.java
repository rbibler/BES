package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Implied implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		return 0;
	}

}
