package com.bibler.awesome.emulators.mos.systems.instructions;

import com.bibler.awesome.emulators.mos.systems.CPU6502; 
import com.bibler.awesome.emulators.mos.systems.addressingmode.AddressingMode; 

public class PHP extends Instruction { 

    public PHP(CPU6502 cpu, AddressingMode mode, String mnemonic) { 
        super(cpu, mode, mnemonic); 
    } 

    @Override 
    public void execute() { 
    	final int statusRegister = cpu.getStatusRegister();
    	final int SP = cpu.getSP();
    	cpu.write(0x100 + SP, statusRegister);
    	cpu.setSP(SP - 1 >= 0 ? SP - 1 : 0xFF);
        
    } 
}