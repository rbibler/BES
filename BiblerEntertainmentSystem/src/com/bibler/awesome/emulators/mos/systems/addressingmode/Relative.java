package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public class Relative implements AddressingMode {
	
	@Override
	public int read(CPU6502 cpu) {
		int PC = cpu.getPC();
		cpu.incrementPC();
		int add = cpu.read(PC);
		add = add > 127 ? (add - 256) : add;
		cpu.setAddress(add);
		return (PC + add);
	}

}
