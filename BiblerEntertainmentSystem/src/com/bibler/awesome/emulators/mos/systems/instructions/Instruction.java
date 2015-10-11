package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502;
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode;

public abstract class Instruction {
	
	protected CPU6502 cpu;
	protected AddressingMode mode;
	protected String mnemonic;
	
	public Instruction(CPU6502 cpu, AddressingMode mode, String mnemonic) {
		this.cpu = cpu;
		this.mode = mode;
		this.mnemonic = mnemonic;
	}
	
	public abstract void execute();

}
