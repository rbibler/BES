package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class ZeroPage implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu, boolean read) {
		final int add = cpu.read(cpu.getPC());
		cpu.incrementPC();
		cpu.setAddress(add);
		if(read) {
			return cpu.read(add);
		} else
			return 0;
	}

}
