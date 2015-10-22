package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class PLP extends Instruction { 

    public PLP(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int SP = cpu.getSP();
    	final int statusRegister = cpu.read(0x100 + ((SP + 1) & 0xFF));
    	cpu.setStatusRegister(statusRegister);
    	cpu.setSP((SP + 1) & 0xFF); 
    } 
}