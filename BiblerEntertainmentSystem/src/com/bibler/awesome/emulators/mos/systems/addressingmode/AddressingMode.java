package com.bibler.awesome.emulators.mos.systems.addressingmode;

import com.bibler.awesome.emulators.mos.systems.CPU6502;

public abstract interface AddressingMode {

	public abstract int read(CPU6502 cpu, boolean read);

}
