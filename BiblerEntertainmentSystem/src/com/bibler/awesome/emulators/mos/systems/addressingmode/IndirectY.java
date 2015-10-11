package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class IndirectY implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		final int add = cpu.read(cpu.getPC());
		cpu.incrementPC();
		final int effectiveAdd = ((cpu.read(add) | (cpu.read(add + 1) << 8)) + cpu.getY()) % 65536;
		if(add >>8 != ((effectiveAdd) >> 8)) {
			cpu.setPageBoundaryFlag();
		}
		cpu.setAddress(effectiveAdd);
		return cpu.read(effectiveAdd);
	}

}
