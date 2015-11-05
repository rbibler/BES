package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class IndirectX implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu, boolean read) {
		int add = cpu.read(cpu.getPC());
		add += cpu.getX();
		add %= 256;
		cpu.incrementPC();
		add = cpu.read(add) | cpu.read(add + 1) << 8;
		cpu.setAddress(add);
		if(read) {
			return cpu.read(add);
		} else 
			return 0;
	}

}
