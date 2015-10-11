package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class AbsoluteX implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		int PC = cpu.getPC();
		cpu.incrementPC();
		int PCPlusOne = cpu.getPC();
		cpu.incrementPC();
		final int add = cpu.read(PC)  | cpu.read(PCPlusOne) << 8;
		final int effectiveAdd = (add + cpu.getX());
		if(add >>8 != ((effectiveAdd) >> 8)) {
			cpu.setPageBoundaryFlag();
		}
		cpu.setAddress(effectiveAdd);
		return cpu.read(effectiveAdd);
	}

}
