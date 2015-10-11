package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Indirect implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		final int add = cpu.read(cpu.getPC());
		cpu.incrementPC();
		cpu.setAddress(add);
		return add;
	}

}
