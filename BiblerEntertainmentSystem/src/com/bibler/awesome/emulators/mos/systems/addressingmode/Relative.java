package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Relative implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu, boolean read) {
		int offset = cpu.read(cpu.getPC());
		cpu.incrementPC();
		offset = offset > 127 ? (offset - 256) : offset;
		cpu.setAddress(offset);
		if(read) {
			return (cpu.getPC() + offset);
		} else
			return 0;
	}

}
